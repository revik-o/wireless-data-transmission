package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Platform.PlatformDataBase;
import sample.Platform.Message;
import sample.Platform.PlatformDataBaseKt;
import sample.Platform.RealizeAlertInterface;
import com.WDTComponents.StartApplicationConfigs.DefaultStartApplicationConfigs;

import java.io.File;

public class Main extends Application {

    /*@Override
    public void start(Stage primaryStage) throws Exception {
        if (!new File(PlatformConfigKt.getCOMMON_DIRECTORY()).exists()) new File(PlatformConfigKt.getCOMMON_DIRECTORY()).mkdirs();
        InitKt.InitApp();
        ServerSocketKt.startServerSocket();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle(AppInfoKt.APPLICATION_NAME);
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(windowEvent -> System.exit(0));
        primaryStage.show();
    }*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (!new File(PlatformDataBaseKt.getCOMMON_DIRECTORY()).exists()) new File(PlatformDataBaseKt.getCOMMON_DIRECTORY()).mkdirs();
        DefaultStartApplicationConfigs configs = new DefaultStartApplicationConfigs(new RealizeAlertInterface(), new Message(), new PlatformDataBase(), new File(System.getProperty("user.home") + "/Downloads/WirelessDataTransmission_WDTFX"));
        configs.start();
        configs = null;
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
