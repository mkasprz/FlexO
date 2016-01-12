package flexo.deformationcalculator;

import flexo.model.Connection;
import flexo.model.Setup;
import flexo.model.SimpleNode;
import flexo.model.TypicalNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class DeformationCalculator {

    private Setup scene;

    private List<TypicalNode> nodesList = new LinkedList<>();

    public DeformationCalculator(Setup scene) {
        this.scene = scene;
        createListofNodes();

    }

    private void createListofNodes() {
        nodesList.clear();
        for (Connection connection : scene.getConnections()) {
            if (!nodesList.contains(connection.getTypicalNode1())) {
                nodesList.add(connection.getTypicalNode1());
            }
            if (!nodesList.contains(connection.getTypicalNode2())) {
                nodesList.add(connection.getTypicalNode2());
            }
        }
    }

    public void recalculateDeformation() {
        double centralXChange = scene.getCentralNode().getX() - scene.getPreviousCentralX();
        double centralYChange = scene.getCentralNode().getY() - scene.getPreviousCentralY();
        double centralZChange = scene.getCentralNode().getZ() - scene.getPreviousCentralZ();
        scene.setPreviousCentralX(scene.getCentralNode().getX());
        scene.setPreviousCentralY(scene.getCentralNode().getY());
        scene.setPreviousCentralZ(scene.getCentralNode().getZ());

        moveAllNodesAccordingly(centralXChange, centralYChange, centralZChange);
        setAllNodesImba();
//        moveNodesTowardsCentral();
        //moveOneNodeForTest(pos, val);
        performCalculations();
    }

    private void moveAllNodesAccordingly(double x, double y, double z) {
        for (TypicalNode node : nodesList){
            node.translateNode(x, y, z);
        }
    }

    private void setAllNodesImba() {
        for (Connection connection : scene.getConnections()) {
            connection.getTypicalNode1().setImba(true);
            connection.getTypicalNode2().setImba(true);
        }
    }

    /**
     * this is just a stub method to see if i can get some basic
     * repositioning to work, it should be removed in the future
     */
    private void moveNodesTowardsCentral() {
        SimpleNode centralNode = scene.getCentralNode();
        double centralNodeX = centralNode.getX();
        double centralNodeY = centralNode.getY();
        double centralNodeZ = centralNode.getZ();
        double maxdist = 0;

        for (Connection connection : scene.getConnections()) {
            TypicalNode typicalNode1 = connection.getTypicalNode1();
            double nodeX = typicalNode1.getX();
            double nodeY = typicalNode1.getY();
            double nodeZ = typicalNode1.getZ();
            //double distance = Math.abs(nodeX - centralNodeX) + Math.abs(nodeY - centralNodeY) + Math.abs(nodeZ - centralNodeZ);
            double distance = Math.sqrt(Math.pow(nodeX - centralNodeX, 2) + Math.pow(nodeY - centralNodeY, 2) + Math.pow(nodeZ - centralNodeZ, 2));
            if (distance > maxdist) {
                maxdist = distance;
            }

            TypicalNode typicalNode2 = connection.getTypicalNode2();
            nodeX = typicalNode2.getX();
            nodeY = typicalNode2.getY();
            nodeZ = typicalNode2.getZ();
            distance = Math.sqrt(Math.pow(nodeX - centralNodeX, 2) + Math.pow(nodeY - centralNodeY, 2) + Math.pow(nodeZ - centralNodeZ, 2));
            if (distance > maxdist) {
                maxdist = distance;
            }
        }

        for (Connection connection : scene.getConnections()) {
            TypicalNode typicalNode1 = connection.getTypicalNode1();
            double nodeX = typicalNode1.getX();
            double nodeY = typicalNode1.getY();
            double nodeZ = typicalNode1.getZ();
            double distance = Math.sqrt(Math.pow(nodeX - centralNodeX, 2) + Math.pow(nodeY - centralNodeY, 2) + Math.pow(nodeZ - centralNodeZ, 2));
            if (distance > maxdist) {
                maxdist = distance;
            }
            double ratio = distance / maxdist;
            ratio = Math.abs(1 - ratio);
//            distance = Math.abs(distance);
//            double ratio = (scene.getNumberOfNodes() * 10) / distance;  //TODO : remove magical number
            if (typicalNode1.isImba()) {
                typicalNode1.translateNode(0, -Math.abs(ratio * centralNodeY), 0);
                typicalNode1.setImba(false);
            }

            TypicalNode typicalNode2 = connection.getTypicalNode2();
            nodeX = typicalNode2.getX();
            nodeY = typicalNode2.getY();
            nodeZ = typicalNode2.getZ();
            distance = Math.sqrt(Math.pow(nodeX - centralNodeX, 2) + Math.pow(nodeY - centralNodeY, 2) + Math.pow(nodeZ - centralNodeZ, 2));
            if (distance > maxdist) {
                maxdist = distance;
            }
            ratio = distance / maxdist;
            ratio = Math.abs(1 - ratio);
//            distance = Math.abs(distance);
//            double ratio = (scene.getNumberOfNodes() * 10) / distance;  //TODO : remove magical number
            if (typicalNode2.isImba()) {
                typicalNode2.translateNode(0, -Math.abs(ratio * centralNodeY), 0);
                typicalNode2.setImba(false);
            }
        }
    }

    //TODO: this is horrible
    private void moveOneNodeForTest(int pos, double val) {
        if (nodesList.size() > 20) {
            if (pos == 1) {
                nodesList.get(nodesList.size() - 1).translateNode(val, 0, 0);
            } else if (pos == 2) {
                nodesList.get(nodesList.size() - 1).translateNode(0, val, 0);
            } else if (pos == 3) {
                nodesList.get(nodesList.size() - 1).translateNode(0, 0, val);
            }

        } else {
            if (pos == 1) {
                nodesList.get((int) Math.floor(nodesList.size() / 2)).translateNode(val, 0, 0);
            } else if (pos == 2) {
                nodesList.get((int) Math.floor(nodesList.size() / 2)).translateNode(0, val, 0);
            } else if (pos == 3) {
                nodesList.get((int) Math.floor(nodesList.size() / 2)).translateNode(0, 0, val);
            }

        }
    }

    // TODO : there is still nothing going between central and normal nodes
    private void performCalculations() {
        for (TypicalNode node1 : nodesList) {
            //requires some consideration
            for (TypicalNode node2 : nodesList) {
                //do maths in a single iteration
                performIteration(node2);
            }
        }

        for (TypicalNode node : nodesList){
            checkBalance(node);
        }


        //check if all nodes are in balance
        boolean balanced = true;
        for (TypicalNode node : nodesList){
            if (node.isImba()) {
                balanced = false;
            }
        }
        if (!balanced) {
            performCalculations(); //if nodes are not in balance call for another iteration
        }
    }

    private void checkBalance(TypicalNode node) {
        List<Connection> connectionsFromNode = scene.getConnectionsFromNode(node);
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
        if(((Double)resultVector.get(0)).doubleValue() + ((Double)resultVector.get(1)).doubleValue() + ((Double)resultVector.get(2)).doubleValue() <= 0.01){
            node.setImba(false);
        }
    }

    private void performIteration(TypicalNode node) {
        List<Connection> connectionsFromNode = scene.getConnectionsFromNode(node);
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
        //TODO : redo using all the fancy hook laws and stuff
        force.add(0, new Double(connection.getBalanceX() - (Math.abs(node2.getX() - node1.getX()))) * 0.002);
        force.add(1, new Double(connection.getBalanceY() - (Math.abs(node2.getY() - node1.getY()))) * 0.002);
        force.add(2, new Double(connection.getBalanceZ() - (Math.abs(node2.getZ() - node1.getZ()))) * 0.002);

        return force;
    }
}
