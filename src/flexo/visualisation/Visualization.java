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

        camera.getTransforms().add(new Translate(0, 0, -2000));

        subScene.setCamera(camera);

        subScene.setRoot(root);

        pane.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();

            System.out.println(event.getSceneX() + " " + event.getSceneY());
            lastX = root.getTranslateX();
            lastY = root.getTranslateY();
//          anchorAngle = parent.getRotate();
        });
//
        pane.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                Transform transform = camera.getTransforms().get(0);
                transform = transform.createConcatenation(new Translate(anchorX - event.getSceneX(), anchorY - event.getSceneY()));
                camera.getTransforms().set(0, transform);
            }

            if (event.isSecondaryButtonDown()) {
                // [TODO] Make camera rotate correctly instead of rotating objects
                // Transform transform = camera.getTransforms().get(0);
                // transform = transform.createConcatenation(new Rotate((anchorX - event.getSceneX()), 450, 0, 2000, Rotate.Y_AXIS));
                // transform = transform.createConcatenation(new Rotate((anchorY - event.getSceneY()), 0, 200, 2000, Rotate.X_AXIS));
                // camera.getTransforms().set(0, transform);
                 Transform transform = root.getTransforms().get(0);
                 transform = transform.createConcatenation(new Rotate((anchorX - event.getSceneX()), 450, 0, 0, Rotate.Y_AXIS));
                 transform = transform.createConcatenation(new Rotate((anchorY - event.getSceneY()), 0, 150, 0, Rotate.X_AXIS));
                 root.getTransforms().set(0, transform);
            }

            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
        });

        pane.setOnScroll(event -> {
            Transform transform = camera.getTransforms().get(0);
            transform = transform.createConcatenation(new Translate(0, 0, event.getDeltaY()));
            camera.getTransforms().set(0, transform);
//                group.getCamera().setTranslateZ(red.getCamera().getTranslateZ() + event.getDeltaY());
//                    root.setTranslateZ(root.getTranslateZ() + event.getDeltaY());
        });

        PointLight pointLight = new PointLight(Color.ANTIQUEWHITE);
        pointLight.setTranslateX(15);
        pointLight.setTranslateY(-10);
        pointLight.setTranslateZ(-100);

//        group.getChildren().add(pointLight);

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
