package flexo;

import flexo.gui.ApplicationController;
import flexo.gui.GUI;
import flexo.visualisation.Visualization;
import javafx.application.Application;
import javafx.stage.Stage;

public class FlexO extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationController applicationController = GUI.loadGUI(primaryStage);
        new Visualization(applicationController.getRoot(), applicationController.getPropertiesController());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
