package app.Controller;

import app.Action.MainController.CleanButton;
import app.Common.CommonMethod.ScanDevices;
import app.Common.Session;
import app.Controller.TemplateController.FolderOrDirectoryController;
import app.Feacher.MainController.SwitchBackToCleanButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button sendDataButton;

    @FXML
    private Button getClipboardButton;

    @FXML
    private AnchorPane anchorPaneForContent;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button rescanButton;

    @FXML
    private Button backOrClearButton;

    @FXML
    private Button nextButton;

    private SwitchBackToCleanButton switchBackToCleanButton = null;

    private double layoutXForDragDropped = 0;

    private byte MAIN_MODE = 0;

    @FXML
    void dragDropped(DragEvent event) {
        Session.INSTANCE.getFileArray().clear();
        anchorPaneForContent.getChildren().clear();
        layoutXForDragDropped = 0;
        for (File file : event.getDragboard().getFiles())
            addFile(file);
    }

    @FXML
    void dragOver(DragEvent event) {
        if (MAIN_MODE == 1)
            if (event.getDragboard().hasFiles())
                event.acceptTransferModes(TransferMode.ANY);
    }

    @FXML
    void anchorPaneMouseClicked(MouseEvent event) {
        if (MAIN_MODE == 1 && Session.INSTANCE.getFileArray().isEmpty()) {
            layoutXForDragDropped = 0;
            anchorPaneForContent.getChildren().clear();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(anchorPaneForContent.getScene().getWindow());
            if (file != null)
                addFile(file);
        }
    }

    @FXML
    void getClipboardAction(ActionEvent event) {
        anchorPaneForContent.getChildren().clear();
        ScanDevices.INSTANCE.startScanDevice(anchorPaneForContent, progressBar, 2);
    }

    @FXML
    void sendDataAction(ActionEvent event) {
        MAIN_MODE = 1;
        new CleanButton(anchorPaneForContent).handle(event);
        rescanButton.setDisable(true);
        rescanButton.setVisible(false);
        switchBackToCleanButton = new SwitchBackToCleanButton(backOrClearButton);
        switchBackToCleanButton.toCleanButton(anchorPaneForContent);
        progressBar.setProgress(0);
        progressBar.setVisible(false);
    }

    @FXML
    void initialize() {
        assert sendDataButton != null : "fx:id=\"sendDataButton\" was not injected: check your FXML file 'main.fxml'.";
        assert getClipboardButton != null : "fx:id=\"getClipboardButton\" was not injected: check your FXML file 'main.fxml'.";
        assert anchorPaneForContent != null : "fx:id=\"anchorPaneForContent\" was not injected: check your FXML file 'main.fxml'.";
        assert rescanButton != null : "fx:id=\"rescanButton\" was not injected: check your FXML file 'main.fxml'.";
        assert backOrClearButton != null : "fx:id=\"backOrClearButton\" was not injected: check your FXML file 'main.fxml'.";
        assert nextButton != null : "fx:id=\"nextButton\" was not injected: check your FXML file 'main.fxml'.";

        sendDataAction(null);
        nextButton.setOnAction(actionEvent -> {
            if (!Session.INSTANCE.getFileArray().isEmpty())
                ScanDevices.INSTANCE.startScanDevice(anchorPaneForContent, progressBar, 1);
        });
    }

    private void addFile(File file) {
        Session.INSTANCE.getFileArray().add(file);
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getClassLoader()
                        .getResource("resource/FXML/template/file_or_directory_template.fxml")
        );
        fxmlLoader.setController(new FolderOrDirectoryController(
                file.getName(),
                (file.isFile()) ? "resource/IMG/document.png" : "resource/IMG/folder.png"
        ));
        Platform.runLater(() -> {
            try {
                Node node = fxmlLoader.load();
                node.setLayoutX(20);
                node.setLayoutY(layoutXForDragDropped);
                layoutXForDragDropped += 80;
                anchorPaneForContent.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
