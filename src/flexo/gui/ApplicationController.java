package flexo.gui;

import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;

public class ApplicationController {

    @FXML
    private SpherePropertiesController propertiesController;

    @FXML
    private Pane pane;

    @FXML
    private SubScene subScene;

    @FXML
    void initialize() {
        subScene.heightProperty().bind(pane.heightProperty());
        subScene.widthProperty().bind(pane.widthProperty());
//        new Visualization(pane, subScene, propertiesController); // [TODO] Decide is it a good idea to call it here as it does things connected not only with GUI
    }

    public SpherePropertiesController getPropertiesController() {
        return propertiesController;
    }

    public Pane getPane() {
        return pane;
    }

    public SubScene getSubScene() {
        return subScene;
    }
}
