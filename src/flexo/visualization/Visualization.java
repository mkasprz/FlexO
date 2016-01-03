package flexo.visualization;

import flexo.deformationcalculator.DeformationCalculator;
import flexo.gui.PropertiesController;
import flexo.model.Connection;
import flexo.model.Setup;
import flexo.model.TypicalNode;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;

import java.util.LinkedList;
import java.util.List;

public class Visualization {

    private final PhongMaterial blackMaterial = new PhongMaterial(Color.BLACK);
    private final PhongMaterial redMaterial = new PhongMaterial(Color.RED);
    private final PhongMaterial greyMaterial = new PhongMaterial(Color.GREY);
    private final PhongMaterial darkerGreyMaterial = new PhongMaterial(Color.gray(0.15));

    private DeformationCalculator deformationCalculator;
    private int radius = 20;
    private int visualisationMultiplier = 100;
    private Shape3D selectedElement;
    private Material selectedElementMaterial;

    private Group root;
    private List<VisualizedConnection> visualizedConnections;

    public Visualization(Setup setup, Group root, ListView listView, PropertiesController propertiesController) {
        this.root = root;

        deformationCalculator = new DeformationCalculator(setup);

        List<Node> visualisedObjects = createVisualisedObjects(setup, radius, listView, propertiesController);
        root.getChildren().addAll(visualisedObjects);
    }

    private List<Node> createVisualisedObjects(Setup setup, int radius, ListView listView, PropertiesController propertiesController) {
        blackMaterial.setSpecularColor(Color.WHITE);
        greyMaterial.setSpecularColor(Color.WHITE);
        darkerGreyMaterial.setSpecularColor(Color.WHITE);
        redMaterial.setSpecularColor(Color.WHITE);

        visualizedConnections = new LinkedList<>();
        List<Node> visibleObjects = new LinkedList<>();

        for (TypicalNode typicalNode : setup.getImmovableNodes()) {
            visibleObjects.add(createVisualizedNode(typicalNode, radius, blackMaterial, listView, propertiesController));
        }

        for (TypicalNode typicalNode :  setup.getTypicalNodes()) {
            visibleObjects.add(createVisualizedNode(typicalNode, radius, darkerGreyMaterial, listView, propertiesController));
        }

        ObservableList listViewItems = listView.getItems();
        for (Connection connection : setup.getConnections()) {
            visibleObjects.add(createVisualizedConnection(connection, radius / 3, greyMaterial, listView, propertiesController));
            listViewItems.add(connection.getTypicalNode1().getId() + " - " + connection.getTypicalNode2().getId());
        }

        visibleObjects.add(createVisualizedNode(setup.getCentralNode(), radius, greyMaterial, listView, propertiesController));

        return visibleObjects;
    }

private VisualizedNode createVisualizedNode(TypicalNode typicalNode, int radius, Material material, ListView listView, PropertiesController propertiesController) {
        VisualizedNode visualizedNode = new VisualizedNode(typicalNode, radius, material, visualisationMultiplier);
        visualizedNode.setOnMouseClicked(event -> {
            selectElement(visualizedNode, material);
            propertiesController.setSelectedNode(typicalNode);
            listView.getSelectionModel().clearSelection();
        });
        return visualizedNode;
    }

    private VisualizedConnection createVisualizedConnection(Connection connection, int radius, Material material, ListView listView, PropertiesController propertiesController) {
        VisualizedConnection visualizedConnection = new VisualizedConnection(connection, radius, material, visualisationMultiplier);
        int index = visualizedConnections.size();
        visualizedConnection.setOnMouseClicked(event -> {
            selectElement(visualizedConnection, material);
            propertiesController.setSelectedConnection(connection);
            listView.getSelectionModel().select(index);
        });
        visualizedConnections.add(visualizedConnection);
        return visualizedConnection;
    }

    public void selectElement(Shape3D element, Material elementMaterial) {
        if (selectedElement != null) {
            selectedElement.setMaterial(selectedElementMaterial);
        }
        element.setMaterial(redMaterial); // [TODO] Find out why 'sphere.setEffect()' doesn't seem to work
        selectedElement = element;
        selectedElementMaterial = elementMaterial;
    }

    public void recalculateDeformation(int coord, double value) {
        //this is very stupid, but just for presentation
        //TODO : implement a better way of starting calculations
        deformationCalculator.recalculateDeformation(coord, value);
        refreshVisualization();
    }

    private void refreshVisualization() {
        for (Node visualizedElement : root.getChildren()) {
            ((VisualizedElement)visualizedElement).refresh(visualisationMultiplier);
        }
    }

    public Shape3D getSelectedElement() {
        return selectedElement;
    }

    public List<VisualizedConnection> getVisualizedConnections() {
        return visualizedConnections;
    }
}
