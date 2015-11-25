package flexo.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI {

    public static ApplicationController loadGUI(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("Application.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("FlexO");
        primaryStage.setScene(new Scene(root, 800, 600, true));
        primaryStage.show();

        return fxmlLoader.getController();
    }

}
