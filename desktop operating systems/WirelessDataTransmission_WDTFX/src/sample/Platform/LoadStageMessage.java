package sample.Platform;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Window4StatusLoadController;
import sample.lib.Message.ILoadStageMessage;

import java.io.IOException;

public class LoadStageMessage implements ILoadStageMessage {

    @Override
    public void showMessage() {}
    @Override
    public void changeText4NameFile(String text) {}
    @Override
    public void changeProgress4ProgressBar(double number) {}
    @Override
    public void changeTitle(String text) {}
    @Override
    public void closeMessage() {}

    /*private Stage stage;
    private Window4StatusLoadController window4StatusLoadController = new Window4StatusLoadController();

    @Override
    public void showMessage() {
        Platform.runLater(() -> {
            try {
                stage  = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Window4StatusLoad.fxml"));
                fxmlLoader.setController(window4StatusLoadController);
                Parent parent = fxmlLoader.load();
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void changeText4NameFile(String text) { window4StatusLoadController.setParam1(text); }

    @Override
    public void changeProgress4ProgressBar(double number) { window4StatusLoadController.setParam2(number); }

    @Override
    public void changeTitle(String text) { stage.setTitle(text); }

    @Override
    public void closeMessage() {
        Platform.runLater(() -> stage.close());
    }*/

}
