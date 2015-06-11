package FlexO;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by kcpr on 10.06.15.
 */
public class FlexO extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new GUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
