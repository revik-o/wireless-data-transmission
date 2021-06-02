import com.WDTComponents.StartApplicationConfigs.DefaultStartApplicationConfigs;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import sample.Platform.Message;
import sample.Platform.PlatformDataBase;
import sample.Platform.PlatformDataBaseKt;
import sample.Platform.RealizeAlertInterface;
import sampleimagePath.AppInfoKt;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FMain {

    public static void main(String[] args) {
        AppInfoKt.ICON = (FMain.class.getResource("ic_launcher_round.png")).getFile();
        System.out.println(AppInfoKt.ICON);
        final Frame frame = new Frame();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = dimension.width / 2;
        int height = (int) (dimension.height / 1.5);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setLocation(
                (dimension.width / 2) - (width / 2),
                (dimension.height / 2) - (height / 2)
        );
        frame.setTitle(AppInfoKt.APPLICATION_NAME);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
        new Thread(() -> {
            final JFXPanel jfxPanel = new JFXPanel();
            if (!new File(PlatformDataBaseKt.getCOMMON_DIRECTORY()).exists()) new File(PlatformDataBaseKt.getCOMMON_DIRECTORY()).mkdirs();
            DefaultStartApplicationConfigs configs = new DefaultStartApplicationConfigs(new RealizeAlertInterface(), new Message(), new PlatformDataBase(), new File(System.getProperty("user.home") + "/Downloads/WirelessDataTransmission_WDTFX"));
            configs.start();
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(FMain.class.getResource("sample/sample.fxml")));
                    jfxPanel.setScene(new Scene(root));
//                    new sample.Platform.lM().showMessage("that's all data");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            frame.add(jfxPanel);
            frame.pack();
            System.gc();
        }).start();
    }

}
