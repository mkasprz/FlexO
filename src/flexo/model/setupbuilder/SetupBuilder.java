package flexo.model.setupbuilder;

import flexo.model.Setup;
import flexo.model.TypicalNode;

public interface SetupBuilder {

    Setup build(int number);

    static double getDistanceBetweenNodes(TypicalNode node1, TypicalNode node2) {
        return (Math.sqrt(Math.pow(node1.getX() - node2.getX(), 2) + Math.pow(node1.getY() - node2.getY(), 2) + Math.pow(node1.getZ() - node2.getZ(), 2)));
    }

    static double getDistanceOnCoordinate(TypicalNode node1, TypicalNode node2, int coordinate) { // [TODO] This seems so bad to me. I would just do this in 'Connection' constructor
        if (coordinate == 0) {
            return Math.abs(node1.getX() - node2.getX());
        } else if (coordinate == 1) {
            return Math.abs(node1.getY() - node2.getY());
        } else if (coordinate == 2) {
            return Math.abs(node1.getZ() - node2.getZ());
        } else {
            return 0;
        }
    }

}
