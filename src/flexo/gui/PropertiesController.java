package flexo.gui;

import flexo.model.Connection;
import flexo.model.TypicalNode;
import flexo.visualization.Visualization;
import javafx.beans.value.ChangeListener;
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



    private enum XYZ {
        X, Y, Z;
    }
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

    private ChangeListener<String> changeFontWeight(XYZ xyz, NumberField numberField) { // [TODO] Decide which option is better
        return (observable, oldValue, newValue) -> {
            double number;

            if (xyz == XYZ.X) {
                number = selectedNode.getX();
            } else if (xyz == XYZ.Y) {
                number = selectedNode.getY();
            } else {
                number = selectedNode.getZ();
            }

            if (number != numberField.getNumber()) {
                numberField.setFont(boldFont);
            } else {
                numberField.setFont(defaultFont);
            }
        };
    }

    @FXML
    private void moveNode() {
        selectedNode.moveNode(x.getNumber(), y.getNumber(), z.getNumber());
        x.setFont(defaultFont);
        y.setFont(defaultFont);
        z.setFont(defaultFont);
        visualization.recalculateDeformation(1, x.getNumber() - selectedNode.getX());
        visualization.recalculateDeformation(2, y.getNumber() - selectedNode.getY());
        visualization.recalculateDeformation(3, z.getNumber() - selectedNode.getZ());
    }

    @FXML
    private void applyConnectionChanges() {
        // selectedConnection.setPreferredLength(preferredLength.getNumber()); // [TODO]
        selectedConnection.setYoungsModulus(youngsModulus.getNumber());
        preferredLength.setFont(defaultFont);
        youngsModulus.setFont(defaultFont);
        visualization.recalculateDeformation(1, 0); // [TODO] Not sure about it
        visualization.recalculateDeformation(2, 0);
        visualization.recalculateDeformation(3, 0);
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
        currentLength.setText(String.valueOf(connection.getBalanceLength()));
        preferredLength.setNumber(connection.getBalanceLength()); // [TODO] Not sure about it
        youngsModulus.setNumber(connection.getYoungsModulus());

        connectionPropertiesScrollPane.setVisible(true);
    }
}
