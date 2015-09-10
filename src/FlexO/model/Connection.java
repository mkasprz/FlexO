package FlexO.model;

/**
 * Created by Piotr on 2015-09-10.
 */
public class Connection {

    private Node node1;
    private Node node2;
    private double youngsModule;
    private double balanceLength;

    public Connection(Node node1, Node node2, double youngsModule, double balanceLength) {
        this.node1 = node1;
        this.node2 = node2;
        this.youngsModule = youngsModule;
        this.balanceLength = balanceLength;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

    public double getBalanceLength() {
        return balanceLength;
    }

    public void setBalanceLength(double balanceLength) {
        this.balanceLength = balanceLength;
    }

    public double getYoungsModule() {
        return youngsModule;
    }

    public void setYoungsModule(double youngsModule) {
        this.youngsModule = youngsModule;
    }

}
