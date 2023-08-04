package app.Configuration;

import com.WDTComponents.AlertInterfaces.IMessage;
import com.WDTComponents.DelegateMethods.IDelegateMethod;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class MessageConfiguration implements IMessage {

    @Override
    public void showMessage(String strMessage) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setTitle("Message");
            alert.setContentText(strMessage);
            alert.show();
        });
    }

    @Override
    public void showMessageLikeQuestion(String strMessage, IDelegateMethod ifYesAction, IDelegateMethod ifNoAction) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setTitle("Yes or no?");
            alert.setContentText(strMessage);
            alert.getButtonTypes().addAll(
                    new ButtonType("Yes", ButtonBar.ButtonData.YES),
                    new ButtonType("No", ButtonBar.ButtonData.NO)
            );
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES)
                    ifYesAction.voidMethod();
                else
                    ifNoAction.voidMethod();
            });
        });
    }

}
