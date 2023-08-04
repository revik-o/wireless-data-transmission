package app.Controller.TemplateController;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class FolderOrDirectoryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imageView;

    @FXML
    private Text textBox;

    private String text = "";
    private String resourceString = "";

    public FolderOrDirectoryController(String text, String resourceString) {
        this.text = text;
        this.resourceString = resourceString;
    }

    @FXML
    void initialize() {
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'file_or_directory_template.fxml'.";
        assert textBox != null : "fx:id=\"textBox\" was not injected: check your FXML file 'file_or_directory_template.fxml'.";
        textBox.setText(text);
        imageView.setImage(new Image(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream(resourceString)
                )
        ));
    }

}
