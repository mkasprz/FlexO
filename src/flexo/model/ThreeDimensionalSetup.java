package flexo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kcpr on 06.12.15.
 */
public class ThreeDimensionalSetup extends Setup {

    public ThreeDimensionalSetup(int numberOfNodesInBase) {
        if (numberOfNodesInBase < 2) {
            numberOfNodesInBase = 2;
        }

        double diameter = numberOfNodesInBase / 2;

        int id = 0;
        double stepSize = Math.PI / numberOfNodesInBase;
        List<TypicalNode> typicalNodes = new LinkedList<>();
        for (double k = 0; k < Math.PI / 2; k += stepSize) {
            double y = diameter * Math.sin(k);
            double r = diameter * Math.cos(k);
            TypicalNode typicalNode1 = null;
            double angle = 0;
            for (int i = 0; i < numberOfNodesInBase; i++, angle += 2 * stepSize, id++) {
                TypicalNode typicalNode2;
                if (k == 0) {
                    typicalNode2 = new ImmovableNode(r * Math.cos(angle), 0, r * Math.sin(angle), id);
                    typicalNodes.add(typicalNode2);
                } else {
                    typicalNode2 = new TypicalNode(r * Math.cos(angle), y, r * Math.sin(angle), id);
                    connections.add(new Connection(typicalNodes.get(i), typicalNode2, 0, 10));
                    typicalNodes.set(i, typicalNode2);
                }
                if (i != 0) {
                    connections.add(new Connection(typicalNode1, typicalNode2, 0, 10));
                }
                typicalNode1 = typicalNode2;
            }
            connections.add(new Connection(typicalNode1, typicalNodes.get(0), 0, 10));
        }

        TypicalNode typicalNode2 = new TypicalNode(0, diameter, 0, id);
        for (TypicalNode typicalNode1 : typicalNodes) {
            connections.add(new Connection(typicalNode1, typicalNode2, 0, 10));
        }

        centralNode = new TypicalNode(0, 0, 0, -1);
    }
}
