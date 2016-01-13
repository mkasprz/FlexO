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
        List immovableNodes = setup.getImmovableNodes();
        List typicalNodes = setup.getTypicalNodes();
        List connections = setup.getConnections();

        double n = (numberOfNodes - 1)/2.0;
        TypicalNode typicalNode;
        List<TypicalNode> nodes = new LinkedList<>();

        typicalNode = new ImmovableNode(-n, 0, 0, 1);
        immovableNodes.add(typicalNode);
        nodes.add(typicalNode);

        double stepSize = Math.PI / (numberOfNodes - 1);
        double angle = stepSize;
        for (int i = 2; i < numberOfNodes; i++, angle += stepSize) {
            typicalNode = new TypicalNode(-n * Math.cos(angle), n * Math.sin(angle), 0, i);
            typicalNodes.add(typicalNode);
            nodes.add(typicalNode);
        }

        typicalNode = new ImmovableNode(n, 0, 0, numberOfNodes);
        immovableNodes.add(typicalNode);
        nodes.add(typicalNode);

        for (int i = 0; i < nodes.size() - 1; i++) {
            connections.add(new Connection(nodes.get(i), nodes.get(i+1), 0.07));
        }

        setup.setCentralNode(new TypicalNode());

        return setup;
    }

}
