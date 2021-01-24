package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.DataBase.Model.DeviceModel;
import sample.Platform.AlertMessage;
import sample.Platform.DataBase.DataBaseConfigKt;
import sample.Platform.PlatformConfigKt;
import sample.lib.Server.ServerSocketKt;

import java.awt.*;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (!new File(PlatformConfigKt.getCOMMON_DIRECTORY()).exists()) new File(PlatformConfigKt.getCOMMON_DIRECTORY()).mkdirs();
        ServerSocketKt.startServerSocket(new AlertMessage());
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle(AppInfoKt.APPLICATION_NAME);
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(windowEvent -> System.exit(0));
        DataBaseConfigKt.getDeviceModelDAO().selectAll().forEach(strings -> {
            for (String s : strings) {
                System.out.print(s + " :::: ");
            }
            System.out.println();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
