package flexo.gui;

import flexo.model.Setup;
import flexo.model.persistence.SetupLoader;
import flexo.model.persistence.SetupSaver;
import flexo.model.setupbuilder.SetupBuilder;
import flexo.visualization.Visualization;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;

public class ApplicationController {

    @FXML
    SplitPane splitPane;

    @FXML
    TitledPane listViewTitledPane;

    @FXML
    TitledPane propertiesTitledPane;

    @FXML
    private ListView listView;

    @FXML
    private PropertiesController propertiesController;

    @FXML
    private Pane pane;

    double lastDividerPosition;

    private Setup setup;
    private Group root;
    private Visualization visualization;

    private final int X = 0;
    private final int Y = 150;
    private final int Z = -2000;

    double lastX, lastY;
    Translate cameraTranslate = new Translate(X, Y, Z);
    Rotate cameraRotateX = new Rotate(0, 0, Y, 0, Rotate.X_AXIS);
    Rotate cameraRotateY = new Rotate(0, X, 0, 0, Rotate.Y_AXIS);

    @FXML
    void initialize() {
        listViewTitledPane.expandedProperty().addListener(getTitledPaneExtendedPropertyChangeListener(propertiesTitledPane));
        propertiesTitledPane.expandedProperty().addListener(getTitledPaneExtendedPropertyChangeListener(listViewTitledPane));

        propertiesController.setVisible(false);

        root = new Group();
        root.setRotationAxis(Rotate.X_AXIS);
        root.setRotate(180);

        SubScene subScene = new SubScene(root, 0, 0, true, SceneAntialiasing.BALANCED);
        subScene.heightProperty().bind(pane.heightProperty());
        subScene.widthProperty().bind(pane.widthProperty());
        pane.getChildren().add(subScene);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(Double.MAX_VALUE);
        camera.setNearClip(0.1);
        camera.getTransforms().addAll(cameraRotateY, cameraRotateX, cameraTranslate); // [TODO] Decide which rotation method is better

        subScene.setCamera(camera);
        subScene.setRoot(root);

        pane.setOnMousePressed(event -> {
            if (event.isMiddleButtonDown()) {
                cameraTranslate.setX(X);
                cameraTranslate.setY(Y);
                cameraTranslate.setZ(Z);
                cameraRotateX.setAngle(0);
                cameraRotateY.setAngle(0);
//                selectedSphere = null; // [TODO] Decide how deselection should work
//                propertiesController.setVisible(false);
            } else {
                lastX = event.getSceneX();
                lastY = event.getSceneY();
            }
        });

        pane.setOnMouseDragged(event -> {
            double sceneX = event.getSceneX();
            double sceneY = event.getSceneY();
            double deltaX = lastX - sceneX;
            double deltaY = lastY - sceneY;

            double multiplier = cameraTranslate.getZ() / Z;

            if (event.isPrimaryButtonDown()) {
                cameraTranslate.setX(cameraTranslate.getX() + deltaX * multiplier);
                cameraTranslate.setY(cameraTranslate.getY() + deltaY * multiplier);
            }

            if (event.isSecondaryButtonDown()) {
                cameraRotateX.setAngle(cameraRotateX.getAngle() + deltaY * multiplier);
                cameraRotateY.setAngle(cameraRotateY.getAngle() + deltaX * multiplier);
            }

            lastX = sceneX;
            lastY = sceneY;
        });

        pane.setOnScroll(event -> {
            double multiplier = 2 * cameraTranslate.getZ() / Z;
            cameraTranslate.setZ(cameraTranslate.getZ() + event.getDeltaY() * multiplier);
        });
    }

    private ChangeListener<Boolean> getTitledPaneExtendedPropertyChangeListener(TitledPane anotherTitledPane) {
        return (observable, oldValue, newValue) -> {
            if (newValue) {
                splitPane.setDividerPositions(lastDividerPosition);
            } else if (anotherTitledPane.isExpanded()){
                lastDividerPosition = splitPane.getDividerPositions()[0];
            }
        };
    }

    public ListView getListView() {
        return listView;
    }

    public PropertiesController getPropertiesController() {
        return propertiesController;
    }

    public Group getRoot() {
        return root;
    }

    public void newTwoDimensionalSetup(ActionEvent actionEvent) {
        setup = SetupBuilder.buildTwoDimensionalSetup(new Integer(new TextInputDialog().showAndWait().get()));
        visualizeSetup();
    }

    public void newThreeDimensionalSetup(ActionEvent actionEvent) {
        setup = SetupBuilder.buildThreeDimensionalSetup(new Integer(new TextInputDialog().showAndWait().get()));
        visualizeSetup();
    }

    public void loadSetup(ActionEvent actionEvent) {
        setup = SetupLoader.loadFromXMLFile(new FileChooser().showOpenDialog(null));
        visualizeSetup();
    }

    private void visualizeSetup() {
        root.getChildren().clear();
        visualization = new Visualization(setup, root, listView, propertiesController);
    }

    public void saveSetup(ActionEvent actionEvent) {
        SetupSaver.saveToXMLFile(setup, new FileChooser().showSaveDialog(null));
    }
}
