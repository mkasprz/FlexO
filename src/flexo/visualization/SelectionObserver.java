package flexo.visualization;

import flexo.model.Connection;
import flexo.model.TypicalNode;

public interface SelectionObserver {

    void selectedTypicalNode(TypicalNode typicalNode);

    void selectedConnection(Connection connection);

}
