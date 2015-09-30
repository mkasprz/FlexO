package flexo.gui;

import flexo.model.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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

    private Node selectedNode;

    @FXML
    void initialize() {

        parameter.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // && parameter.getText().equals(selectedNode.getParameter())) { // && parameter.getText().equals("")
                setParameter(selectedNode.getParameter());
            }
        });

        parameter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("")) {
                selectedNode.setParameter(getParameter());
            }
        });
    }

    @FXML
    void parameterChanged(ActionEvent event) {
        System.out.println(getParameter());
        selectedNode.setParameter(getParameter());
    }

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

    public double getParameter() {
        return Double.parseDouble(parameter.getText());
    }

    public void setParameter(double parameter) {
        this.parameter.setText(String.valueOf(parameter));
    }

    public void setVisible(boolean visible) {
        sphereProperties.setVisible(visible);
    }

    public Node getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(Node node) {
        this.selectedNode = node;
    }

}
