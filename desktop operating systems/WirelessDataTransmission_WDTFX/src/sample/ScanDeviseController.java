package sample;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import sample.DataBase.Model.DeviceModel;
import sample.DataBase.ModelDAO.DeviceModelDAO;
import sample.Platform.DataBase.DataBaseConfigKt;
import sample.lib.DeviceIP.EnumerableIP4Kt;
import sample.lib.DeviceWork.DevicePostKt;
import sample.lib.DeviceWork.ScanDevices;
import sample.lib.FileWork.SocketСommunication4DirectoryKt;
import sample.lib.FileWork.SocketСommunication4FileKt;

public class ScanDeviseController {

    private Set<File> fileSet = new TreeSet<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane addDev;

    @FXML
    private Label l1;

    ScanDevices scanDevices;

    @FXML
    void Rescan(ActionEvent event) {
        scanDevices.stopScan();
        ScanDevise();
    }

    @FXML
    void initialize() {
        assert l1 != null : "fx:id=\"l1\" was not injected: check your FXML file 'scanDevise.fxml'.";
        l1.setText("0%");
        ScanDevise();
    }

    private void initScanDevise(Stage stage) {
        stage.setOnCloseRequest(windowEvent -> {
            scanDevices.stopScan();
        });
    }

    ScanDeviseController(Stage stage) {
        initScanDevise(stage);
    }

    ScanDeviseController(Stage stage, Set<File> fileSet) {
        this.fileSet = fileSet;
        initScanDevise(stage);
    }

    private void ScanDevise() {
        addDev.getChildren().clear();
        scanDevices = new ScanDevices(
                DataBaseConfigKt.getDeviceModelDAO(),
                (nameDevice, typeDevice, dataInputStream, dataOutputStream, socket) -> {
                    try {
                        dataOutputStream.write(0);
                        Platform.runLater(() -> {
                            Button button = new Button(nameDevice);
                            button.setOnMouseClicked(mouseEvent -> buttonMouseClicked(socket, nameDevice, typeDevice));
                            addDev.getChildren().add(button);
                        });
                        socket.close();
                    } catch (IOException e) { e.printStackTrace(); }
                },
                num -> Platform.runLater(() -> l1.setText(num + "%"))
        );
    }

    private void buttonMouseClicked(Socket socket, String nameDevice, String typeDevice) {
        scanDevices.stopScan();
        ////////
        boolean ipIsExistsInDatabase = false;
        for (String[] strings : DataBaseConfigKt.getDeviceModelDAO().selectWhereIPLike(EnumerableIP4Kt.enumerableIp4(socket.getInetAddress().toString().substring(1))))
            if (strings[3].equals(socket.getInetAddress().toString().substring(1))) {
                ipIsExistsInDatabase = true;
                break;
            }
        if (!ipIsExistsInDatabase) {
            DataBaseConfigKt.getDeviceModelDAO().insert(new DeviceModel(nameDevice, typeDevice, socket.getInetAddress().toString().substring(1)));
            System.out.println("add new device");
        }
        //////
        if (!fileSet.isEmpty()) {
            int sendType = 0;
            for (File file: fileSet) {
                if (file.isDirectory()) {
                    sendType = 2;
                    break;
                }
                else sendType = 1;
            }
            int finalSendType = sendType;
            System.out.println(sendType + "->>>>");
            if (sendType == 1) {
                ////// only files
                DevicePostKt.sendData(
                        new InetSocketAddress(socket.getInetAddress().toString().substring(1), socket.getPort()),
                        (string1, string2, dataInputStream1, dataOutputStream1, socket1) -> {
                            try {
                                dataOutputStream1.write(finalSendType);
                                dataOutputStream1.write(fileSet.size());
                                fileSet.forEach(file -> SocketСommunication4FileKt.sendDataFromFile(file, dataOutputStream1, dataInputStream1));
                            } catch (IOException e) { e.printStackTrace(); }
                        }
                );
            } else {
                ////// Dirs and Files
                DevicePostKt.sendData(
                        new InetSocketAddress(socket.getInetAddress().toString().substring(1), socket.getPort()),
                        (string1, string2, dataInputStream1, dataOutputStream1, socket1) -> {
                            try {
                                dataOutputStream1.write(finalSendType);
                                dataOutputStream1.write(fileSet.size());
                                fileSet.forEach(file -> {
                                    System.out.println(file.isDirectory());
                                    if (file.isDirectory()) {
                                        try {
                                            dataOutputStream1.writeBoolean(true);
                                            SocketСommunication4DirectoryKt.sendDataFromDirectory(file, file, dataInputStream1, dataOutputStream1);
                                        } catch (IOException e) { System.out.println("Не удалось отправить файл"); }
                                    }
                                    else {
                                        try {
                                            dataOutputStream1.writeBoolean(false);
                                            SocketСommunication4FileKt.sendDataFromFile(file, dataOutputStream1, dataInputStream1);
                                        } catch (IOException e) { System.out.println("Не удалось отправить directories"); }
                                    }
                                });
                            } catch (IOException e) { e.printStackTrace(); }
                        }
                );
            }
        } else {
            System.out.println("empty");
        }
    }

}
