package flexo.visualisation;

import flexo.deformationcalculator.DeformationCalculator;
import flexo.gui.PropertiesController;
import flexo.model.*;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
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
    private int visualisationMultiplicant = 100;
    private Shape3D selectedElement;
    private Material selectedElementMaterial;

    Group root;
    Setup setup;
    PropertiesController propertiesController;

    public Visualization(Group root, PropertiesController propertiesController) {
        this.root = root;
        this.propertiesController = propertiesController;

//        Setup setup = new TwoDimensionalSetup(10);
        Setup setup = new ThreeDimensionalSetup(10);

        this.setup = setup;

        deformationCalculator = new DeformationCalculator(setup);
        propertiesController.setVisualization(this);

        List<Node> visualisedObjects = createVisualisedObjects(setup, radius, propertiesController);

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
        connectionsMap = new HashMap<>();
        List<Node> visibleObjects = new LinkedList<>();

        for (Connection connection : setup.getConnections()) {
            TypicalNode node1 = connection.getTypicalNode1();
            Sphere sphere1 = addSphere(visibleObjects, node1, propertiesController, radius, blackMaterial);
            TypicalNode node2 = connection.getTypicalNode2();
            Sphere sphere2 = addSphere(visibleObjects, node2, propertiesController, radius, blackMaterial);

            Cylinder cylinder = createCylinder(connection, sphere1, sphere2, radius/3, greyMaterial);
            connectionsMap.put(connection, cylinder);
            visibleObjects.add(cylinder);
        }

        greyMaterial.setSpecularColor(Color.WHITE);
        TypicalNode centralNode = setup.getCentralNode();
        addSphere(visibleObjects, centralNode, propertiesController, radius, greyMaterial);

        return visibleObjects;
    }

    private Sphere addSphere(List<Node> visibleObjects, TypicalNode simpleNode, PropertiesController propertiesController, int radius, Material material) {
        if (!nodesMap.containsKey(simpleNode)) {
            Sphere sphere = createSphere(simpleNode, radius, material);
            sphere.setOnMouseClicked(event -> {
                selectElement(sphere, material);
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

    private Cylinder createCylinder(Connection connection, Sphere sphere1, Sphere sphere2, int radius, Material material) {
        Point3D point1 = new Point3D(sphere1.getTranslateX(), sphere1.getTranslateY(), sphere1.getTranslateZ());
        Point3D point2 = new Point3D(sphere2.getTranslateX(), sphere2.getTranslateY(), sphere2.getTranslateZ());

        Cylinder cylinder = new Cylinder(radius, point1.distance(point2));
        cylinder.setMaterial(material);
        setCylinderPosition(cylinder, point1, point2);

        cylinder.setOnMouseClicked(event -> {
            selectElement(cylinder, greyMaterial);
            propertiesController.setSelectedConnection(connection);
        });

        return cylinder;
    }

    private void setCylinderPosition(Cylinder cylinder, Point3D point1, Point3D point2) {
        Point3D middlePoint = point1.midpoint(point2);
        cylinder.setTranslateX(middlePoint.getX());
        cylinder.setTranslateY(middlePoint.getY());
        cylinder.setTranslateZ(middlePoint.getZ());

        Point3D displacementVector = point1.subtract(point2);
        cylinder.setRotationAxis(displacementVector.crossProduct(Rotate.Y_AXIS));
        cylinder.setRotate(-displacementVector.angle(Rotate.Y_AXIS));
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
        refreshVisualization(visualisationMultiplicant);
    }

    private void refreshVisualization(int multiplier) {
        for (Map.Entry<SimpleNode, Sphere> nodeEntry : nodesMap.entrySet()) {
            setSphereTranslate(nodeEntry.getValue(), nodeEntry.getKey(), multiplier);
        }

        for (Map.Entry<Connection, Cylinder> connectionEntry : connectionsMap.entrySet()) {
            Connection connection = connectionEntry.getKey();
            SimpleNode simpleNode1 = connection.getTypicalNode1();
            SimpleNode simpleNode2 = connection.getTypicalNode2();
            Point3D point1 = new Point3D(simpleNode1.getX() * visualisationMultiplicant, simpleNode1.getY() * visualisationMultiplicant, simpleNode1.getZ() * visualisationMultiplicant);
            Point3D point2 = new Point3D(simpleNode2.getX() * visualisationMultiplicant, simpleNode2.getY() * visualisationMultiplicant, simpleNode2.getZ() * visualisationMultiplicant);
            Cylinder cylinder = connectionEntry.getValue();
            cylinder.setHeight(point1.distance(point2));
            setCylinderPosition(cylinder, point1, point2);
        }
    }

    private void setSphereTranslate(Sphere sphere, SimpleNode simpleNode, int multiplicant) {
        sphere.setTranslateX(simpleNode.getX() * multiplicant);
        sphere.setTranslateY(simpleNode.getY() * multiplicant);
        sphere.setTranslateZ(simpleNode.getZ() * multiplicant);
    }
}
