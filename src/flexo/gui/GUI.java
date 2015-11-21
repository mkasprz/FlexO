package flexo.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI {

    public static ApplicationController loadGUI(Stage primaryStage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Application.fxml"));
//        ApplicationController applicationController;
//        applicationController = new ApplicationController(this);
//        fxmlLoader.setController(applicationController);
//        Parent root = fxmlLoader.load();
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("Application.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("FlexO");
        primaryStage.setScene(new Scene(root, 800, 600, true)); // true
        primaryStage.show();

        return fxmlLoader.getController();

//        fxmlLoader = new FXMLLoader(getClass().getResource("SphereProperties.fxml"));
//        SpherePropertiesController spherePropertiesController = new SpherePropertiesController();
//        fxmlLoader.setController(spherePropertiesController);
//        Parent settings = fxmlLoader.load();
//        applicationController.getPropertiesVBox().getChildren().add(settings);


    }

}
