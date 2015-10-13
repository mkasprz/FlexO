package flexo.gui;

import flexo.visualisation.Visualization;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.security.auth.Subject;
import java.io.IOException;

public class GUI {

    Pane pane;

    SubScene subScene;

    SpherePropertiesController spherePropertiesController;

    public GUI(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Application.fxml"));
        ApplicationController applicationController;
        applicationController = new ApplicationController(this);
        fxmlLoader.setController(applicationController);
        Parent root = fxmlLoader.load();
        pane = applicationController.getPane();
        subScene = applicationController.getSubScene();

        primaryStage.setTitle("FlexO");
        primaryStage.setScene(new Scene(root, 800, 600, true)); // true
        primaryStage.show();

        fxmlLoader = new FXMLLoader(getClass().getResource("SphereProperties.fxml"));
        SpherePropertiesController spherePropertiesController = new SpherePropertiesController();
        fxmlLoader.setController(spherePropertiesController);
        Parent settings = fxmlLoader.load();
        applicationController.getPropertiesVBox().getChildren().add(settings);
        this.spherePropertiesController = spherePropertiesController;

        new Visualization(applicationController.getPane(), applicationController.getSubScene(), spherePropertiesController, this, null);
    }

    public void reloadVisualisation(flexo.model.Scene scene){
        new Visualization(pane, subScene, spherePropertiesController, this, scene);
    }

}
