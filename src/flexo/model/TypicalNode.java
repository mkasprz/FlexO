package flexo.model;

/**
 * Created by Piotr on 2015-09-10.
 */
public class TypicalNode extends SimpleNode {

    private double parameter;
    private int id;
    private boolean imba = false;

    public TypicalNode(double x, double y, double z, int id) {
        super(x, y, z);
        this.id = id;
    }

    public double getParameter() {
        return parameter;
    }

    public void setParameter(double parameter) {
        this.parameter = parameter;
    }

    public int getId() {
        return id;
    }

    public boolean isImba(){
        return imba;
    }

    public void setImba(boolean imba){
        this.imba = imba;
    }
}
