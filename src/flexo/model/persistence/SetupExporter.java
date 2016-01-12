package flexo.model.persistence;

import eu.mihosoft.vrl.v3d.CSG;
import eu.mihosoft.vrl.v3d.Cylinder;
import eu.mihosoft.vrl.v3d.Sphere;
import eu.mihosoft.vrl.v3d.Vector3d;
import flexo.model.Connection;
import flexo.model.Setup;
import flexo.model.SimpleNode;
import flexo.model.TypicalNode;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SetupExporter {

    public static void exportToOBJFile(Setup setup, File file) throws IOException {
        double radius = 0.2;
        int slices = 20;
        int stacks = 10;

        Vector3d vector = getVector3d(setup.getCentralNode());
        CSG union = new Sphere(vector, radius, slices, stacks).toCSG();

        List<TypicalNode> typicalNodes = setup.getTypicalNodes();
        typicalNodes.addAll(setup.getImmovableNodes());
        for (TypicalNode typicalNode : typicalNodes) {
            vector = getVector3d(typicalNode);
            union = union.dumbUnion(new Sphere(vector, radius, slices, stacks).toCSG());
        }

        for (Connection connection : setup.getConnections()) {
            Vector3d vector1 = getVector3d(connection.getTypicalNode1());
            Vector3d vector2 = getVector3d(connection.getTypicalNode2());
            union = union.dumbUnion(new Cylinder(vector1, vector2, radius / 3, slices).toCSG());
        }

        union.toObj().toFiles(file.toPath());
    }

    private static Vector3d getVector3d(SimpleNode node) {
        return new Vector3d(node.getX(), node.getY(), node.getZ());
    }

}