package flexo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 2015-09-10.
 */
public class Setup {

    List<Connection> connections = new LinkedList<>();

    TypicalNode centralNode;

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public void addConnections(List<Connection> connections) {
        this.connections.addAll(connections);
    }

    public List<Connection> getConnections(){
        return connections;
    }

    public TypicalNode getCentralNode() {
        return centralNode;
    }

    public void setCentralNode(TypicalNode centralNode) {
        this.centralNode = centralNode;
    }

    public List<TypicalNode> getTypicalNodes() { // [TODO] Decide if it's better to store nodes list in some variable
        List<TypicalNode> typicalNodes = new LinkedList<>();
        for (Connection connection : connections) {
            if (!typicalNodes.contains(connection.getTypicalNode1())) {
                typicalNodes.add(connection.getTypicalNode1());
            }
            if (!typicalNodes.contains(connection.getTypicalNode2())) {
                typicalNodes.add(connection.getTypicalNode2());
            }
        }
        return typicalNodes;
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

}
