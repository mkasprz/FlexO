package flexo.deformationcalculator;

import flexo.model.Connection;
import flexo.model.Setup;
import flexo.model.SimpleNode;
import flexo.model.TypicalNode;

/**
 * Created by Piotr on 2015-09-10.
 */
public class DeformationCalculator {

    private Setup scene;

    public DeformationCalculator(Setup scene) {
        this.scene = scene;
    }

    public void recalculateDeformation() {
        setAllNodesImba();
        moveNodesTowardsCentral();
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
    private void moveNodesTowardsCentral(){
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

        for (Connection connection : scene.getConnections()){
            TypicalNode typicalNode1 = connection.getTypicalNode1();
            double nodeX = typicalNode1.getX();
            double nodeY = typicalNode1.getY();
            double nodeZ = typicalNode1.getZ();
            double distance = Math.sqrt(Math.pow(nodeX - centralNodeX, 2) + Math.pow(nodeY - centralNodeY, 2) + Math.pow(nodeZ - centralNodeZ, 2));
            if (distance > maxdist) {
                maxdist = distance;
            }
            double ratio = distance/maxdist;
            ratio = Math.abs(1-ratio);
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
            ratio = distance/maxdist;
            ratio = Math.abs(1-ratio);
//            distance = Math.abs(distance);
//            double ratio = (scene.getNumberOfNodes() * 10) / distance;  //TODO : remove magical number
            if (typicalNode2.isImba()) {
                typicalNode2.translateNode(0, -Math.abs(ratio * centralNodeY),0);
                typicalNode2.setImba(false);
            }
        }
    }

    private void performCalculations(){
        for (Connection connection : scene.getConnections()) {
            //do maths in a single iteration
            performIteration(connection);
        }
        //check if all nodes are in balance
        boolean balanced = true;
        for (Connection connection : scene.getConnections()){
            if (connection.getTypicalNode2().isImba() || connection.getTypicalNode1().isImba()) {
                balanced = false;
            }
        }
        if (!balanced) {
            performCalculations(); //if nodes are not in balance call for another iteration
        }
    }

    private void performIteration(Connection connection){

    }

}
