package flexo.scenebuilder;

import flexo.model.Setup;

/**
 * Created by Piotr on 2015-09-10.
 */
public interface SceneBuilder {

    public Setup build();

    public void setNodesNumber(int number);

}
