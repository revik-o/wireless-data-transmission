package app.Action.DeviceButtonAction;

import com.WDTComponents.AppConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.net.Socket;
import java.util.List;

public class DeviceButtonFileTypeAction implements EventHandler<ActionEvent> {

    private Socket socket;
    private List<File> files;

    public DeviceButtonFileTypeAction(Socket socket, List<File> files) {
        this.socket = socket;
        this.files = files;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        int sendType = 0;
        for (File file: files) {
            if (file.isDirectory()) {
                sendType = 2;
                break;
            }
            else {
                sendType = 1;
            }
        }
        if (sendType == 1)
            new Thread(() ->
                    AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType1(socket, files)
            ).start();
        if (sendType == 2)
            new Thread(() ->
                    AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType2(socket, files)
            ).start();
    }

}