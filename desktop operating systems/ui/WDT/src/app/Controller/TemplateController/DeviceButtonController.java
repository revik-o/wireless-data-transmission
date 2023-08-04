package app.Controller.TemplateController;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.WDTComponents.TypeDeviceENUM;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DeviceButtonController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deviceButton;

    @FXML
    private ImageView imageDeviceButton;

    private String deviceType = "";
    private String text = "";

    @FXML
    void initialize() {
        assert deviceButton != null : "fx:id=\"deviceButton\" was not injected: check your FXML file 'device_button.fxml'.";
        assert imageDeviceButton != null : "fx:id=\"imageDeviceButton\" was not injected: check your FXML file 'device_button.fxml'.";
        if (TypeDeviceENUM.INSTANCE.getCOMPUTER().equals(deviceType))
            imageDeviceButton.setImage(
                    new Image(Objects.requireNonNull(
                            getClass().getClassLoader().getResourceAsStream("resource/IMG/computer-laptop.png"))
                    )
            );
        else
            imageDeviceButton.setImage(
                    new Image(Objects.requireNonNull(
                            getClass().getClassLoader().getResourceAsStream("resource/IMG/phone.png"))
                    )
            );

        deviceButton.setText(text);
    }

    public DeviceButtonController(String deviceType, String text) {
        this.deviceType = deviceType;
        this.text = text;
    }

}
