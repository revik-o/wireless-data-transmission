package app.Configuration;

import app.Controller.LoadAlertController;
import com.WDTComponents.AlertInterfaces.ILoadAlert;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadAlertConfiguration implements ILoadAlert {

    private Stage stage;
    private LoadAlertController loadAlertController;

    @Override
    public void showAlert() {
        Platform.runLater(() -> {
            stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("resource/FXML/load_alert.fxml"));
            loadAlertController = new LoadAlertController();
            fxmlLoader.setController(loadAlertController);
            Parent parent = null;
            try {
                parent = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(parent));
            stage.show();
        });
    }

    @Override
    public void closeAlert() {
        Platform.runLater(() -> stage.close());
    }

    @Override
    public void setPercentageOfDownload(double percent) {
        if (loadAlertController != null)
            loadAlertController.setProgress(percent / 100d);
    }

    @Override
    public void setTitleAlert(String title) {
        Platform.runLater(() -> stage.setTitle(title));
    }

    @Override
    public void setContentText(String text) {
        if (loadAlertController != null)
            loadAlertController.setText(text);
    }

}
