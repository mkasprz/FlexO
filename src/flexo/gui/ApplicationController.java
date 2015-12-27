package flexo.gui;

import flexo.model.Setup;
import flexo.model.persistence.SetupLoader;
import flexo.model.persistence.SetupSaver;
import flexo.model.setupbuilder.SetupBuilder;
import flexo.model.setupbuilder.ThreeDimensionalSetupBuilder;
import flexo.model.setupbuilder.TwoDimensionalSetupBuilder;
import flexo.visualization.Visualization;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Optional;

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

    public void newTwoDimensionalSetup(ActionEvent actionEvent) {
        newSetup(new TwoDimensionalSetupBuilder(), 3, 10, "two-dimensional setup", "Number of nodes:");
    }

    public void newThreeDimensionalSetup(ActionEvent actionEvent) {
        newSetup(new ThreeDimensionalSetupBuilder(), 2, 10, "three-dimensional setup", "Number of nodes in base:");
    }

    private void newSetup(SetupBuilder setupBuilder, int minimalValue, int defaultValue, String setupTypeName, String contentText) {
        TextInputDialog textInputDialog = new TextInputDialog(Integer.toString(defaultValue)); // [TODO] Think about moving this part to separate method such as 'showIntegerInputDialog'
        textInputDialog.setTitle("New " + setupTypeName);
        textInputDialog.setHeaderText("Create new " + setupTypeName);
        textInputDialog.setContentText(contentText);
        textInputDialog.setGraphic(null);

        textInputDialog.getEditor().setTextFormatter(new TextFormatter<String>(change -> {
            if (change.isAdded() && !change.getText().matches("\\d+")) { // [TODO] Could confirm if it works well
                return null;
            }
            return change;
        }));

//        textInputDialog.getEditor().setTextFormatter(new TextFormatter<>(change -> {
//            NumberFormat numberFormat = NumberFormat.getIntegerInstance();
//            String text = change.getText();
//            ParsePosition parsePosition = new ParsePosition(0);
//            numberFormat.parse(text, parsePosition);
//            if (change.isAdded() && parsePosition.getIndex() != text.length()) {
//                return null;
//            }
//            return change;
//        }));

        Node button = textInputDialog.getDialogPane().lookupButton(ButtonType.OK);
        textInputDialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0 || newValue.length() > 9 || Integer.parseInt(newValue) < minimalValue) {
                button.setDisable(true);
            } else {
                button.setDisable(false);
            }
        });

        Optional<String> integerInputDialogResult = textInputDialog.showAndWait();
        if (integerInputDialogResult.isPresent()) {
            setup = setupBuilder.build(Integer.parseInt(integerInputDialogResult.get()));
            visualizeSetup();
        }
    }

    public void loadSetup(ActionEvent actionEvent) {
        File file = new FileChooser().showOpenDialog(null);
        if (file != null) {
            try {
                setup = SetupLoader.loadFromXMLFile(file);
                visualizeSetup();
            } catch (JAXBException | RuntimeException e) {
                new Alert(Alert.AlertType.ERROR, "Error while opening file", ButtonType.OK).show();
            }
        }
    }

    private void visualizeSetup() {
        root.getChildren().clear();
        visualization = new Visualization(setup, root, listView, propertiesController);
    }

    public void saveSetup(ActionEvent actionEvent) {
        File file = new FileChooser().showSaveDialog(null);
        if (file != null) {
            try {
                SetupSaver.saveToXMLFile(setup, file);
            } catch (JAXBException e) {
                new Alert(Alert.AlertType.ERROR, "Error while saving file", ButtonType.OK);
            }
        }
    }
}
