package flexo.model;

public class ImmovableNode extends TypicalNode {

    public ImmovableNode() {
    }

    public ImmovableNode(double x, double y, double z, int id) {
        super(x, y, z, id);
    }

    @Override
    public void moveNode(double x, double y, double z) {
    }

    @Override
    public void translateNode(double x, double y, double z) {
    }

    @Override
    public void translateNodeFromInitialPosition(double x, double y, double z) {
    }


}
