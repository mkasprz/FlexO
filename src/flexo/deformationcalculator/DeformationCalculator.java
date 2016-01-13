package flexo.deformationcalculator;

import flexo.model.Connection;
import flexo.model.Setup;
import flexo.model.SimpleNode;
import flexo.model.TypicalNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class DeformationCalculator {

    private Setup setup;

    private List<TypicalNode> nodes = new LinkedList<>();

    private boolean first = true;
    private double centralXChange;
    private double centralYChange;
    private double centralZChange;
    private int iterations = 0;
    private boolean difficulty = false;

    public DeformationCalculator(Setup setup) {
        this.setup = setup;
        createListOfNodes();
    }

    private void createListOfNodes() {
        nodes.clear();
        nodes.addAll(setup.getTypicalNodes());
//        for (Connection connection : setup.getConnections()) {
//            if (!nodes.contains(connection.getTypicalNode1())) {
//                nodes.add(connection.getTypicalNode1());
//            }
//            if (!nodes.contains(connection.getTypicalNode2())) {
//                nodes.add(connection.getTypicalNode2());
//            }
//        }
    }

    public void recalculateDeformation() {
        TypicalNode centralNode = setup.getCentralNode();
        centralXChange = centralNode.getX();
        centralYChange = centralNode.getY();
        centralZChange = centralNode.getZ();
//        setup.setPreviousCentralX(centralNode.getX());
//        setup.setPreviousCentralY(centralNode.getY());
//        setup.setPreviousCentralZ(centralNode.getZ());

        setAllNodesImba();
        first = true; // [TODO] Seems useless right now
        performCalculations();
        if (nodes.stream().anyMatch(TypicalNode::isImba)) {
            performCalculations(); //if nodes are not in balance call for another iteration
        }
    }

    private void moveNodeAccordingly(TypicalNode node, double x, double y, double z) {
        node.translateNodeFromInitialPosition(x, y, z);
//        node.translateNode(x, y, z);  // [TODO] Let user choose calculation mode
    }

    private void setAllNodesImba() {
        for (Connection connection : setup.getConnections()) {
            connection.getTypicalNode1().setImba(true);
            connection.getTypicalNode2().setImba(true);
        }
    }

    private void performCalculations() {
        for (TypicalNode node1 : nodes) {
//            if (first) {
            moveNodeAccordingly(node1, centralXChange, centralYChange, centralZChange);
            for (TypicalNode node2 : nodes) {
                performIteration(node2);
            }
//            } else {
//                performIteration(node1);
//            }
        }
        first = false;
        iterations++;
//        System.out.println(iterations);

        for (TypicalNode node : nodes) {
            checkBalance(node);
        }

        // Check if all nodes are in balance
    }

    private void checkBalance(TypicalNode node) {
        List<Connection> connectionsFromNode = setup.getConnectionsFromNode(node);
        List<Vector> forces = new LinkedList<>(); // [TODO] Don't use 'Vector' - it's better to use even normal Double[]
        Vector resultVector = new Vector(3);
        resultVector.add(0, new Double(0));
        resultVector.add(1, new Double(0));
        resultVector.add(2, new Double(0));
        for (Connection connection : connectionsFromNode) {
            Vector result;
            if (connection.getTypicalNode1().equals(node)) {
                result = getForceBetweenNodes(connection, connection.getTypicalNode1(), connection.getTypicalNode2(),
                        connection.getYoungsModulus(), connection.getBalanceLength());
            } else {
                result = getForceBetweenNodes(connection, connection.getTypicalNode2(), connection.getTypicalNode1(),
                        connection.getYoungsModulus(), connection.getBalanceLength());
            }
            forces.add(result);
        }

        double value;
        for (List vector : forces) {
            for (int i = 0; i < 3; i++) {
                value = ((Double) vector.get(i)).doubleValue() + ((Double) resultVector.get(i)).doubleValue();
                resultVector.set(i, new Double(value));
            }
        }
        if (((Double) resultVector.get(0)).doubleValue() + ((Double) resultVector.get(1)).doubleValue() + ((Double) resultVector.get(2)).doubleValue() <= 0.001) {
            node.setImba(false);
        }
    }

    private void performIteration(TypicalNode node) {
        List<Connection> connectionsFromNode = setup.getConnectionsFromNode(node);
        List<Vector> forces = new LinkedList<>();
        Vector resultVector = new Vector(3);
        resultVector.add(0, new Double(0));
        resultVector.add(1, new Double(0));
        resultVector.add(2, new Double(0));
        for (Connection connection : connectionsFromNode) {
            Vector result;
            if (connection.getTypicalNode1().equals(node)) {
                result = getForceBetweenNodes(connection, connection.getTypicalNode1(), connection.getTypicalNode2(),
                        connection.getYoungsModulus(), connection.getBalanceLength());
            } else {
                result = getForceBetweenNodes(connection, connection.getTypicalNode2(), connection.getTypicalNode1(),
                        connection.getYoungsModulus(), connection.getBalanceLength());
            }
            forces.add(result);
        }

        double value;
        for (List vector : forces) {
            for (int i = 0; i < 3; i++) {
                value = ((Double) vector.get(i)).doubleValue() + ((Double) resultVector.get(i)).doubleValue();
                resultVector.set(i, new Double(value));
            }
        }
        node.translateNode(((Double) resultVector.get(0)).doubleValue(), ((Double) resultVector.get(1)).doubleValue(), ((Double) resultVector.get(2)).doubleValue());
    }

    private Vector getForceBetweenNodes(Connection connection, SimpleNode node1, SimpleNode node2, double youngsModule, double length) {
        Vector force = new Vector(3);
        force.add(0, new Double(node2.getX() - node1.getX()) * connection.getYoungsModulus() * (connection.getLength() - connection.getBalanceLength()) / connection.getLength());
        force.add(1, new Double(node2.getY() - node1.getY()) * connection.getYoungsModulus() * (connection.getLength() - connection.getBalanceLength()) / connection.getLength());
        force.add(2, new Double(node2.getZ() - node1.getZ()) * connection.getYoungsModulus() * (connection.getLength() - connection.getBalanceLength()) / connection.getLength());

        return force;
    }
}
