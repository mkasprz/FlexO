package flexo.gui;

import flexo.model.Connection;
import flexo.model.TypicalNode;
import flexo.visualization.Visualization;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PropertiesController {

    @FXML
    private Pane pane;

    @FXML
    private ScrollPane spherePropertiesScrollPane;

    @FXML
    private ScrollPane connectionPropertiesScrollPane;

    @FXML
    private GridPane sphereProperties;

    @FXML
    private GridPane connectionProperties;

    @FXML
    private Label id;

    @FXML
    private NumberField x;

    @FXML
    private NumberField y;

    @FXML
    private NumberField z;

    @FXML
    private Label firstNode;

    @FXML
    private Label secondNode;

    @FXML
    private TextField currentLength;

    @FXML
    private NumberField preferredLength;

    @FXML
    private NumberField youngsModulus;

    private Visualization visualization;

    private TypicalNode selectedNode;
    private Connection selectedConnection;

    private final Font defaultFont = Font.getDefault();
    private final Font boldFont = Font.font(defaultFont.getFamily(), FontWeight.BOLD, defaultFont.getSize());

    @FXML
    void initialize() {
        spherePropertiesScrollPane.setStyle("-fx-background-color:transparent;");
        connectionPropertiesScrollPane.setStyle("-fx-background-color:transparent;");
        connectionProperties.prefWidthProperty().bind(pane.widthProperty().subtract(1));
        connectionProperties.prefHeightProperty().bind(pane.heightProperty().subtract(1));
        sphereProperties.prefWidthProperty().bind(pane.widthProperty().subtract(1));
        sphereProperties.prefHeightProperty().bind(pane.heightProperty().subtract(1));

        x.textProperty().addListener((observable, oldValue, newValue) -> {
            changeFontWeight(selectedNode.getX(), x);
        });
        y.textProperty().addListener((observable, oldValue, newValue) -> {
            changeFontWeight(selectedNode.getY(), y);
        });
        z.textProperty().addListener((observable, oldValue, newValue) -> {
            changeFontWeight(selectedNode.getZ(), z);
        });
        preferredLength.textProperty().addListener((observable, oldValue, newValue) -> {
            changeFontWeight(selectedConnection.getBalanceLength(), preferredLength);
        });
        youngsModulus.textProperty().addListener((observable, oldValue, newValue) -> {
            changeFontWeight(selectedConnection.getYoungsModulus(), youngsModulus);
        });
    }

    private void changeFontWeight(double number, NumberField numberField) {
        if (number != numberField.getNumber()) {
            numberField.setFont(boldFont);
        } else {
            numberField.setFont(defaultFont);
        }
    }

    @FXML
    private void moveNode() {
        selectedNode.moveNode(x.getNumber(), y.getNumber(), z.getNumber());
        x.setFont(defaultFont);
        y.setFont(defaultFont);
        z.setFont(defaultFont);
        visualization.recalculateDeformation();
    }

    @FXML
    private void applyConnectionChanges() {
        selectedConnection.setBalanceLength(preferredLength.getNumber());
        selectedConnection.setYoungsModulus(youngsModulus.getNumber());
        preferredLength.setFont(defaultFont);
        youngsModulus.setFont(defaultFont);
        visualization.recalculateDeformation(); // [TODO] Not sure about it
        currentLength.setText(String.valueOf(selectedConnection.getLength()));
    }

    public void setVisualization(Visualization visualization) {
        this.visualization = visualization;
    }

    public void setVisible(boolean visible) {
        spherePropertiesScrollPane.setVisible(visible);
        connectionPropertiesScrollPane.setVisible(visible);
    }

    public void setSelectedNode(TypicalNode typicalNode) {
        connectionPropertiesScrollPane.setVisible(false);

        this.selectedNode = typicalNode;

        if (selectedNode.getId() == 0) {
            setNumberFieldsEditable(true);
        } else {
//            setNumberFieldsEditable(false);
        }

        id.setText(typicalNode.getIdAsString());
        x.setNumber(typicalNode.getX());
        y.setNumber(typicalNode.getY());
        z.setNumber(typicalNode.getZ());

        spherePropertiesScrollPane.setVisible(true);
    }

    private void setNumberFieldsEditable(boolean numberFieldsEditable) {
        x.setEditable(numberFieldsEditable);
        y.setEditable(numberFieldsEditable);
        z.setEditable(numberFieldsEditable);
    }

    public void setSelectedConnection(Connection connection) {
        spherePropertiesScrollPane.setVisible(false);

        this.selectedConnection = connection;

        firstNode.setText(connection.getTypicalNode1().getIdAsString());
        secondNode.setText(connection.getTypicalNode2().getIdAsString());
        currentLength.setText(String.valueOf(connection.getLength()));
        preferredLength.setNumber(connection.getBalanceLength());
        youngsModulus.setNumber(connection.getYoungsModulus());

        connectionPropertiesScrollPane.setVisible(true);
    }
}
