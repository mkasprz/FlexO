package FlexO;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI {

    private final Controller controller;

    public GUI(Stage primaryStage) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("FlexO-temporary.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FlexO-temporary.fxml"));
        controller = new Controller(this);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 800, 600)); // true
        primaryStage.show();


        loadVisualization();

        new Visualization(controller.getGroup());



    }

    private void loadVisualization() {



    }

}
