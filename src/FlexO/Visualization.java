package flexo;

import flexo.model.Node;
import flexo.model.Scene;
import flexo.model_converter.ModelConverter;
import flexo.scene_builder.SceneBuilder;
import flexo.scene_builder.TwoDimensionBuilder;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kcpr on 12.06.15.
 */
public class Visualization {

    double anchorX, anchorY, anchorAngle, lastX, lastY;

    public Visualization(SubScene subScene) {

//        subScene.setHeight(300);
//        subScene.setWidth(800);

//        final PhongMaterial redMaterial = new PhongMaterial();
//        redMaterial.setSpecularColor(Color.ORANGE);
//        redMaterial.setDiffuseColor(Color.RED);
//
//        final PhongMaterial blueMaterial = new PhongMaterial();
//        blueMaterial.setDiffuseColor(Color.BLUE);
//        blueMaterial.setSpecularColor(Color.LIGHTBLUE);
//
//        final Box red = new Box(400, 400, 400);
//        red.setMaterial(redMaterial);
//
//        final javafx.scene.shape.Node blue = new javafx.scene.shape.Node(200);
//        blue.setMaterial(blueMaterial);
//
//        blue.setTranslateX(250);
//        blue.setTranslateY(250);
//        blue.setTranslateZ(50);
//        red.setTranslateX(250);
//        red.setTranslateY(250);
//        red.setTranslateZ(450);

        SceneBuilder builder = new TwoDimensionBuilder();

        Scene scene = builder.build();

        List<Node> list = ModelConverter.convert(scene);

//        Node node1 = new Node(200, 50, "super kulka 1");
//        Node node2 = new Node(300, 50, "super kulka 2");
//        Node node3 = new Node(400, 50, "super kulka 3");
//        Spring spring = new Spring();
//        spring.setNode1(node1);
//        spring.setNode2(node2);
//        System.out.println(spring.getNode1().x);
//        node1.x = 123;
//        System.out.println(spring.getNode1().x);
//        List<Node> list = new LinkedList<Node>();
//        list.add(node1);
//        list.add(node2);
//        list.add(node3);


        final PhongMaterial blackMaterial = new PhongMaterial();
        blackMaterial.setDiffuseColor(Color.BLACK);
        blackMaterial.setSpecularColor(Color.LIGHTBLUE);



        //visualistation
        List<javafx.scene.Node> vislist = new LinkedList<>();
        for (Node node : list){
            Sphere sphere = new Sphere(20);
            sphere.setMaterial(blackMaterial);
            sphere.setTranslateX(node.getX() * 10);
            sphere.setTranslateY(node.getY());
            sphere.setTranslateZ(node.getZ());

//            sphere.setTranslateZ(250+ Node.x);
            vislist.add(sphere);
        }

        vislist.add(new Cylinder(10, 100));
        final Group parent = new Group(vislist);


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
