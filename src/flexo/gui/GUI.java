package flexo.gui;

import flexo.visualisation.Visualization;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI {

    public GUI(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Application.fxml"));
        ApplicationController applicationController;
        applicationController = new ApplicationController(this);
        fxmlLoader.setController(applicationController);
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("FlexO");
        primaryStage.setScene(new Scene(root, 800, 600, true)); // true
        primaryStage.show();

        fxmlLoader = new FXMLLoader(getClass().getResource("SphereProperties.fxml"));
        SpherePropertiesController spherePropertiesController = new SpherePropertiesController();
        fxmlLoader.setController(spherePropertiesController);
        Parent settings = fxmlLoader.load();
        applicationController.getPropertiesVBox().getChildren().add(settings);

        new Visualization(applicationController.getPane(), applicationController.getSubScene(), spherePropertiesController);
    }

}
