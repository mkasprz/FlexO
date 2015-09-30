package flexo;

import flexo.gui.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class FlexO extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new GUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
