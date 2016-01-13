package flexo.model;

public class SimpleNode {

    private double initialX;
    private double initialY;
    private double initialZ;
    private double x;
    private double y;
    private double z;

    public SimpleNode() {
    }

    public SimpleNode(double x, double y, double z) {
        this.x = initialX = x;
        this.y = initialY = y;
        this.z = initialZ = z;
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

    public void translateNodeFromInitialPosition(double x, double y, double z) {
        this.x = initialX + x;
        this.y = initialY + y;
        this.z = initialZ + z;
    }

    public double getInitialX() {
        return initialX;
    }

    public void setInitialX(double initialX) {
        this.initialX = initialX;
    }

    public double getInitialY() {
        return initialY;
    }

    public void setInitialY(double initialY) {
        this.initialY = initialY;
    }

    public double getInitialZ() {
        return initialZ;
    }

    public void setInitialZ(double initialZ) {
        this.initialZ = initialZ;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

}
