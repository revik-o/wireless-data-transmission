package app.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadAlertController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text text;

    @FXML
    private ProgressBar progressBar;

    @FXML
    void initialize() {
        assert text != null : "fx:id=\"text\" was not injected: check your FXML file 'load_alert.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'load_alert.fxml'.";

    }

    public void setProgress(double progress) {
        if (progressBar != null)
            Platform.runLater(() ->
                progressBar.setProgress(progress)
            );
    }

    public void setText(String textStr) {
        if (text != null)
            Platform.runLater(() ->
                text.setText(textStr)
            );
    }

}