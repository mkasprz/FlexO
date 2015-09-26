package flexo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 2015-09-10.
 */
public class Scene {

    private List<Connection> connectionList = new LinkedList<Connection>();

    private CentralNode centralNode;

    public void addConnection(Connection connection){
        connectionList.add(connection);
    }

    public List<Connection> getConnections(){
        return connectionList;
    }

    public CentralNode getCentralNode() {
        return centralNode;
    }

    public void setCentralNode(CentralNode centralNode) {
        this.centralNode = centralNode;
    }

}
