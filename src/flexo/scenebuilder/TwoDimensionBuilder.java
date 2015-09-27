package flexo.scenebuilder;

import flexo.model.CentralNode;
import flexo.model.Connection;
import flexo.model.Node;
import flexo.model.Scene;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 2015-09-10.
 */
public class TwoDimensionBuilder implements SceneBuilder {

    private int numberOfNodes = 5;
    private List<Node> nodes = new LinkedList<>();
    private List<Connection> connections = new LinkedList<>();

    @Override
    public Scene build() {
        Scene scene = new Scene();
        Node node;
        for (int i = 0; i < numberOfNodes; i++){
            node = new Node(i*10, 0, 0, i);
            nodes.add(node);
        }

        for (int i = 0; i < numberOfNodes - 1; i++){
            connections.add(new Connection(nodes.get(i), nodes.get(i+1), 0 , 10));
        }

        for (Connection connection : connections){
            scene.addConnection(connection);
        }

        CentralNode centralNode = new CentralNode((numberOfNodes-1)*10/2, 20, 0);
        scene.setCentralNode(centralNode);

        return scene;
    }

    @Override
    public void setNodesNumber(int number){
        if (number >= 3){
            this.numberOfNodes = number;
        }
    }
}
