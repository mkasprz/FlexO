package flexo.deformationcalculator;

import flexo.model.*;
import javafx.geometry.Point3D;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class DeformationCalculator {

    private Setup setup;

    private List<TypicalNode> nodes = new LinkedList<>();
    private List<Connection> connections = new LinkedList<>();

    private double xChange;
    private double yChange;
    private double zChange;
    private int iterations;
    private boolean difficulty = false;

    public DeformationCalculator(Setup setup) {
        this.setup = setup;

        nodes.addAll(setup.getTypicalNodes());

        connections.addAll(setup.getConnections());
        List<ImmovableNode> immovableNodes = setup.getImmovableNodes();
        connections.removeIf(connection -> (immovableNodes.contains(connection.getTypicalNode1()) && immovableNodes.contains(connection.getTypicalNode2())));
    }

    public void recalculateDeformation() {
        TypicalNode centralNode = setup.getCentralNode();
        xChange = centralNode.getX(); // - setup.getPreviousCentralX(); // [TODO] Let user choose calculation mode
        yChange = centralNode.getY(); // - setup.getPreviousCentralY();
        zChange = centralNode.getZ(); // - setup.getPreviousCentralZ();
        // setup.setPreviousCentralX(centralNode.getX());
        // setup.setPreviousCentralY(centralNode.getY());
        // setup.setPreviousCentralZ(centralNode.getZ());

        nodes.forEach(typicalNode -> typicalNode.setImba(true));
        iterations = 0;

        for (TypicalNode typicalNode : nodes) {
            moveNodeAccordingly(typicalNode, xChange, yChange, zChange);
        }

        nodes.forEach(this::calculateForcesAndMoveNodeAccordingly);
        double lengthSum = setup.getConnections().stream().mapToDouble(Connection::getLength).sum();
        double maximumLengthSum;

        do {
            maximumLengthSum = lengthSum;
            nodes.forEach(this::calculateForcesAndMoveNodeAccordingly); //if nodes are not in balance call for another iteration
            lengthSum = setup.getConnections().stream().mapToDouble(Connection::getLength).sum();
            iterations++;
        }
        while (nodes.stream().anyMatch(TypicalNode::isImba) || maximumLengthSum - lengthSum > 0.1 && iterations < Integer.MAX_VALUE); // [TODO] Let user choose accuracy
         System.out.println(iterations);
    }

    private void moveNodeAccordingly(TypicalNode node, double x, double y, double z) {
        node.translateNodeFromInitialPosition(x, y, z);
//        node.translateNode(x, y, z);  // [TODO] Let user choose calculation mode
    }

    private void calculateForcesAndMoveNodeAccordingly(TypicalNode node) {
        Point3D resultVector = calculateForces(node);
        double x = resultVector.getX();
        double y = resultVector.getY();
        double z = resultVector.getZ();
        node.translateNode(x, y, z);
        if (x + y + z <= 0.01) {
            node.setImba(false);
        }
    }

    private Point3D calculateForces(TypicalNode node) {
        List<Connection> connectionsFromNode = setup.getConnectionsFromNode(node);
        List<Point3D> forces = new LinkedList<>();
        Point3D resultVector = new Point3D(0,0,0);
        for (Connection connection : connectionsFromNode) {
            Point3D result;
            if (connection.getTypicalNode1().equals(node)) {
                result = getForceBetweenNodes(connection, connection.getTypicalNode1(), connection.getTypicalNode2());
            } else {
                result = getForceBetweenNodes(connection, connection.getTypicalNode2(), connection.getTypicalNode1());
            }
            forces.add(result);
        }

        for (Point3D vector : forces) {
            resultVector = resultVector.add(vector);
        }
        return resultVector;
    }

    private Point3D getForceBetweenNodes(Connection connection, SimpleNode node1, SimpleNode node2) {
        Point3D force = new Point3D(node2.getX() - node1.getX(), node2.getY() - node1.getY(), node2.getZ() - node1.getZ());
        force.normalize();
        force = force.multiply(connection.getYoungsModulus() * (connection.getLength() - connection.getBalanceLength()) / connection.getLength());
        return force;
    }
}
