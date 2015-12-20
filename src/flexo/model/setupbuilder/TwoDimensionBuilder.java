package flexo.model.setupbuilder;

import flexo.model.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 2015-09-10.
 */
public class TwoDimensionBuilder implements SceneBuilder {

    private int numberOfNodes = 10;

    @Override
    public Setup build() {
        List<TypicalNode> typicalNodes = new LinkedList<>();
        List<Connection> connections = new LinkedList<>();
        Setup scene = new Setup();
        TypicalNode typicalNode;
        int id = 0;
        double n = (numberOfNodes - 1)/2.0;
        for (double i = -n; i < n + 1; i++, id++) {
            if (i == -n || i == n) {
                typicalNode = new ImmovableNode(i, 0, 0, id);
            } else {
                typicalNode = new TypicalNode(i, 0, 0, id);
            }
            typicalNodes.add(typicalNode);
        }

        for (int i = 0; i < typicalNodes.size() - 1; i++) {
            connections.add(new Connection(typicalNodes.get(i), typicalNodes.get(i+1), 0 , getDistanceBetweenNodes(typicalNodes.get(i), typicalNodes.get(i+1)), getDistanceOnCoordinate(typicalNodes.get(i), typicalNodes.get(i+1), 0), getDistanceOnCoordinate(typicalNodes.get(i), typicalNodes.get(i+1), 1), getDistanceOnCoordinate(typicalNodes.get(i), typicalNodes.get(i+1), 2)));
        }

        scene.addConnections(connections);

        TypicalNode centralNode = new TypicalNode(0, -1, 0, -1);
        scene.setCentralNode(centralNode);

        return scene;
    }

    @Override
    public void setBaseNodesNumber(int number) {
        if (number > 2){
            this.numberOfNodes = number;
        }
    }

    private double getDistanceBetweenNodes(TypicalNode node1, TypicalNode node2){
        return (Math.sqrt(Math.pow(node1.getX()-node2.getX(),2) + Math.pow(node1.getY()-node2.getY(),2) + Math.pow(node1.getZ()-node2.getZ(),2)));
    }

    private double getDistanceOnCoordinate(TypicalNode node1, TypicalNode node2, int coordinate){
        if (coordinate == 0) {
            return Math.abs(node1.getX()-node2.getX());
        } else if (coordinate == 1) {
            return Math.abs(node1.getY()-node2.getY());
        } else if (coordinate == 2) {
            return Math.abs(node1.getZ()-node2.getZ());
        } else {
            return 0;
        }
    }
}
