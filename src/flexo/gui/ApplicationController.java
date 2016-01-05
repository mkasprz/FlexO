package flexo.gui;

import flexo.model.Connection;
import flexo.model.Setup;
import flexo.model.TypicalNode;
import flexo.model.persistence.SetupLoader;
import flexo.model.persistence.SetupSaver;
import flexo.model.setupbuilder.SetupBuilder;
import flexo.model.setupbuilder.ThreeDimensionalSetupBuilder;
import flexo.model.setupbuilder.TwoDimensionalSetupBuilder;
import flexo.visualization.Visualization;
import flexo.visualization.SelectionObserver;
import flexo.visualization.VisualizedConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ApplicationController implements SelectionObserver {

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private MenuItem saveAsMenuItem;

    @FXML
    private MenuItem exportMenuItem;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TitledPane listViewTitledPane;

    @FXML
    private TitledPane propertiesTitledPane;

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

    String filePath;

    @FXML
    void initialize() {
        listViewTitledPane.expandedProperty().addListener(getTitledPaneExtendedPropertyChangeListener(propertiesTitledPane));
        propertiesTitledPane.expandedProperty().addListener(getTitledPaneExtendedPropertyChangeListener(listViewTitledPane));

        listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int index = newValue.intValue();
            if (index != -1) {
                VisualizedConnection visualizedConnection = visualization.getVisualizedConnections().get(index);
                if (visualizedConnection != visualization.getSelectedElement()) {
                    visualization.selectElement(visualizedConnection, visualizedConnection.getMaterial());
                    propertiesController.setSelectedConnection(visualizedConnection.getConnection());
                } else {
                    listView.scrollTo(index);
                }
            }
        });

        listView.setCellFactory(param -> new ListCell<Connection>() {
            @Override
            protected void updateItem(Connection item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getTypicalNode1().getId() + " - " + item.getTypicalNode2().getId());
                }
            }
        });

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
            } else if (anotherTitledPane.isExpanded()) {
                lastDividerPosition = splitPane.getDividerPositions()[0];
            }
        };
    }

    @FXML
    private void newTwoDimensionalSetup() {
        newSetup(new TwoDimensionalSetupBuilder(), 3, 10, "two-dimensional setup", "Number of nodes:");
    }

    @FXML
    private void newThreeDimensionalSetup() {
        newSetup(new ThreeDimensionalSetupBuilder(), 2, 10, "three-dimensional setup", "Number of nodes in base:");
    }

    private void newSetup(SetupBuilder setupBuilder, int minimalValue, int defaultValue, String setupTypeName, String contentText) {
        TextInputDialog textInputDialog = new TextInputDialog(Integer.toString(defaultValue)); // [TODO] Think about moving this part to separate method such as 'showIntegerInputDialog'
        textInputDialog.setTitle("New " + setupTypeName);
        textInputDialog.setHeaderText("Create new " + setupTypeName);
        textInputDialog.setContentText(contentText);
        textInputDialog.setGraphic(null);

        textInputDialog.getEditor().setTextFormatter(new TextFormatter<String>(change -> {
            if (change.isAdded() && !change.getText().matches("\\d+")) {
                return null;
            }
            return change;
        }));

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
            enableMenuItems();
        }
    }

    @FXML
    private void loadSetup() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML files", "*.xml", "*.XML"), new FileChooser.ExtensionFilter("All files", "*"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                setup = SetupLoader.loadFromXMLFile(file);
                visualizeSetup();
                filePath = file.getPath();
                enableMenuItems();
            } catch (JAXBException | RuntimeException e) {
                new Alert(Alert.AlertType.ERROR, "Error while opening file", ButtonType.OK).show();
            }
        }
    }

    private void enableMenuItems() {
        saveMenuItem.setDisable(false);
        saveAsMenuItem.setDisable(false);
        exportMenuItem.setDisable(false);
    }

    private void visualizeSetup() {
        root.getChildren().clear();
        listView.getItems().setAll(setup.getConnections());
        visualization = new Visualization(setup, root, this);
        propertiesController.setVisualization(visualization);
    }

    @FXML
    private void saveSetup() {
        if (filePath != null) {
            try {
                SetupSaver.saveToXMLFile(setup, new File(filePath));
            } catch (JAXBException e) {
                new Alert(Alert.AlertType.ERROR, "Error while saving file", ButtonType.OK);
            }
        } else {
            saveSetupAs();
        }
    }

    @FXML
    private void saveSetupAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Setup.xml");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML files", "*.xml", "*.XML"), new FileChooser.ExtensionFilter("All files", "*"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                SetupSaver.saveToXMLFile(setup, file);
                filePath = file.getPath();
            } catch (JAXBException e) {
                new Alert(Alert.AlertType.ERROR, "Error while saving file", ButtonType.OK);
            }
        }
    }

    @FXML
    private void exportSetup() {
        File file = new FileChooser().showSaveDialog(null);
        if (file != null) {
            try {
                SetupSaver.exportToOBJFile(setup, file);
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error while exporting file", ButtonType.OK);
            }
        }
    }

    @FXML
    private void quit() {
        Platform.exit();
    }

    @Override
    public void selectedTypicalNode(TypicalNode typicalNode) {
        propertiesController.setSelectedNode(typicalNode);
        listView.getSelectionModel().clearSelection();
    }

    @Override
    public void selectedConnection(Connection connection) {
        propertiesController.setSelectedConnection(connection);
        listView.getSelectionModel().select(connection);
    }
}
