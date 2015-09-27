package flexo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class SpherePropertiesController {

    @FXML
    private GridPane sphereProperties;

    @FXML
    private Label id;

    @FXML
    private Label x;

    @FXML
    private Label y;

    @FXML
    private Label z;

    @FXML
    private TextField parameter;

    public void setId(int id) {
        this.id.setText(String.valueOf(id));
    }

    public void setX(double x) {
        this.x.setText(String.valueOf(x));
    }

    public void setY(double y) {
        this.y.setText(String.valueOf(y));
    }

    public void setZ(double z) {
        this.z.setText(String.valueOf(z));
    }

    public void setParameter(TextField parameter) {
        this.parameter = parameter;
    }

    public void setVisible(boolean visible) {
        sphereProperties.setVisible(visible);
    }

}
