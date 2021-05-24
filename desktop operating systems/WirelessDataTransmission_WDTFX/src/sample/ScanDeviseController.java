package sample;

import com.WDTComponents.AppConfig;
import com.WDTComponents.WorkingWithDevices.ScanDevicesIPVersion4;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.*;

public class ScanDeviseController {

    private List<File> fileSet = new ArrayList<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane addDev;

    @FXML
    private Label l1;

//    ScanDevices scanDevices;
    ScanDevicesIPVersion4 scanDevices_;

    @FXML
    void Rescan(ActionEvent event) {
//        scanDevices.stopScan();
//        ScanDevise();
        scanDevices_.stopScanDevices();
        ScanDevise_();
    }

    @FXML
    void initialize() {
        assert l1 != null : "fx:id=\"l1\" was not injected: check your FXML file 'scanDevise.fxml'.";
        l1.setText("0%");
//        ScanDevise();
        ScanDevise_();
    }

    private Stage stage;

    private void initScanDevise(Stage stage) {
        stage.setOnCloseRequest(windowEvent -> {
//            scanDevices.stopScan();
            scanDevices_.stopScanDevices();
        });
        this.stage = stage;
    }

    ScanDeviseController(Stage stage) {
        initScanDevise(stage);
    }

    ScanDeviseController(Stage stage, List<File> fileSet) {
        this.fileSet = fileSet;
        initScanDevise(stage);
    }

    int status = 0;

    ScanDeviseController(Stage stage, int status) {
        this.status = status;
        initScanDevise(stage);
    }

    private void ScanDevise_() {
        addDev.getChildren().clear();
        scanDevices_ = new ScanDevicesIPVersion4(
                (nameDevice, typeDevice, ipString, dataInputStream, dataOutputStream, socket) -> {
                    try {
                        Platform.runLater(() -> {
                            Button button = new Button(nameDevice);
                            button.setOnMouseClicked(mouseEvent -> buttonMouseClicked_(socket, ipString));
                            addDev.getChildren().add(button);
                        });
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },
                number -> Platform.runLater(() -> l1.setText(number + "%"))
        );
    }

    private void buttonMouseClicked_(Socket socket, String ipString) {
        scanDevices_.stopScanDevices();
        if (status == 3) {
            this.stage.close();
            AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType3(socket);
        } else {
            int sendType = 0;
            for (File file : fileSet){
                if (file.isDirectory()) {
                    sendType = 2;
                    break;
                }
                else sendType = 1;
            }
            if (sendType == 1) {
                this.stage.close();
                new Thread(() ->
                    AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType1(socket, fileSet)
                ).start();
            } else if (sendType == 2) {
                this.stage.close();
                new Thread(() ->
                        AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType2(socket, fileSet)
                ).start();
            }
        }
    }

    /*
    private void ScanDevise() {
        addDev.getChildren().clear();
        scanDevices = new ScanDevices(
                DataBaseConfigKt.getDeviceModelDAO(),
                (nameDevice, typeDevice, dataInputStream, dataOutputStream, socket) -> {
                    try {
                        dataOutputStream.write(0); //
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
        if (status == 3) BUTTON_ACTION.INSTANCE.sendButtonAction4Buff(scanDevices, socket, nameDevice, typeDevice, status);
        else BUTTON_ACTION.INSTANCE.sendButtonAction(scanDevices, socket, nameDevice, typeDevice, fileSet);
    }
    */

}
