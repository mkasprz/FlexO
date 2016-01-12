package flexo.visualization;

import flexo.deformationcalculator.DeformationCalculator;
import flexo.model.Connection;
import flexo.model.Setup;
import flexo.model.TypicalNode;
import javafx.scene.Group;
import javafx.scene.Node;
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

    public Visualization(Setup setup, Group root, SelectionObserver selectionObserver) {
        this.root = root;

        deformationCalculator = new DeformationCalculator(setup);

        List<Node> visualisedObjects = createVisualisedObjects(setup, radius, selectionObserver);
        root.getChildren().addAll(visualisedObjects);
    }

    private List<Node> createVisualisedObjects(Setup setup, int radius, SelectionObserver selectionObserver) {
        blackMaterial.setSpecularColor(Color.WHITE);
        greyMaterial.setSpecularColor(Color.WHITE);
        darkerGreyMaterial.setSpecularColor(Color.WHITE);
        redMaterial.setSpecularColor(Color.WHITE);

        visualizedConnections = new LinkedList<>();
        List<Node> visibleObjects = new LinkedList<>();

        for (TypicalNode typicalNode : setup.getImmovableNodes()) {
            visibleObjects.add(createVisualizedNode(typicalNode, radius, blackMaterial, selectionObserver));
        }

        for (TypicalNode typicalNode :  setup.getTypicalNodes()) {
            visibleObjects.add(createVisualizedNode(typicalNode, radius, darkerGreyMaterial, selectionObserver));
        }

        for (Connection connection : setup.getConnections()) {
            visibleObjects.add(createVisualizedConnection(connection, radius / 3, greyMaterial, selectionObserver));
        }

        visibleObjects.add(createVisualizedNode(setup.getCentralNode(), radius, greyMaterial, selectionObserver));

        return visibleObjects;
    }

private VisualizedNode createVisualizedNode(TypicalNode typicalNode, int radius, Material material, SelectionObserver selectionObserver) {
        VisualizedNode visualizedNode = new VisualizedNode(typicalNode, radius, material, visualisationMultiplier);
        visualizedNode.setOnMouseClicked(event -> {
            selectElement(visualizedNode, material);
            selectionObserver.selectedTypicalNode(typicalNode);
        });
        return visualizedNode;
    }

    private VisualizedConnection createVisualizedConnection(Connection connection, int radius, Material material, SelectionObserver selectionObserver) {
        VisualizedConnection visualizedConnection = new VisualizedConnection(connection, radius, material, visualisationMultiplier);
        visualizedConnection.setOnMouseClicked(event -> {
            selectElement(visualizedConnection, material);
            selectionObserver.selectedConnection(connection);
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

    public void recalculateDeformation() {
        deformationCalculator.recalculateDeformation();
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
