package flexo.scenebuilder;

import flexo.model.Scene;

/**
 * Created by Piotr on 2015-09-10.
 */
public interface SceneBuilder {

    public Scene build();

    public void setNodesNumber(int number);

}