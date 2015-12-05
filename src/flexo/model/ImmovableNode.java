package flexo.model;

/**
 * Created by Piotr on 2015-11-21.
 */
public class ImmovableNode extends TypicalNode {

    public ImmovableNode(double x, double y, double z, int id) {
        super(x, y, z, id);
    }


    @Override
    public void moveNode(double x, double y, double z){
        //do something interesting
    }

    @Override
    public void translateNode(double x, double y, double z){
        //to�samo
    }

    @Override
    public void setX(double x) {
        //
    }

    @Override
    public void setY(double y) {
        //
    }

    @Override
    public void setZ(double z) {
        //
    }
}
