package flexo.deformationcalculator;

import flexo.model.Connection;
import flexo.model.Scene;

import java.util.List;
public class DeformationCalculator {

    private Scene scene;

    private void calculateDeformation(){
        moveNodesTowardsCentral();
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**=
     * this is just a stub method to see if i can get some basic
     * repositioning to work, it should be removed in the future
     */
    private void moveNodesTowardsCentral(){
        List<Connection> connections = scene.getConnections();
        for (Connection connection : connections) {

        }

    }

}
