package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Platform.DataBase.DataBaseConfigKt;
import sample.Platform.InitKt;
import sample.Platform.PlatformConfigKt;
import sample.lib.ApplicationConfigKt;
import sample.lib.Message.ILoadStageMessage;
import sample.lib.Server.ServerSocketKt;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (!new File(PlatformConfigKt.getCOMMON_DIRECTORY()).exists()) new File(PlatformConfigKt.getCOMMON_DIRECTORY()).mkdirs();
        InitKt.InitApp();
        ServerSocketKt.startServerSocket();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle(AppInfoKt.APPLICATION_NAME);
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(windowEvent -> System.exit(0));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
