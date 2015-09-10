package FlexO.model;

/**
 * Created by Piotr on 2015-09-10.
 */
public class Node {

    public int x;
    public int y;
    public int z;
    public int id;

    public Node(int x, int y, int z, int id) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
    }

    public void moveNodeTo(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void translateNode(int x, int y, int z){
        this.x = this.x + x;
        this.y = this.y + y;
        this.z = this.z + z;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getId() {
        return id;
    }
}
