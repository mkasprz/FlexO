package flexo.visualisation;

import flexo.deformationcalculator.DeformationCalculator;
import flexo.gui.PropertiesController;
import flexo.model.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kcpr on 12.06.15.
 */
public class Visualization {

    final PhongMaterial blackMaterial = new PhongMaterial(Color.BLACK);
    final PhongMaterial redMaterial = new PhongMaterial(Color.RED);
    final PhongMaterial greyMaterial = new PhongMaterial(Color.GREY);

//    private Map<SimpleNode, Sphere> nodesMap;
//    private Map<Connection, Cylinder> connectionsMap;

    private DeformationCalculator deformationCalculator;
    private int radius = 20;
    private int visualisationMultiplier = 100;
    private Shape3D selectedElement;
    private Material selectedElementMaterial;

    Group root;
    Setup setup;
    PropertiesController propertiesController;
    List<VisualizedConnection> visualizedConnections; // [TODO] Follow instructions for creating nice ListView and then delete this as useless

    public Visualization(Group root, ListView listView, PropertiesController propertiesController) {
        this.root = root;
        this.propertiesController = propertiesController;

//        Setup setup = new TwoDimensionalSetup(10);
        Setup setup = new ThreeDimensionalSetup(10); // [TODO] Move somewhere and pass as argument

        this.setup = setup;

        deformationCalculator = new DeformationCalculator(setup);
        propertiesController.setVisualization(this);

        List<Node> visualisedObjects = createVisualisedObjects(setup, radius, listView, propertiesController);
        root.getChildren().addAll(visualisedObjects);

        listView.setOnMouseClicked(event -> { // [TODO] Find better place to do this, this type of listener isn't the best by the way
            VisualizedConnection visualizedConnection = visualizedConnections.get(listView.getSelectionModel().getSelectedIndex());
            selectElement(visualizedConnection, visualizedConnection.getMaterial());
            propertiesController.setSelectedConnection(visualizedConnection.getConnection());
        });
    }

    private List<Node> createVisualisedObjects(Setup setup, int radius, ListView listView, PropertiesController propertiesController) {
        blackMaterial.setSpecularColor(Color.WHITE);
        greyMaterial.setSpecularColor(Color.WHITE);
        redMaterial.setSpecularColor(Color.WHITE);

        visualizedConnections = new LinkedList<>();
        List<Node> visibleObjects = new LinkedList<>();
        List<SimpleNode> nodes = new LinkedList<>();
        for (Connection connection : setup.getConnections()) {
            TypicalNode typicalNode1 = connection.getTypicalNode1();
            createVisualizedNode(nodes, visibleObjects, typicalNode1, radius, blackMaterial, propertiesController);
            TypicalNode typicalNode2 = connection.getTypicalNode2();
            createVisualizedNode(nodes, visibleObjects, typicalNode2, radius, blackMaterial, propertiesController);

            createVisualizedConnection(visualizedConnections, visibleObjects, connection, radius/3, propertiesController, greyMaterial);
            listView.getItems().add("Connection between nodes " + typicalNode1.getId() + " and " + typicalNode2);
        }

        createVisualizedNode(nodes, visibleObjects, setup.getCentralNode(), radius, greyMaterial, propertiesController);

        return visibleObjects;
    }

    private void createVisualizedNode(List<SimpleNode> nodes, List<Node> visibleObjects, TypicalNode node1, int radius, Material material, PropertiesController propertiesController) {
        if (!nodes.contains(node1)) {
            VisualizedNode visualizedNode = new VisualizedNode(node1, radius, material, visualisationMultiplier);
            visibleObjects.add(visualizedNode);
            visualizedNode.setOnMouseClicked(event -> {
                selectElement(visualizedNode, material);
                propertiesController.setSelectedNode(node1);
            });
            nodes.add(node1);
        }
    }

    private void createVisualizedConnection(List visualizedConnections, List<Node> visibleObjects, Connection connection, int radius, PropertiesController propertiesController, Material material) {
        VisualizedConnection visualizedConnection = new VisualizedConnection(connection, radius, material, visualisationMultiplier);
        visualizedConnections.add(visualizedConnection);
        visibleObjects.add(visualizedConnection);
        visualizedConnection.setOnMouseClicked(event -> {
            selectElement(visualizedConnection, material);
            propertiesController.setSelectedConnection(connection);
        });
    }

    private void selectElement(Shape3D element, Material elementMaterial) {
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

}
