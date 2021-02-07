package sample.Platform;

//import javafx.application.Platform;
//import javafx.concurrent.Task;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonBar;
//import javafx.scene.control.ButtonType;
//import javafx.stage.StageStyle;
import sample.WDTComponents.AlertInterfaces.IMessage;
import sample.WDTComponents.DelegateMethods.IDelegateMethod;
import javax.swing.*;

public class Message implements IMessage {

    @Override
    public void showMessage(String strMessage) {
        JOptionPane.showMessageDialog(null, strMessage, "Message", JOptionPane.INFORMATION_MESSAGE);
        /*new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.TRANSPARENT);
                    alert.setTitle("Message");
                    alert.setContentText(strMessage);
                    alert.show();
                });
                return null;
            }
        }).start();*/
    }

    @Override
    public void showMessageLikeQuestion(String strMessage, IDelegateMethod ifYesAction) {
        int alert = JOptionPane.showConfirmDialog(null, strMessage, "Accept Data", JOptionPane.YES_NO_OPTION);
        if (alert == JOptionPane.YES_OPTION)
            ifYesAction.voidMethod();
        /*Platform.runLater(() -> {
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
            });
        });*/
    }

}
