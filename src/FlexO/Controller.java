package FlexO;

import javafx.fxml.FXML;
import javafx.scene.Group;

public class Controller {

    private final GUI gui;

    @FXML
    private Group group;

    public Group getGroup() {
        return group;
    }

    public Controller(GUI gui) {
        this.gui = gui;
    }
}
