package flexo.model.setupbuilder;

import flexo.model.Setup;

/**
 * Created by Piotr on 2015-09-10.
 */
public interface SceneBuilder {

    public Setup build();

    public void setBaseNodesNumber(int number);

}
