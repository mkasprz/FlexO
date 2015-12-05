package flexo.model;

/**
 * Created by Piotr on 2015-09-10.
 */
public class SimpleNode {

    private double x;
    private double y;
    private double z;

    public SimpleNode(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void moveNode(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void translateNode(double x, double y, double z){
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
