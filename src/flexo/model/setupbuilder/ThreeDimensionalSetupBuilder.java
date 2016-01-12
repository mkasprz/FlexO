package flexo.model.setupbuilder;

import flexo.model.Connection;
import flexo.model.ImmovableNode;
import flexo.model.Setup;
import flexo.model.TypicalNode;

import java.util.LinkedList;
import java.util.List;

public class ThreeDimensionalSetupBuilder implements SetupBuilder {

    @Override
    public Setup build(int numberOfNodesInBase) {
        Setup setup = new Setup();
        List connections = setup.getConnections();
        List immovableNodes = setup.getImmovableNodes();
        List typicalNodes = setup.getTypicalNodes();

        double diameter = numberOfNodesInBase / 2;

        List<TypicalNode> nodes = new LinkedList<>();
        int id = 1;
        double stepSize = Math.PI / numberOfNodesInBase;
        for (double k = 0; k < Math.PI / 2; k += stepSize) {
            double y = diameter * Math.sin(k);
            double r = diameter * Math.cos(k);
            TypicalNode typicalNode1 = null;
            double angle = 0;
            for (int i = 0; i < numberOfNodesInBase; i++, angle += 2 * stepSize, id++) {
                TypicalNode typicalNode2;
                if (k == 0) {
                    typicalNode2 = new ImmovableNode(r * Math.cos(angle), 0, r * Math.sin(angle), id);
                    immovableNodes.add(typicalNode2);
                    nodes.add(typicalNode2);
                } else {
                    typicalNode2 = new TypicalNode(r * Math.cos(angle), y, r * Math.sin(angle), id);
                    connections.add(new Connection(nodes.get(i), typicalNode2, 0.07, SetupBuilder.getDistanceBetweenNodes(nodes.get(i), typicalNode2), SetupBuilder.getDistanceOnCoordinate(nodes.get(i), typicalNode2, 0), SetupBuilder.getDistanceOnCoordinate(nodes.get(i), typicalNode2, 1), SetupBuilder.getDistanceOnCoordinate(nodes.get(i), typicalNode2, 2)));
                    typicalNodes.add(typicalNode2);
                    nodes.set(i, typicalNode2);
                }
                if (i != 0) {
                    connections.add(new Connection(typicalNode1, typicalNode2, 0.07, SetupBuilder.getDistanceBetweenNodes(typicalNode1, typicalNode2), SetupBuilder.getDistanceOnCoordinate(typicalNode1, typicalNode2, 0), SetupBuilder.getDistanceOnCoordinate(typicalNode1, typicalNode2, 1), SetupBuilder.getDistanceOnCoordinate(typicalNode1, typicalNode2, 2)));
                }
                typicalNode1 = typicalNode2;
            }
            connections.add(new Connection(typicalNode1, nodes.get(0), 0.07, SetupBuilder.getDistanceBetweenNodes(typicalNode1, nodes.get(0)), SetupBuilder.getDistanceOnCoordinate(typicalNode1, nodes.get(0), 0), SetupBuilder.getDistanceOnCoordinate(typicalNode1, nodes.get(0), 1), SetupBuilder.getDistanceOnCoordinate(typicalNode1, nodes.get(0), 2)));
        }

        TypicalNode typicalNode2 = new TypicalNode(0, diameter, 0, id);
        typicalNodes.add(typicalNode2);
        for (TypicalNode typicalNode1 : nodes) {
            connections.add(new Connection(typicalNode1, typicalNode2, 0.07, SetupBuilder.getDistanceBetweenNodes(typicalNode1, typicalNode2), SetupBuilder.getDistanceOnCoordinate(typicalNode1, typicalNode2, 0), SetupBuilder.getDistanceOnCoordinate(typicalNode1, typicalNode2, 1), SetupBuilder.getDistanceOnCoordinate(typicalNode1, typicalNode2, 2)));
        }

        setup.setCentralNode(new TypicalNode());

        return setup;
    }

}
