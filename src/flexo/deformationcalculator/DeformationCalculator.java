package flexo.deformationcalculator;

import flexo.model.Connection;
import flexo.model.Scene;

/**
 * Created by Piotr on 2015-09-10.
 */
public class DeformationCalculator {

    private Scene scene;

    public void recalculateDeformation(){
        setAllNodesImba();
        moveNodesTowardsCentral();

    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setAllNodesImba(){
        if (scene != null){
            for (Connection connection : scene.getConnections()) {
                connection.getNode1().setImba(true);
                connection.getNode2().setImba(true);
            }
        }
    }

    /**
     * this is just a stub method to see if i can get some basic
     * repositioning to work, it should be removed in the future
     */
    private void moveNodesTowardsCentral(){
        double centralNodeX = scene.getCentralNode().getX();
        double centralNodeY = scene.getCentralNode().getY();
        double centralNodeZ = scene.getCentralNode().getZ();
        double maxdist = 0;

        for (Connection connection : scene.getConnections()) {
            double nodeX = connection.getNode1().getX();
            double nodeY = connection.getNode1().getY();
            double nodeZ = connection.getNode1().getZ();
            double distance = Math.abs(nodeX - centralNodeX) + Math.abs(nodeY - centralNodeY) + Math.abs(nodeZ - centralNodeZ);
            if (distance > maxdist) {
                maxdist = distance;
            }

            nodeX = connection.getNode2().getX();
            nodeY = connection.getNode2().getY();
            nodeZ = connection.getNode2().getZ();
            distance = Math.abs(nodeX - centralNodeX) + Math.abs(nodeY - centralNodeY) + Math.abs(nodeZ - centralNodeZ);
            if (distance > maxdist) {
                maxdist = distance;
            }
        }

        for (Connection connection : scene.getConnections()){
            double nodeX = connection.getNode1().getX();
            double nodeY = connection.getNode1().getY();
            double nodeZ = connection.getNode1().getZ();
            double distance = Math.abs(nodeX - centralNodeX) + Math.abs(nodeY - centralNodeY) + Math.abs(nodeZ - centralNodeZ);
            if (distance > maxdist) {
                maxdist = distance;
            }
            double ratio = distance/maxdist;
            ratio = Math.abs(1-ratio);
//            distance = Math.abs(distance);
//            double ratio = (scene.getNumberOfNodes() * 10) / distance;  //TODO : remove magical number
            if (connection.getNode1().isImba()) {
                connection.getNode1().translateNode(0, ratio * centralNodeY,0);
                connection.getNode1().setImba(false);
            }

            nodeX = connection.getNode2().getX();
            nodeY = connection.getNode2().getY();
            nodeZ = connection.getNode2().getZ();
            distance = Math.abs(nodeX - centralNodeX) + Math.abs(nodeY - centralNodeY) + Math.abs(nodeZ - centralNodeZ);
            if (distance > maxdist) {
                maxdist = distance;
            }
            ratio = distance/maxdist;
            ratio = Math.abs(1-ratio);
//            distance = Math.abs(distance);
//            double ratio = (scene.getNumberOfNodes() * 10) / distance;  //TODO : remove magical number
            if (connection.getNode2().isImba()) {
                connection.getNode2().translateNode(0, ratio * centralNodeY,0);
                connection.getNode2().setImba(false);
            }
        }


    }

}
