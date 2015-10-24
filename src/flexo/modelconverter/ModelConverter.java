package flexo.modelconverter;

import flexo.model.Connection;
import flexo.model.TypicalNode;
import flexo.model.Scene;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 2015-09-10.
 */
public class ModelConverter {

    public static List<TypicalNode> convert(Scene scene) {

        List<TypicalNode> typicalNodes = new LinkedList<>();
        for (Connection connection : scene.getConnections()){
            if (!typicalNodes.contains(connection.getTypicalNode1())){
                typicalNodes.add(connection.getTypicalNode1());
            }
            if (!typicalNodes.contains(connection.getTypicalNode2())) {
                typicalNodes.add(connection.getTypicalNode2());
            }
        }
        return typicalNodes;
    }
}
