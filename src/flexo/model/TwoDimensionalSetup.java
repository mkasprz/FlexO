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
            connections.add(new Connection(typicalNodes.get(i), typicalNodes.get(i+1), 0 , 10));
        }

        centralNode = new TypicalNode(0, -1, 0, -1);
    }
}
