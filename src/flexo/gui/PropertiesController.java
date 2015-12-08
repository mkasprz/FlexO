package flexo.gui;

import flexo.model.Connection;
import flexo.model.SimpleNode;
import flexo.model.TypicalNode;
import flexo.visualisation.Visualization;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PropertiesController {

    @FXML
    private GridPane sphereProperties;

    @FXML
    private Label id;

    @FXML
    private NumberField x;

    @FXML
    private NumberField y;

    @FXML
    private NumberField z;

    @FXML
    private TextField parameter;

    private Visualization visualization;

    private SimpleNode selectedNode;

    @FXML
    void initialize() {
        x.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // && parameter.getText().equals(selectedNode.getParameter())) { // && parameter.getText().equals("")
                setX(selectedNode.getX());
            }
        });

        y.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // && parameter.getText().equals(selectedNode.getParameter())) { // && parameter.getText().equals("")
                setY(selectedNode.getY());
            }
        });

        z.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // && parameter.getText().equals(selectedNode.getParameter())) { // && parameter.getText().equals("")
                setZ(selectedNode.getZ());
            }
        });

        x.setOnAction(event -> {
            double oldX = selectedNode.getX();
            double change = getX() - oldX;
            selectedNode.setX(getX());
            visualization.recalculateDeformation(1, change);
        });

        y.setOnAction(event -> {
            double oldY = selectedNode.getY();
            double change = getY() - oldY;
            selectedNode.setY(getY());
            visualization.recalculateDeformation(2, change);
        });

        z.setOnAction(event -> {
            double oldZ = selectedNode.getZ();
            double change = getZ() - oldZ;
            selectedNode.setZ(getZ());
            visualization.recalculateDeformation(3, change);
        });

        parameter.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && selectedNode instanceof TypicalNode) { // && parameter.getText().equals(selectedNode.getParameter())) { // && parameter.getText().equals("")
                setParameter(((TypicalNode) selectedNode).getParameter());
            }
        });

        parameter.setOnAction(event -> {
            ((TypicalNode)selectedNode).setParameter(getParameter());
        });
    }

//    @FXML
//    void parameterChanged(ActionEvent event) {
//        System.out.println(getParameter());
//        selectedNode.setParameter(getParameter());
//    }

    public void setVisualization(Visualization visualization) {
        this.visualization = visualization;
    }

    public void setId(int id) {
        this.id.setText(String.valueOf(id));
    }

    public double getX() {
        return x.getNumber();
    }

    public double getY() {
        return y.getNumber();
    }

    public double getZ() {
        return z.getNumber();
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

    public SimpleNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(SimpleNode simpleNode) {
        this.selectedNode = simpleNode;
        id.setVisible(false);
        parameter.setVisible(false);
        setX(simpleNode.getX());
        setY(simpleNode.getY());
        setZ(simpleNode.getZ());
        setVisible(true);
    }

    public void setSelectedNode(TypicalNode typicalNode) {
        this.selectedNode = typicalNode;
        setId(typicalNode.getId());
        id.setVisible(true);
        setParameter(typicalNode.getParameter());
        parameter.setVisible(true);
        setX(typicalNode.getX());
        setY(typicalNode.getY());
        setZ(typicalNode.getZ());
        setVisible(true);
    }

    public void setSelectedConnection(Connection connection) {

    }
}
