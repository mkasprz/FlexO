package flexo.model;

/**
 * Created by Piotr on 2015-09-10.
 */
public class Node {

    private double x;
    private double y;
    private double z;
    private double parameter;
    private int id;
    private boolean imba = false;

    public Node(double x, double y, double z, int id) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
    }

    public void moveNode(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void translateNode(double x, double y, double z) {
        this.x = this.x + x;
        this.y = this.y + y;
        this.z = this.z + z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
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
