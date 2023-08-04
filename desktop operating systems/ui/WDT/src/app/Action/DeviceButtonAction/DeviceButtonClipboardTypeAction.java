package app.Action.DeviceButtonAction;

import com.WDTComponents.AppConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.net.Socket;

public class DeviceButtonClipboardTypeAction implements EventHandler<ActionEvent> {

    private final Socket socket;

    public DeviceButtonClipboardTypeAction(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType3(socket);
    }

}