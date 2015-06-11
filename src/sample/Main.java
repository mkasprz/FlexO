package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class Main extends Application {

    double anchorX, anchorY, anchorAngle, lastX, lastY;

    private PerspectiveCamera addCamera(Scene scene) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(false);
        scene.setCamera(perspectiveCamera);
//        perspectiveCamera.;
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
        primaryStage.setTitle("SphereAndBox");

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.ORANGE);
        redMaterial.setDiffuseColor(Color.RED);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.BLUE);
        blueMaterial.setSpecularColor(Color.LIGHTBLUE);

        final Box red = new Box(400, 400, 400);
        red.setMaterial(redMaterial);

        final Sphere blue = new Sphere(200);
        blue.setMaterial(blueMaterial);

        blue.setTranslateX(250);
        blue.setTranslateY(250);
        blue.setTranslateZ(50);
        red.setTranslateX(250);
        red.setTranslateY(250);
        red.setTranslateZ(450);

        Kulka kulka1 = new Kulka(250,50, "super kulka 1");
        Kulka kulka2 = new Kulka(250,450, "super kulka 2");
        Kulka kulka3 = new Kulka(250,600, "super kulka 3");
        Sprezynka sprezynka = new Sprezynka();
        sprezynka.setKulka1(kulka1);
        sprezynka.setKulka2(kulka2);
        System.out.println(sprezynka.getKulka1().x);
        kulka1.x = 123;
        System.out.println(sprezynka.getKulka1().x);
        List<Kulka> list = new LinkedList<Kulka>();
        list.add(kulka1);
        list.add(kulka2);
        list.add(kulka3);



        final PhongMaterial blackMaterial = new PhongMaterial();
        blackMaterial.setDiffuseColor(Color.BLACK);
        blackMaterial.setSpecularColor(Color.LIGHTBLUE);


        //visualistation
        List<Node> vislist = new LinkedList<>();
        for (Kulka kulka : list){
            Sphere sphere = new Sphere(200);
            sphere.setMaterial(blackMaterial);
            sphere.setTranslateX(kulka.x);
            sphere.setTranslateY(kulka.y);
            sphere.setTranslateZ(250+kulka.x);
            vislist.add(sphere);
        }

        Group parent = new Group(vislist);


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
            @Override public void handle(MouseEvent event) {
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
            @Override public void handle(MouseEvent event) {

                if (event.isPrimaryButtonDown()) {
//                scene.getCamera().setTranslateX(lastX + (anchorX - event.getSceneX()));
//                scene.getCamera().setTranslateY(lastY + (anchorY - event.getSceneY()));
                    scene.getCamera().relocate(lastX + (anchorX - event.getSceneX()), lastY + (anchorY - event.getSceneY()));

                }

                if (event.isSecondaryButtonDown()) {

                    scene.getCamera().setRotate(scene.getCamera().getRotate() + (anchorX - event.getSceneX()));

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
    }
}