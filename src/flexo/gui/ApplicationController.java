package flexo.gui;

import flexo.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ApplicationController {

    private final GUI gui;

    @FXML
    private VBox propertiesVBox;

    @FXML
    private SubScene subScene;

    @FXML
    private Pane pane;

    public VBox getPropertiesVBox() {
        return propertiesVBox;
    }

    public SubScene getSubScene() {
        subScene.heightProperty().bind(pane.heightProperty());
        subScene.widthProperty().bind(pane.widthProperty());
        return subScene;
    }

    public ApplicationController(GUI gui) {
        this.gui = gui;
    }
}
