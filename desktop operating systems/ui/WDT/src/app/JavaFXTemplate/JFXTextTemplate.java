package app.JavaFXTemplate;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class JFXTextTemplate {

    public Text createText(String string) {
        Text text = new Text(string);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.web("#0000007e"));
        text.setLayoutX(137);
        text.setLayoutY(187);
        return text;
    }

}
