package flexo.visualisation;

import flexo.gui.SpherePropertiesController;
import flexo.model.Scene;
import flexo.modelconverter.ModelConverter;
import flexo.scenebuilder.SceneBuilder;
import flexo.scenebuilder.TwoDimensionBuilder;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kcpr on 12.06.15.
 */
public class Visualization {

    double anchorX, anchorY, anchorAngle, lastX, lastY;

    int radius = 20;
    int visualisationMultiplicant = 10;

    Sphere selectedSphere;

    public Visualization(SubScene subScene, SpherePropertiesController spherePropertiesController) {

        SceneBuilder builder = new TwoDimensionBuilder();
        builder.setNodesNumber(10);
        Scene scene = builder.build();

        List<javafx.scene.Node> visualisedObjects = createVisualisedObjects(scene, radius, visualisationMultiplicant, spherePropertiesController);
        final Group parent = new Group(visualisedObjects);


//        group.getChildren().addAll(vislist);

        //final Group parent = new Group(red, blue);
//        parent.setTranslateZ(500);
//        parent.setRotationAxis(Rotate.Y_AXIS);


        final Group root = new Group(parent);
        subScene.setRoot(root);

//        pane.getChildren().setAll(root);


//        final Scene scene = new Scene(root, 500, 500, true);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();

                lastX = root.getTranslateX();
                lastY = root.getTranslateY();
//                anchorAngle = parent.getRotate();
            }
        });
//
        root.setOnMouseDragged(event -> {

            if (event.isPrimaryButtonDown()) {
////                scene.getCamera().setTranslateX(lastX + (anchorX - event.getSceneX()));
////                scene.getCamera().setTranslateY(lastY + (anchorY - event.getSceneY()));
//                    red.getCamera().relocate(lastX + (anchorX - event.getSceneX()), lastY + (anchorY - event.getSceneY()));


                root.setTranslateX(lastX + (anchorX - event.getSceneX()));
                root.setTranslateY(lastY + (anchorY - event.getSceneY()));
            }

            if (event.isSecondaryButtonDown()) {

//                    red.getCamera().setRotate(red.getCamera().getRotate() - (anchorX - event.getSceneX()));

            }
//                parent.setRotate(anchorAngle + anchorX -  event.getSceneX());

        });


//        group.setOnScroll(new EventHandler<ScrollEvent>() {
//            @Override
//            public void handle(ScrollEvent event) {
//                group.getCamera().setTranslateZ(red.getCamera().getTranslateZ() + event.getDeltaY());
//            }
//        });


        PointLight pointLight = new PointLight(Color.ANTIQUEWHITE);
        pointLight.setTranslateX(15);
        pointLight.setTranslateY(-10);
        pointLight.setTranslateZ(-100);

//        group.getChildren().add(pointLight);

//        addCamera(red);
//        primaryStage.setScene(red);
//        primaryStage.show();

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

        visibleObjects.add(sphere);
        return visibleObjects;
    }
}
