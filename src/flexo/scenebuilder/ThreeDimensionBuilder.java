package flexo.scenebuilder;

import flexo.model.Connection;
import flexo.model.Setup;
import flexo.model.TypicalNode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 2015-09-10.
 */
public class ThreeDimensionBuilder implements SceneBuilder {

    private int numberOfNodesInBase = 10;

    @Override
    public Setup build() {
        List<List<TypicalNode>> nodesLayers = new LinkedList<>();
        List<Connection> connections = new LinkedList<>();

        int diameter = numberOfNodesInBase / 2;

        Setup scene = new Setup();
        TypicalNode typicalNode;

        double r = diameter;
        double y = 0;
        double stepSize = Math.PI / numberOfNodesInBase;
        int id = 0;
        for (double k = stepSize; k <= Math.PI / 2; k += stepSize) {
            List typicalNodes = new LinkedList<>();
            for (double i = 0; i < 2 * Math.PI; i += 2 * stepSize) {
                typicalNode = new TypicalNode(r * Math.cos(i), y, r * Math.sin(i), id);
                typicalNodes.add(typicalNode);
                id++;
            }
            nodesLayers.add(typicalNodes);
            y = - diameter * Math.sin(k);
            r = diameter * Math.cos(k);
        }

        nodesLayers.add(Arrays.asList(new TypicalNode(0, y, 0, id)));

        for (int k = 1; k < nodesLayers.size(); k++) {
            List<TypicalNode> typicalNodes = nodesLayers.get(k - 1);
            TypicalNode typicalNode1 = typicalNodes.get(0);
            for (int i = 0; i < typicalNodes.size(); i++) {
                List<TypicalNode> nextNodesLayer = nodesLayers.get(k);
                TypicalNode typicalNode2 = typicalNodes.get((i + 1) % typicalNodes.size());
                connections.add(new Connection(typicalNode1, typicalNode2, 0, 10));
                connections.add(new Connection(typicalNode1, nextNodesLayer.get(i % nextNodesLayer.size()), 0, 10));
                typicalNode1 = typicalNode2;
            }
            connections.add(new Connection(typicalNode1, typicalNodes.get(0), 0, 10));
        }

        scene.addConnections(connections);

        TypicalNode centralNode = new TypicalNode(0, 0, 0, -1);
        scene.setCentralNode(centralNode);

        return scene;
    }

    @Override
    public void setBaseNodesNumber(int number) {
        if (number > 1) {
            this.numberOfNodesInBase = number;
        }
    }
}
