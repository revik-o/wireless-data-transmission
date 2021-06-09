package app.JavaFXTemplate;

import javafx.scene.layout.Pane;

public class JFXSpaceTemplate {

    public Pane createSpace(int width, int height) {
        Pane pane = new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        return pane;
    }

}
