package flexo.modelconverter;

import flexo.model.Connection;
import flexo.model.Node;
import flexo.model.Scene;

import java.util.LinkedList;
import java.util.List;

public class ModelConverter {

    public static List<Node> convert(Scene scene) {

        List<Node> nodes = new LinkedList<>();
        for (Connection connection : scene.getConnections()){
            if (!nodes.contains(connection.getNode1())){
                nodes.add(connection.getNode1());
            }
            if (!nodes.contains(connection.getNode2())) {
                nodes.add(connection.getNode2());
            }
        }
        return nodes;
    }
}
