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

        for (Connection connection : scene.getConnections()){
            double distance = connection.getNode1().getY() - scene.getCentralNode().getY();
            distance = Math.abs(distance);
            double ratio = (scene.getNumberOfNodes() * 10) / distance;  //TODO : remove magical number
            connection.getNode1().translateNode(0,ratio * 10,0);
            connection.getNode1().setImba(false);

            distance = connection.getNode2().getY() - scene.getCentralNode().getY();
            distance = Math.abs(distance);
            ratio = (scene.getNumberOfNodes() * 10) / distance;  //TODO : remove magical number
            connection.getNode2().translateNode(0,ratio * 10,0);
            connection.getNode2().setImba(false);
        }


    }

}
