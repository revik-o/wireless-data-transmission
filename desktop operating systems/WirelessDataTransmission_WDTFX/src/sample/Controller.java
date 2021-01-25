package sample;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.lib.ApplicationConfigKt;
import sample.lib.Message.ILoadStageMessage;

public class Controller {

    private Set<File> fileSet;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane drag_file;

    @FXML
    void DragDropped(DragEvent event) {
        fileSet.clear();
        drag_file.getChildren().clear();
        for (File file:
             event.getDragboard().getFiles()) {
            fileSet.add(file);
            Text text = new Text(file.getAbsolutePath());
            text.setLayoutX(30);
            text.setLayoutY(30);
            drag_file.getChildren().add(text);
        }
    }

    @FXML
    void DragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) event.acceptTransferModes(TransferMode.ANY);
    }

    @FXML
    void ScanDevices(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scanDevise.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new ScanDeviseController(stage));
        Parent root = fxmlLoader.load();
        stage.setTitle("Scan Devices");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void NextStep(ActionEvent event) throws IOException {
        if (!fileSet.isEmpty()){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scanDevise.fxml"));
            Stage stage = new Stage();
            fxmlLoader.setController(new ScanDeviseController(stage, fileSet));
            Parent root = fxmlLoader.load();
            stage.setTitle("Scan Devices");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    void Clean(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        drag_file.getChildren().clear();
        fileSet.clear();
    }

    @FXML
    void initialize() {
//        assert scan_devices != null : "fx:id=\"scan_devices\" was not injected: check your FXML file 'sample.fxml'.";
        assert drag_file != null : "fx:id=\"drag_file\" was not injected: check your FXML file 'sample.fxml'.";
        fileSet = new TreeSet<>();
    }

}
