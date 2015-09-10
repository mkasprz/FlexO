package FlexO.model;

/**
 * Created by Piotr on 2015-09-10.
 */
public class Connection {

    private Node node1;
    private Node node2;
    private int youngsModule;
    private int balanceLength;

    public Connection(Node node1, Node node2, int youngsModule, int balanceLength) {
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

    public int getBalanceLength() {
        return balanceLength;
    }

    public void setBalanceLength(int balanceLength) {
        this.balanceLength = balanceLength;
    }

    public int getYoungsModule() {
        return youngsModule;
    }

    public void setYoungsModule(int youngsModule) {
        this.youngsModule = youngsModule;
    }

}
