package flexo.visualisation;

import flexo.deformationcalculator.DeformationCalculator;
import flexo.gui.GUI;
import flexo.gui.SpherePropertiesController;
import flexo.model.Scene;
import flexo.modelconverter.ModelConverter;
import flexo.scenebuilder.SceneBuilder;
import flexo.scenebuilder.TwoDimensionBuilder;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kcpr on 12.06.15.
 */
public class Visualization {

    public static final int X = 450;
    public static final int Y = 150;
    public static final int Z = -2000;

    double lastX, lastY, lastXTranslation, lastYTranslation;
    double lastZTranslation = -Z;

    double anchorX, anchorY, lastX, lastY;
    DeformationCalculator deformationCalculator;
    int radius = 20;
    int visualisationMultiplicant = 10;
    Sphere selectedSphere;
    GUI gui;

    public Visualization(Pane pane, SubScene subScene, SpherePropertiesController spherePropertiesController, GUI gui, Scene scene) {

        SceneBuilder builder = new TwoDimensionBuilder();
        deformationCalculator = new DeformationCalculator();
        builder.setNodesNumber(40);
        if (scene == null) {
            scene = builder.build();
        }
        deformationCalculator.setScene(scene);
        this.gui = gui;

        List<javafx.scene.Node> visualisedObjects = createVisualisedObjects(scene, radius, visualisationMultiplicant, spherePropertiesController);

        final Group root = new Group(visualisedObjects);
        root.getTransforms().add(new Translate());

        // [TODO] Make depth buffer work
        // SubScene subScene = new SubScene(root, 0, 0, true, SceneAntialiasing.BALANCED);
        // subScene.heightProperty().bind(pane.heightProperty());
        // subScene.widthProperty().bind(pane.widthProperty());
        // pane.getChildren().set(0, subScene);
        // System.out.println(subScene.isDepthBuffer());

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
                 transform = transform.createConcatenation(new Rotate((lastY - event.getSceneY()), 0, lastYTranslation, lastZTranslation, Rotate.X_AXIS));
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

    private List<javafx.scene.Node> createVisualisedObjects(Scene scene, int radius, int multiplicant, SpherePropertiesController spherePropertiesController) {
        List<flexo.model.Node> list = ModelConverter.convert(scene);

        final PhongMaterial blackMaterial = new PhongMaterial(Color.BLACK);
        blackMaterial.setSpecularColor(Color.WHITE);

        final PhongMaterial redMaterial = new PhongMaterial(Color.RED);
        redMaterial.setSpecularColor(Color.WHITE);

        List<javafx.scene.Node> visibleObjects = new LinkedList<>();
        for (flexo.model.Node node : list) {
            Sphere sphere = new Sphere(radius);
            sphere.setMaterial(blackMaterial);
            sphere.setTranslateX(node.getX() * multiplicant);
            sphere.setTranslateY(node.getY() * multiplicant);
            sphere.setTranslateZ(node.getZ() * multiplicant);

            sphere.setOnMouseClicked(event -> {
                if (selectedSphere != null) {
                    selectedSphere.setMaterial(blackMaterial);
                }
                selectedSphere = sphere;
                sphere.setMaterial(redMaterial); // [TODO] Find out why 'sphere.setEffect()' doesn't seem to work

                spherePropertiesController.setSelectedNode(node);

                spherePropertiesController.setId(node.getId());
                spherePropertiesController.setParameter(node.getParameter());
                spherePropertiesController.setX(node.getX());
                spherePropertiesController.setY(node.getY());
                spherePropertiesController.setZ(node.getZ());
                spherePropertiesController.setVisible(true);
            });

            visibleObjects.add(sphere);
        }

        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.GREY);
        greyMaterial.setSpecularColor(Color.WHITE);

        Sphere sphere = new Sphere(radius);
        sphere.setMaterial(greyMaterial);
        sphere.setTranslateX(scene.getCentralNode().getX() * multiplicant);
        sphere.setTranslateY(scene.getCentralNode().getY() * multiplicant);
        sphere.setTranslateZ(scene.getCentralNode().getZ() * multiplicant);
        sphere.setOnMouseClicked(event -> {
            deformationCalculator.recalculateDeformation();
            //TODO : at this spot the objects in scene should be moved, they should be rerendered
            gui.reloadVisualisation(scene);
        });

        visibleObjects.add(sphere);
        return visibleObjects;
    }
}
