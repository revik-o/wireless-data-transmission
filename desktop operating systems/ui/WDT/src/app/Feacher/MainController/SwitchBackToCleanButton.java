package app.Feacher.MainController;

import app.Action.MainController.CleanButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.util.ArrayList;

public class SwitchBackToCleanButton {

    private final Button button;

    public SwitchBackToCleanButton(Button button) {
        this.button = button;
    }

    public void toBackButton() {
        button.setText("Back");
        button.setOnAction(new BackButtonAction());
    }

    public void toCleanButton(AnchorPane node) {
        button.setText("Clean");
        button.setOnAction(new CleanButton(node));
    }

    private class BackButtonAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
        }
    }

}
