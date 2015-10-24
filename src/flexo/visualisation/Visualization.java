package flexo.visualisation;

import flexo.deformationcalculator.DeformationCalculator;
import flexo.gui.SpherePropertiesController;
import flexo.model.Scene;
import flexo.model.SimpleNode;
import flexo.model.TypicalNode;
import flexo.modelconverter.ModelConverter;
import flexo.scenebuilder.SceneBuilder;
import flexo.scenebuilder.TwoDimensionBuilder;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by kcpr on 12.06.15.
 */
public class Visualization {

    public static final int X = 450;
    public static final int Y = 150;
    public static final int Z = -2000;

    final PhongMaterial blackMaterial = new PhongMaterial(Color.BLACK);
    final PhongMaterial redMaterial = new PhongMaterial(Color.RED);

    Map<SimpleNode, Sphere> nodesMap;

    double lastX, lastY, lastXTranslation, lastYTranslation;
    double lastZTranslation = -Z;
    DeformationCalculator deformationCalculator;
    int radius = 20;
    int visualisationMultiplicant = 10;
    Sphere selectedSphere;

    public Visualization(Pane pane, SubScene subScene, SpherePropertiesController spherePropertiesController) {
        SceneBuilder builder = new TwoDimensionBuilder();
        builder.setNodesNumber(10);
        Scene scene = builder.build();

        deformationCalculator = new DeformationCalculator(scene);
        spherePropertiesController.setVisualization(this);

        List<javafx.scene.Node> visualisedObjects = createVisualisedObjects(scene, radius, spherePropertiesController);

        final Group root = new Group(visualisedObjects);
        root.getTransforms().add(new Translate());

        // [TODO] Make depth buffer work
        // SubScene subScene = new SubScene(root, 0, 0, true, SceneAntialiasing.BALANCED);
        // subScene.heightProperty().bind(pane.heightProperty());
        // subScene.widthProperty().bind(pane.widthProperty());
        // pane.getChildren().set(0, subScene);
         System.out.println(subScene.isDepthBuffer());

        PerspectiveCamera camera = new PerspectiveCamera(true);

        camera.setFarClip(Double.MAX_VALUE);
        camera.setNearClip(Double.MIN_VALUE);

        camera.getTransforms().add(new Translate(X, Y, Z));

        subScene.setCamera(camera);

        subScene.setRoot(root);

        pane.setOnMousePressed(event -> {
            if (event.isMiddleButtonDown()) {
                lastXTranslation = 0;
                lastYTranslation = 0;
                lastZTranslation = -Z;
                camera.getTransforms().set(0, new Translate(X, Y, Z));
                spherePropertiesController.setVisible(false);
                selectedSphere = null;
            } else {
                lastX = event.getSceneX();
                lastY = event.getSceneY();
            }
        });

        pane.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                double deltaX = lastX - event.getSceneX();
                double deltaY = lastY - event.getSceneY();

                Transform transform = camera.getTransforms().get(0);
                transform = transform.createConcatenation(new Translate(deltaX, deltaY));
                camera.getTransforms().set(0, transform);

                lastXTranslation -= deltaX;
                lastYTranslation -= deltaY;
            }

            if (event.isSecondaryButtonDown()) {
                Transform transform = camera.getTransforms().get(0);
                 transform = transform.createConcatenation(new Rotate((lastX - event.getSceneX()), lastXTranslation, 0, lastZTranslation, Rotate.Y_AXIS));
                 transform = transform.createConcatenation(new Rotate((event.getSceneY() - lastY), 0, lastYTranslation, lastZTranslation, Rotate.X_AXIS));
                 camera.getTransforms().set(0, transform);
            }

            lastX = event.getSceneX();
            lastY = event.getSceneY();
        });

        pane.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            lastZTranslation -= deltaY;

            Transform transform = camera.getTransforms().get(0);
            transform = transform.createConcatenation(new Translate(0, 0, deltaY));
            camera.getTransforms().set(0, transform);
//                group.getCamera().setTranslateZ(red.getCamera().getTranslateZ() + event.getDeltaY());
//                    root.setTranslateZ(root.getTranslateZ() + event.getDeltaY());
        });

        PointLight pointLight = new PointLight(Color.ANTIQUEWHITE);
        pointLight.setTranslateX(15);
        pointLight.setTranslateY(-10);
        pointLight.setTranslateZ(-100);

//        root.getChildren().add(pointLight);

    }

    private List<javafx.scene.Node> createVisualisedObjects(Scene scene, int radius, SpherePropertiesController spherePropertiesController) {
        List<TypicalNode> list = ModelConverter.convert(scene);

        blackMaterial.setSpecularColor(Color.WHITE);
        redMaterial.setSpecularColor(Color.WHITE);

        nodesMap = new HashMap<>();
        List<javafx.scene.Node> visibleObjects = new LinkedList<>();

        for (TypicalNode typicalNode : list) {
            Sphere sphere = createSphere(typicalNode, radius, blackMaterial);

            sphere.setOnMouseClicked(event -> {
                selectSphere(sphere);
                spherePropertiesController.setSelectedNode(typicalNode);
            });

            nodesMap.put(typicalNode, sphere);
            visibleObjects.add(sphere);
        }

        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.GREY);
        greyMaterial.setSpecularColor(Color.WHITE);

        SimpleNode centralNode = scene.getCentralNode();

        Sphere sphere = createSphere(centralNode, radius, greyMaterial);

        sphere.setOnMouseClicked(event -> {
            //TODO : at this spot the objects in scene should be moved, they should be rerendered
            selectSphere(sphere);
            spherePropertiesController.setSelectedNode(centralNode);
        });

        nodesMap.put(centralNode, sphere);
        visibleObjects.add(sphere);

        return visibleObjects;
    }

    private Sphere createSphere(SimpleNode simpleNode, int radius, Material material) {
        Sphere sphere = new Sphere(radius);
        sphere.setMaterial(material);
        sphere.setTranslateX(simpleNode.getX() * visualisationMultiplicant);
        sphere.setTranslateY(simpleNode.getY() * visualisationMultiplicant);
        sphere.setTranslateZ(simpleNode.getZ() * visualisationMultiplicant);
        return sphere;
    }

    private void selectSphere(Sphere sphere) {
        if (selectedSphere != null) {
            selectedSphere.setMaterial(blackMaterial);
        }
        selectedSphere = sphere;
        sphere.setMaterial(redMaterial); // [TODO] Find out why 'sphere.setEffect()' doesn't seem to work
    }

    public void recalculateDeformation() {
        deformationCalculator.recalculateDeformation();
        refreshVisualization(visualisationMultiplicant);
    }

    private void refreshVisualization(int multiplicant) {
        for (SimpleNode simpleNode : nodesMap.keySet()) {
            Sphere sphere = nodesMap.get(simpleNode);
            sphere.setTranslateX(simpleNode.getX() * multiplicant);
            sphere.setTranslateY(simpleNode.getY() * multiplicant);
            sphere.setTranslateZ(simpleNode.getZ() * multiplicant);
        }
    }
}
