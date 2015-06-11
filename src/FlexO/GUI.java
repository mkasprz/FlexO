package FlexO;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI {

    public GUI(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FlexO-temporary.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 800, 600)); // true
        primaryStage.show();


    }

}
