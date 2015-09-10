package flexo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller;

import java.io.IOException;

public class GUI {

    private final ApplicationController applicationController;

    public GUI(Stage primaryStage) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("Application.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Application.fxml"));
        applicationController = new ApplicationController(this);
        fxmlLoader.setController(applicationController);
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 800, 600)); // true
        primaryStage.show();


        fxmlLoader = new FXMLLoader(getClass().getResource("SphereSettings.fxml"));
        fxmlLoader.setController(new Controller());
        Parent settings = fxmlLoader.load();
        applicationController.getPropertiesVBox().getChildren().add(settings);

        loadVisualization();

        new Visualization(applicationController.getSubScene());



    }

    private void loadVisualization() {



    }

}
