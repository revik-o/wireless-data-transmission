package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import sample.Platform.DataBase.DataBaseConfigKt;
import sample.lib.BUTTON_ACTION;
import sample.lib.DeviceWork.ScanDevices;

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

    int status = 0;

    ScanDeviseController(Stage stage, int status) {
        this.status = status;
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
        if (status == 3) BUTTON_ACTION.INSTANCE.sendButtonAction4Buff(scanDevices, socket, nameDevice, typeDevice, status);
        else BUTTON_ACTION.INSTANCE.sendButtonAction(scanDevices, socket, nameDevice, typeDevice, fileSet);
    }

}
