package FlexO.model;

/**
 * Created by Piotr on 2015-09-10.
 */
public class Node {

    public double x;
    public double y;
    public double z;
    public int id;

    public Node(double x, double y, double z, int id) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
    }

    public void moveNodeTo(double x, double y, double z){
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

    public int getId() {
        return id;
    }
}
