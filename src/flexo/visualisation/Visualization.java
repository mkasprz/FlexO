package flexo.visualisation;

import flexo.deformationcalculator.DeformationCalculator;
import flexo.gui.PropertiesController;
import flexo.model.Setup;
import flexo.model.SimpleNode;
import flexo.model.TypicalNode;
import flexo.modelconverter.ModelConverter;
import flexo.scenebuilder.SceneBuilder;
import flexo.scenebuilder.TwoDimensionBuilder;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

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

    private Sphere centralSphere;

    private DeformationCalculator deformationCalculator;
    private int radius = 20;
    private int visualisationMultiplicant = 10;
    private Sphere selectedSphere;

    public Visualization(Group root, PropertiesController propertiesController) {
        SceneBuilder builder = new TwoDimensionBuilder();
        builder.setNodesNumber(10);
        Setup scene = builder.build();

        deformationCalculator = new DeformationCalculator(scene);
        propertiesController.setVisualization(this);

        List<javafx.scene.Node> visualisedObjects = createVisualisedObjects(scene, radius, propertiesController);

        root.getChildren().addAll(visualisedObjects);

        PointLight pointLight = new PointLight(Color.ANTIQUEWHITE);
        pointLight.setTranslateX(15);
        pointLight.setTranslateY(-10);
        pointLight.setTranslateZ(-100);

//        root.getChildren().add(pointLight);

    }

    private List<javafx.scene.Node> createVisualisedObjects(Setup scene, int radius, PropertiesController propertiesController) {
        List<TypicalNode> list = ModelConverter.convert(scene);

        blackMaterial.setSpecularColor(Color.WHITE);
        redMaterial.setSpecularColor(Color.WHITE);

        nodesMap = new HashMap<>();
        List<javafx.scene.Node> visibleObjects = new LinkedList<>();

        for (TypicalNode typicalNode : list) {
            Sphere sphere = createSphere(typicalNode, radius, blackMaterial);

            sphere.setOnMouseClicked(event -> {
                selectSphere(sphere);
                propertiesController.setSelectedNode(typicalNode);
            });

            nodesMap.put(typicalNode, sphere);
            visibleObjects.add(sphere);
        }

        greyMaterial.setSpecularColor(Color.WHITE);

        SimpleNode centralNode = scene.getCentralNode();

        centralSphere = createSphere(centralNode, radius, greyMaterial);

        centralSphere.setOnMouseClicked(event -> {
            //TODO : at this spot the objects in scene should be moved, they should be rerendered
            selectSphere(centralSphere);
            propertiesController.setSelectedNode(centralNode);
        });

        nodesMap.put(centralNode, centralSphere);
        visibleObjects.add(centralSphere);

        return visibleObjects;
    }

    private Sphere createSphere(SimpleNode simpleNode, int radius, Material material) {
        Sphere sphere = new Sphere(radius);
        sphere.setMaterial(material);
        setSphereTranslate(sphere, simpleNode, visualisationMultiplicant);
        return sphere;
    }

    private void selectSphere(Sphere sphere) {
        if (selectedSphere == centralSphere) {
            selectedSphere.setMaterial(greyMaterial);
        } else if (selectedSphere != null) {
            selectedSphere.setMaterial(blackMaterial);
        }
        selectedSphere = sphere;
        sphere.setMaterial(redMaterial); // [TODO] Find out why 'sphere.setEffect()' doesn't seem to work
    }

    public void recalculateDeformation() {
        deformationCalculator.recalculateDeformation();
        refreshVisualization(visualisationMultiplicant);
    }

    private void refreshVisualization(int multiplier) {
        for (Map.Entry<SimpleNode, Sphere> nodeEntry : nodesMap.entrySet()) {
            setSphereTranslate(nodeEntry.getValue(), nodeEntry.getKey(), multiplier);
        }
    }

    private void setSphereTranslate(Sphere sphere, SimpleNode simpleNode, int multiplicant) {
        sphere.setTranslateX(simpleNode.getX() * multiplicant);
        sphere.setTranslateY(simpleNode.getY() * multiplicant);
        sphere.setTranslateZ(simpleNode.getZ() * multiplicant);
    }
}
