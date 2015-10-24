package flexo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 2015-09-10.
 */
public class Scene {

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

}
