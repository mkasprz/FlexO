package flexo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class Setup {

    @XmlElementWrapper
    @XmlElement(name = "connection")
    List<Connection> connections = new LinkedList<>();

    @XmlElementWrapper
    @XmlElement(name = "immovableNode")
    List<ImmovableNode> immovableNodes = new LinkedList<>();

    @XmlElementWrapper
    @XmlElement(name = "typicalNode")
    List<TypicalNode> typicalNodes = new LinkedList<>();

    TypicalNode centralNode;

    private double previousCentralX;

    private double previousCentralY;

    private double previousCentralZ;

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public void addConnections(List<Connection> connections) {
        this.connections.addAll(connections);
    }

    public List<Connection> getConnections(){
        return connections;
    }

    public List<ImmovableNode> getImmovableNodes() {
        return immovableNodes;
    }

    public List<TypicalNode> getTypicalNodes() { // [TODO] Decide if it's better to store nodes list in some variable
        return typicalNodes;
    }

    public TypicalNode getCentralNode() {
        return centralNode;
    }

    public void setCentralNode(TypicalNode centralNode) {
        this.centralNode = centralNode;
    }

    public List<Connection> getConnectionsFromNode(TypicalNode node) {
        List<Connection> connections = new LinkedList<>();
        for (Connection connection : this.connections) {
            if (connection.getTypicalNode1().equals(node) || connection.getTypicalNode2().equals(node)){
                connections.add(connection);
            }
        }
        return connections;
    }

    public int getNumberOfNodes() {
        return connections.size();
    }

    public double getPreviousCentralX() {
        return previousCentralX;
    }

    public void setPreviousCentralX(double previousCentralX) {
        this.previousCentralX = previousCentralX;
    }

    public double getPreviousCentralY() {
        return previousCentralY;
    }

    public void setPreviousCentralY(double previousCentralY) {
        this.previousCentralY = previousCentralY;
    }

    public double getPreviousCentralZ() {
        return previousCentralZ;
    }

    public void setPreviousCentralZ(double previousCentralZ) {
        this.previousCentralZ = previousCentralZ;
    }
}
