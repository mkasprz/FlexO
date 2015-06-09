package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Main extends Application {

    double anchorX, anchorY, anchorAngle;

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
        System.setProperty("prism.dirtyopts", "false");
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

        final Group parent = new Group(red, blue);
        parent.setTranslateZ(500);
        parent.setRotationAxis(Rotate.Y_AXIS);


        final Group root = new Group(parent);

        final Scene scene = new Scene(root, 500, 500, true);

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
//                anchorX = scene.getCamera().getTranslateX();
//                anchorY = scene.getCamera().getTranslateY();
//                scene.getCamera().setTranslateY( + 5);
                //anchorAngle = parent.getRotate();
            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
//                parent.setRotate(anchorAngle + anchorX -  event.getSceneX());

                System.out.println(event.getSceneX());

                scene.getCamera().setTranslateX((anchorX - event.getSceneX()));
                scene.getCamera().setTranslateY((anchorY - event.getSceneY()));

//                scene.getCamera().setTranslateX(0);
//                scene.getCamera().setTranslateY(0);
            }
        });

        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                anchorX = scene.getCamera().getTranslateX();
                anchorY = scene.getCamera().getTranslateY();
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