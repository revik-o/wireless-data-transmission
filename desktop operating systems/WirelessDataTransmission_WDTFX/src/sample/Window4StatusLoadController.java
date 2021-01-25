package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;

public class Window4StatusLoadController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text textBox;

    @FXML
    private ProgressBar progressBar;

    @FXML
    void initialize() {
        assert textBox != null : "fx:id=\"textBox\" was not injected: check your FXML file 'Window4StatusLoad.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'Window4StatusLoad.fxml'.";
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    }

    public void setParam1(String str) {
        Platform.runLater(() ->{
            textBox.setText(str);
        });
    }
    public void setParam2(Double param2) {
        new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                Platform.runLater(() -> {
                    double p = param2;
                    progressBar.setProgress(p);
                });
                return null;
            }
        }).start();
    }

}