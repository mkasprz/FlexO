package flexo.visualisation;

import flexo.model.Scene;
import flexo.scenebuilder.SceneBuilder;
import flexo.scenebuilder.TwoDimensionBuilder;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;

public class Visualization {

    double anchorX, anchorY, anchorAngle, lastX, lastY;

    int radius = 20;
    int visualisationMultiplicant = 10;

    public Visualization(SubScene subScene) {

        SceneBuilder builder = new TwoDimensionBuilder();
        builder.setNodesNumber(5);
        Scene scene = builder.build();

        List<javafx.scene.Node> visualisedObjects = VisualisedObjectsCreator.createVisualisedObjects(scene, radius, visualisationMultiplicant);
        final Group parent = new Group(visualisedObjects);


//        group.getChildren().addAll(vislist);

        //final Group parent = new Group(red, blue);
//        parent.setTranslateZ(500);
//        parent.setRotationAxis(Rotate.Y_AXIS);


        final Group root = new Group(parent);
        subScene.setRoot(root);

//        pane.getChildren().setAll(root);


//        final Scene scene = new Scene(root, 500, 500, spokotrue);

//        blue.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("Im blue!");
//            }
//        });

//        root.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                anchorX = event.getSceneX();
//                anchorY = event.getSceneY();
////                lastX = scene.getCamera().getTranslateX();
////                lastY = scene.getCamera().getTranslateY();
//                lastX = red.getCamera().getLayoutX();
//                lastY = red.getCamera().getLayoutY();
////                scene.getCamera().setTranslateY( + 5);
//                anchorAngle = parent.getRotate();
////                System.out.println(lastX);
//            }
//        });
//
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.isPrimaryButtonDown()) {
////                scene.getCamera().setTranslateX(lastX + (anchorX - event.getSceneX()));
////                scene.getCamera().setTranslateY(lastY + (anchorY - event.getSceneY()));
//                    red.getCamera().relocate(lastX + (anchorX - event.getSceneX()), lastY + (anchorY - event.getSceneY()));
                    root.setTranslateX(event.getSceneX());
                    root.setTranslateY(event.getSceneY());
                }

                if (event.isSecondaryButtonDown()) {

//                    red.getCamera().setRotate(red.getCamera().getRotate() - (anchorX - event.getSceneX()));

                }
//                parent.setRotate(anchorAngle + anchorX -  event.getSceneX());


            }
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
}
