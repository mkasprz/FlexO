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
    private Pane pane;

    @FXML
    private SubScene subScene;

    @FXML
    void initialize() {
        subScene.heightProperty().bind(pane.heightProperty());
        subScene.widthProperty().bind(pane.widthProperty());
    }

    public ApplicationController(GUI gui) {
        this.gui = gui;
    }

    public VBox getPropertiesVBox() {
        return propertiesVBox;
    }

    public Pane getPane() {
        return pane;
    }

    public SubScene getSubScene() {
        return subScene;
    }
}
