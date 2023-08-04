package app.Configuration;

import app.Common.Options;
import app.Controller.MainController;
import app.Main;
import com.WDTComponents.AlertInterfaces.ILoadAlert;
import com.WDTComponents.Configuration.WDTMinDefaultConfiguration;
import com.WDTComponents.TypeDeviceENUM;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class JavaFXStarter extends Thread {

    @Override
    public void run() {
        File file = new File(Options.INSTANCE.getCOMMON_DIRECTORY());
        if (!file.exists())
            file.mkdirs();
        new WDTMinDefaultConfiguration(
                new ErrorMessageConfiguration(),
                LoadAlertConfiguration.class,
                new MessageConfiguration(),
                new LittleMessageConfiguration(),
                (System.getProperty("os.name").toLowerCase().contains("windows")) ?
                        new NotifyAboutCompleteProcessConfiguration4Windows() : new NotifyAboutCompleteProcessConfiguration(),
                new SystemClipboardConfiguration(),
                new WorkingWithDataBaseConfiguration(),
                new OpenDataMethodConfiguration(),
                TypeDeviceENUM.INSTANCE::getCOMPUTER,
                () -> {
                    try {
                        return System.getProperty("user.name")+ " " + System.getProperty("os.name") + " " + InetAddress.getLocalHost().getHostName();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    return "";
                },
                () -> new File(System.getProperty("user.home") + "/Downloads/WirelessDataTransmission_WDTFX")
        ).start();
        JFXPanel jfxPanel = new JFXPanel();
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getClassLoader().getResource("resource/FXML/main.fxml")));
                jfxPanel.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        JFrame frame = Main.frame;
        frame.getContentPane().removeAll();
        frame.add(jfxPanel);
        frame.pack();
    }

}
