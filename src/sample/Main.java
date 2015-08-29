package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class Main extends Application {

    double anchorX, anchorY, anchorAngle, lastX, lastY;

    private PerspectiveCamera addCamera(Scene scene) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(false);
        scene.setCamera(perspectiveCamera);

        return perspectiveCamera;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Remove this line once dirtyopts bug is fixed for 3D primitive
        //System.setProperty("prism.dirtyopts", "false");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("");

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.ORANGE);
        redMaterial.setDiffuseColor(Color.RED);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.BLUE);
        blueMaterial.setSpecularColor(Color.LIGHTBLUE);

        final Box red = new Box(400, 400, 400);
        red.setMaterial(redMaterial);

        final javafx.scene.shape.Sphere blue = new javafx.scene.shape.Sphere(200);
        blue.setMaterial(blueMaterial);

        blue.setTranslateX(250);
        blue.setTranslateY(250);
        blue.setTranslateZ(50);
        red.setTranslateX(250);
        red.setTranslateY(250);
        red.setTranslateZ(450);

        Node node1 = new Node(250,50, "super kulka 1");
        Node node2 = new Node(250,450, "super kulka 2");
        Node node3 = new Node(250,1000, "super kulka 3");
        Spring spring = new Spring();
        spring.setNode1(node1);
        spring.setNode2(node2);
        System.out.println(spring.getNode1().x);
        node1.x = 123;
        System.out.println(spring.getNode1().x);
        List<Node> list = new LinkedList<Node>();
        list.add(node1);
        list.add(node2);
        list.add(node3);



        final PhongMaterial blackMaterial = new PhongMaterial();
        blackMaterial.setDiffuseColor(Color.BLACK);
        blackMaterial.setSpecularColor(Color.LIGHTBLUE);



        //visualistation
        List<javafx.scene.Node> vislist = new LinkedList<>();
        for (Node ball : list){
            javafx.scene.shape.Sphere sphere = new javafx.scene.shape.Sphere(100);
            sphere.setMaterial(blackMaterial);
            sphere.setTranslateX(ball.x);
            sphere.setTranslateY(ball.y);
            sphere.setTranslateZ(250+ ball.x);
            vislist.add(sphere);
        }

        final Group parent = new Group(vislist);


        //final Group parent = new Group(red, blue);
        parent.setTranslateZ(500);
        parent.setRotationAxis(Rotate.Y_AXIS);


        final Group root = new Group(parent);

        final Scene scene = new Scene(root, 500, 500, true);

        blue.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Im blue!");
            }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
//                lastX = scene.getCamera().getTranslateX();
//                lastY = scene.getCamera().getTranslateY();
                lastX = scene.getCamera().getLayoutX();
                lastY = scene.getCamera().getLayoutY();
//                scene.getCamera().setTranslateY( + 5);
                anchorAngle = parent.getRotate();
//                System.out.println(lastX);
            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.isPrimaryButtonDown()) {
//                scene.getCamera().setTranslateX(lastX + (anchorX - event.getSceneX()));
//                scene.getCamera().setTranslateY(lastY + (anchorY - event.getSceneY()));
                    scene.getCamera().relocate(lastX + (anchorX - event.getSceneX()), lastY + (anchorY - event.getSceneY()));

                }

                if (event.isSecondaryButtonDown()) {

                    scene.getCamera().setRotate(scene.getCamera().getRotate() - (anchorX - event.getSceneX()));

                }
//                parent.setRotate(anchorAngle + anchorX -  event.getSceneX());


            }
        });

        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                scene.getCamera().setTranslateZ(scene.getCamera().getTranslateZ() + event.getDeltaY());
            }
        });


        PointLight pointLight = new PointLight(Color.ANTIQUEWHITE);
        pointLight.setTranslateX(15);
        pointLight.setTranslateY(-10);
        pointLight.setTranslateZ(-100);

        root.getChildren().add(pointLight);

        addCamera(scene);
        primaryStage.setScene(scene);
        primaryStage.show();

//        vislist.get(0).setRotationAxis(Rotate.X_AXIS);
//        scene.getCamera().setRotationAxis(vislist.get(0).);
    }
}