package app.Action.MainController;

import app.Common.CommonMethod.ScanDevices;
import app.Common.Session;
import app.JavaFXTemplate.JFXTextTemplate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;

public class CleanButton implements EventHandler<ActionEvent> {

    private final AnchorPane node;

    public CleanButton(AnchorPane node) {
        this.node = node;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        node.getChildren().clear();
        Session.INSTANCE.getFileArray().clear();
        node.getChildren().add(new JFXTextTemplate().createText("Transfer your files or dirs to this place"));
        ScanDevices.INSTANCE.stop();
    }

}
