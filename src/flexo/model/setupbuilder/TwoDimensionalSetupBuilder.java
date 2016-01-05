package flexo.model.setupbuilder;

import flexo.model.Connection;
import flexo.model.ImmovableNode;
import flexo.model.Setup;
import flexo.model.TypicalNode;

import java.util.LinkedList;
import java.util.List;

public class TwoDimensionalSetupBuilder implements SetupBuilder {

    @Override
    public Setup build(int numberOfNodes) {
        Setup setup = new Setup();
        List connections = setup.getConnections();
        List immovableNodes = setup.getImmovableNodes();
        List typicalNodes = setup.getTypicalNodes();

        List<TypicalNode> nodes = new LinkedList<>();
        TypicalNode typicalNode;
        int id = 1;
        double n = (numberOfNodes - 1)/2.0;
        for (double i = -n; i < n + 1; i++, id++) {
            if (i == -n || i == n) {
                typicalNode = new ImmovableNode(i, 0, 0, id);
                immovableNodes.add(typicalNode);
            } else {
                typicalNode = new TypicalNode(i, 0, 0, id);
                typicalNodes.add(typicalNode);
            }
            nodes.add(typicalNode);
        }

        for (int i = 0; i < nodes.size() - 1; i++) {
            connections.add(new Connection(nodes.get(i), nodes.get(i+1), 0 , SetupBuilder.getDistanceBetweenNodes(nodes.get(i), nodes.get(i+1)), SetupBuilder.getDistanceOnCoordinate(nodes.get(i), nodes.get(i+1), 0), SetupBuilder.getDistanceOnCoordinate(nodes.get(i), nodes.get(i+1), 1), SetupBuilder.getDistanceOnCoordinate(nodes.get(i), nodes.get(i+1), 2)));
        }

        setup.setCentralNode(new TypicalNode(0, -1, 0, 0));

        return setup;
    }

}
