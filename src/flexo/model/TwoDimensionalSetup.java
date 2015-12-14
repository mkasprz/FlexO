package flexo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kcpr on 06.12.15.
 */
public class TwoDimensionalSetup extends Setup {

    public TwoDimensionalSetup(int numberOfNodes) {
        if (numberOfNodes < 3) {
            numberOfNodes = 3;
        }

        List<TypicalNode> typicalNodes = new LinkedList<>();
        TypicalNode typicalNode;
        int id = 0;
        double n = (numberOfNodes - 1)/2.0;
        for (double i = -n; i < n + 1; i++, id++) {
            if (i == -n || i == n + 1) {
                typicalNode = new ImmovableNode(i, 0, 0, id);
            } else {
                typicalNode = new TypicalNode(i, 0, 0, id);
            }
            typicalNodes.add(typicalNode);
        }

        for (int i = 0; i < typicalNodes.size() - 1; i++) {
            connections.add(new Connection(typicalNodes.get(i), typicalNodes.get(i+1), 0 , getDistanceBetweenNodes(typicalNodes.get(i), typicalNodes.get(i+1)), getDistanceOnCoordinate(typicalNodes.get(i), typicalNodes.get(i+1), 0), getDistanceOnCoordinate(typicalNodes.get(i), typicalNodes.get(i+1), 1), getDistanceOnCoordinate(typicalNodes.get(i), typicalNodes.get(i+1), 2)));
        }

        centralNode = new TypicalNode(0, -1, 0, -1);
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
