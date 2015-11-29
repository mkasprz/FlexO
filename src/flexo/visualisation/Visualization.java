package flexo.visualisation;

import flexo.deformationcalculator.DeformationCalculator;
import flexo.gui.PropertiesController;
import flexo.model.Connection;
import flexo.model.Setup;
import flexo.model.SimpleNode;
import flexo.model.TypicalNode;
import flexo.scenebuilder.SceneBuilder;
import flexo.scenebuilder.TwoDimensionBuilder;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by kcpr on 12.06.15.
 */
public class Visualization {

    final PhongMaterial blackMaterial = new PhongMaterial(Color.BLACK);
    final PhongMaterial redMaterial = new PhongMaterial(Color.RED);
    final PhongMaterial greyMaterial = new PhongMaterial(Color.GREY);

    private Map<SimpleNode, Sphere> nodesMap;
    private Map<Connection, Cylinder> connectionsMap;

    private DeformationCalculator deformationCalculator;
    private int radius = 20;
    private int visualisationMultiplicant = 10;
    private Sphere selectedSphere;
    private Material selectedSphereMaterial;

    Group root;
    Setup scene;
    PropertiesController propertiesController;

    public Visualization(Group root, PropertiesController propertiesController) {
        this.root = root;
        this.propertiesController = propertiesController;

        SceneBuilder builder = new TwoDimensionBuilder();
        builder.setNodesNumber(10);
        Setup scene = builder.build();

        this.scene = scene;

        deformationCalculator = new DeformationCalculator(scene);
        propertiesController.setVisualization(this);

        List<Node> visualisedObjects = createVisualisedObjects(scene, radius, propertiesController);

        root.getChildren().addAll(visualisedObjects);

        PointLight pointLight = new PointLight(Color.ANTIQUEWHITE);
        pointLight.setTranslateX(15);
        pointLight.setTranslateY(-10);
        pointLight.setTranslateZ(-100);
//        root.getChildren().add(pointLight);
    }

    private List<Node> createVisualisedObjects(Setup setup, int radius, PropertiesController propertiesController) {
        blackMaterial.setSpecularColor(Color.WHITE);
        redMaterial.setSpecularColor(Color.WHITE);

        nodesMap = new HashMap<>();
        List<Node> visibleObjects = new LinkedList<>();

        for (Connection connection : setup.getConnections()) {
            TypicalNode node1 = connection.getTypicalNode1();
            Sphere sphere1 = addSphere(visibleObjects, node1, propertiesController, radius, blackMaterial);
            TypicalNode node2 = connection.getTypicalNode2();
            Sphere sphere2 = addSphere(visibleObjects, node2, propertiesController, radius, blackMaterial);

            addConnection(visibleObjects, sphere1, sphere2, radius/3, greyMaterial);
        }

        greyMaterial.setSpecularColor(Color.WHITE);
        SimpleNode centralNode = setup.getCentralNode();
        addSphere(visibleObjects, centralNode, propertiesController, radius, greyMaterial);

        return visibleObjects;
    }

    private Sphere addSphere(List<Node> visibleObjects, SimpleNode simpleNode, PropertiesController propertiesController, int radius, Material material) {
        if (!nodesMap.containsKey(simpleNode)) {
            Sphere sphere = createSphere(simpleNode, radius, material);
            sphere.setOnMouseClicked(event -> {
                selectSphere(sphere, material);
                propertiesController.setSelectedNode(simpleNode);
            });
            nodesMap.put(simpleNode, sphere);
            visibleObjects.add(sphere);
            return sphere;
        } else {
            return nodesMap.get(simpleNode);
        }
    }

    private Sphere createSphere(SimpleNode simpleNode, int radius, Material material) {
        Sphere sphere = new Sphere(radius);
        sphere.setMaterial(material);
        setSphereTranslate(sphere, simpleNode, visualisationMultiplicant);
        return sphere;
    }

    private void addConnection(List<Node> visibleObjects, Sphere sphere1, Sphere sphere2, int radius, Material material) {
        Point3D point1 = new Point3D(sphere1.getTranslateX(), sphere1.getTranslateY(), sphere1.getTranslateZ());
        Point3D point2 = new Point3D(sphere2.getTranslateX(), sphere2.getTranslateY(), sphere2.getTranslateZ());

        Point3D middlePoint = point1.midpoint(point2);

        Point3D subtractionResult = point1.subtract(point2);
        Point3D axis = subtractionResult.crossProduct(Rotate.Y_AXIS);

        Cylinder cylinder = new Cylinder(radius, point1.distance(point2));
        cylinder.setMaterial(material);
        cylinder.setTranslateX(middlePoint.getX());
        cylinder.setTranslateY(middlePoint.getY());
        cylinder.setTranslateZ(middlePoint.getZ());
        cylinder.setRotationAxis(axis);
        cylinder.setRotate(-subtractionResult.angle(Rotate.Y_AXIS));

        visibleObjects.add(cylinder);
    }

    private void selectSphere(Sphere sphere, Material sphereMaterial) {
        if (selectedSphere != null) {
            selectedSphere.setMaterial(selectedSphereMaterial);
        }
        sphere.setMaterial(redMaterial); // [TODO] Find out why 'sphere.setEffect()' doesn't seem to work
        selectedSphere = sphere;
        selectedSphereMaterial = sphereMaterial;
    }

    public void recalculateDeformation() {
        deformationCalculator.recalculateDeformation();
        refreshVisualization(visualisationMultiplicant);
    }

    private void refreshVisualization(int multiplier) {
//        [TODO] Refresh every connection position nicely
//        for (Map.Entry<SimpleNode, Sphere> nodeEntry : nodesMap.entrySet()) {
//            setSphereTranslate(nodeEntry.getValue(), nodeEntry.getKey(), multiplier);
//        }
        List<Node> visualisedObjects = createVisualisedObjects(scene, radius, propertiesController);

        root.getChildren().clear();
        root.getChildren().addAll(visualisedObjects);
    }

    private void setSphereTranslate(Sphere sphere, SimpleNode simpleNode, int multiplicant) {
        sphere.setTranslateX(simpleNode.getX() * multiplicant);
        sphere.setTranslateY(simpleNode.getY() * multiplicant);
        sphere.setTranslateZ(simpleNode.getZ() * multiplicant);
    }
}
