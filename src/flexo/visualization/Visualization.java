package flexo.visualization;

import flexo.deformationcalculator.DeformationCalculator;
import flexo.gui.PropertiesController;
import flexo.model.Connection;
import flexo.model.Setup;
import flexo.model.SimpleNode;
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

    final PhongMaterial blackMaterial = new PhongMaterial(Color.BLACK);
    final PhongMaterial redMaterial = new PhongMaterial(Color.RED);
    final PhongMaterial greyMaterial = new PhongMaterial(Color.GREY);

    private DeformationCalculator deformationCalculator;
    private int radius = 20;
    private int visualisationMultiplier = 100;
    private Shape3D selectedElement;
    private Material selectedElementMaterial;

    Group root;
    PropertiesController propertiesController;
    List<VisualizedConnection> visualizedConnections;

    public Visualization(Setup setup, Group root, ListView listView, PropertiesController propertiesController) {
        this.root = root;
        this.propertiesController = propertiesController;

        deformationCalculator = new DeformationCalculator(setup);

        List<Node> visualisedObjects = createVisualisedObjects(setup, radius, listView, propertiesController);
        root.getChildren().addAll(visualisedObjects);
    }

    private List<Node> createVisualisedObjects(Setup setup, int radius, ListView listView, PropertiesController propertiesController) {
        blackMaterial.setSpecularColor(Color.WHITE);
        greyMaterial.setSpecularColor(Color.WHITE);
        redMaterial.setSpecularColor(Color.WHITE);

        visualizedConnections = new LinkedList<>();
        List<SimpleNode> nodes = new LinkedList<>();
        List<Node> visibleObjects = new LinkedList<>();
        ObservableList listViewItems = listView.getItems();
        for (Connection connection : setup.getConnections()) {
            TypicalNode typicalNode1 = connection.getTypicalNode1();
            createVisualizedNode(nodes, visibleObjects, typicalNode1, radius, blackMaterial, listView, propertiesController);
            TypicalNode typicalNode2 = connection.getTypicalNode2();
            createVisualizedNode(nodes, visibleObjects, typicalNode2, radius, blackMaterial, listView, propertiesController);

            createVisualizedConnection(visibleObjects, connection, radius/3, greyMaterial, listView, propertiesController);
            listViewItems.add(typicalNode1.getId() + " - " + typicalNode2.getId());
        }

        createVisualizedNode(nodes, visibleObjects, setup.getCentralNode(), radius, greyMaterial, listView, propertiesController);

        return visibleObjects;
    }

    private void createVisualizedNode(List<SimpleNode> nodes, List<Node> visibleObjects, TypicalNode node1, int radius, Material material, ListView listView, PropertiesController propertiesController) {
        if (!nodes.contains(node1)) {
            VisualizedNode visualizedNode = new VisualizedNode(node1, radius, material, visualisationMultiplier);
            visibleObjects.add(visualizedNode);
            visualizedNode.setOnMouseClicked(event -> {
                selectElement(visualizedNode, material);
                propertiesController.setSelectedNode(node1);
                listView.getSelectionModel().clearSelection();
            });
            nodes.add(node1);
        }
    }

    private void createVisualizedConnection(List<Node> visibleObjects, Connection connection, int radius, Material material, ListView listView, PropertiesController propertiesController) {
        VisualizedConnection visualizedConnection = new VisualizedConnection(connection, radius, material, visualisationMultiplier);
        visualizedConnections.add(visualizedConnection);
        int index = visualizedConnections.size() - 1;
        visibleObjects.add(visualizedConnection);
        visualizedConnection.setOnMouseClicked(event -> {
            selectElement(visualizedConnection, material);
            propertiesController.setSelectedConnection(connection);
            listView.getSelectionModel().select(index);
        });
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
