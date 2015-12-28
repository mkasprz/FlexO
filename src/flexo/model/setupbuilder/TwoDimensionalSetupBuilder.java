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
            connections.add(new Connection(typicalNodes.get(i), typicalNodes.get(i+1), 0 , SetupBuilder.getDistanceBetweenNodes(typicalNodes.get(i), typicalNodes.get(i+1)), SetupBuilder.getDistanceOnCoordinate(typicalNodes.get(i), typicalNodes.get(i+1), 0), SetupBuilder.getDistanceOnCoordinate(typicalNodes.get(i), typicalNodes.get(i+1), 1), SetupBuilder.getDistanceOnCoordinate(typicalNodes.get(i), typicalNodes.get(i+1), 2)));
        }

        setup.setCentralNode(new TypicalNode(0, -1, 0, -1));

        return setup;
    }

}