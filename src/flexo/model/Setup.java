package flexo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 2015-09-10.
 */
public class Setup {

    private List<Connection> connectionList = new LinkedList<Connection>();

    private SimpleNode centralNode;

    public void addConnection(Connection connection){
        connectionList.add(connection);
    }

    public List<Connection> getConnections(){
        return connectionList;
    }

    public SimpleNode getCentralNode() {
        return centralNode;
    }

    public void setCentralNode(SimpleNode centralNode) {
        this.centralNode = centralNode;
    }

    public int getNumberOfNodes(){
        return connectionList.size()+1;
    }

    public List<Connection> getConnectionsFromNode(TypicalNode node) {
        List<Connection> connections = new LinkedList<>();
        for (Connection connection : connectionList){
            if (connection.getTypicalNode1().equals(node) || connection.getTypicalNode2().equals(node)){
                connections.add(connection);
            }
        }
        return connections;
    }

}
