package flexo.scenebuilder;

import flexo.model.SimpleNode;
import flexo.model.Connection;
import flexo.model.TypicalNode;
import flexo.model.Scene;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 2015-09-10.
 */
public class TwoDimensionBuilder implements SceneBuilder {

    private int numberOfNodes = 5;
    private List<TypicalNode> typicalNodes = new LinkedList<>();
    private List<Connection> connections = new LinkedList<>();

    @Override
    public Scene build() {
        Scene scene = new Scene();
        TypicalNode typicalNode;
        for (int i = 0; i < numberOfNodes; i++) {
            typicalNode = new TypicalNode(i*10, 0, 0, i);
            typicalNodes.add(typicalNode);
        }

        for (int i = 0; i < numberOfNodes - 1; i++) {
            connections.add(new Connection(typicalNodes.get(i), typicalNodes.get(i+1), 0 , 10));
        }

        for (Connection connection : connections){
            scene.addConnection(connection);
        }

        SimpleNode simpleNode = new SimpleNode((numberOfNodes-1)*10/2, 20, 0);
        scene.setCentralNode(simpleNode);

        return scene;
    }

    @Override
    public void setNodesNumber(int number) {
        if (number >= 3){
            this.numberOfNodes = number;
        }
    }
}
