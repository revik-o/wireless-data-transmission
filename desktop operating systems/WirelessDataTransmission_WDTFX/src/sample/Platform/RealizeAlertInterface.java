package sample.Platform;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.WDTComponents.AlertInterfaces.ILoadAlert;
import sample.Window4StatusLoadController;

import java.io.IOException;

public class RealizeAlertInterface implements ILoadAlert {

    private Stage stage;
    private Window4StatusLoadController window4StatusLoadController = new Window4StatusLoadController();

    @Override
    public void showAlert() {
//        new Thread(() -> {
            Platform.runLater(() -> {
                stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Window4StatusLoad.fxml"));
                fxmlLoader.setController(window4StatusLoadController);
                Parent parent = null;
                try {
                    parent = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(parent));
                stage.show();
            });
//        }).start();
    }

    @Override
    public void closeAlert() {
        Platform.runLater(() -> stage.close());
    }

    @Override
    public void setPercentageOfDownload(double percent) {
        window4StatusLoadController.setParam2(percent);
    }

    @Override
    public void setTitleAlert(String title) {
        Platform.runLater(() -> {
            stage.setTitle(title);
        });
    }

    @Override
    public void setContentText(String text) {
        window4StatusLoadController.setParam1(text);
    }
}
