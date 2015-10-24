package flexo.model;

/**
 * Created by Piotr on 2015-09-10.
 */
public class Connection {

    private TypicalNode typicalNode1;
    private TypicalNode typicalNode2;
    private double youngsModule;
    private double balanceLength;

    public Connection(TypicalNode typicalNode1, TypicalNode typicalNode2, double youngsModule, double balanceLength) {
        this.typicalNode1 = typicalNode1;
        this.typicalNode2 = typicalNode2;
        this.youngsModule = youngsModule;
        this.balanceLength = balanceLength;
    }

    public TypicalNode getTypicalNode1() {
        return typicalNode1;
    }

    public TypicalNode getTypicalNode2() {
        return typicalNode2;
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
