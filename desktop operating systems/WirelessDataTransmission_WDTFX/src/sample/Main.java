package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Platform.AlertMessage;
import sample.lib.Server.ServerSocketKt;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ServerSocketKt.startServerSocket(new AlertMessage());
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("WDTFX");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(windowEvent -> System.exit(0));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
