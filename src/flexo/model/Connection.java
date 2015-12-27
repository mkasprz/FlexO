package flexo.model;

import javax.xml.bind.annotation.XmlIDREF;

public class Connection {

    @XmlIDREF
    private TypicalNode typicalNode1;

    @XmlIDREF
    private TypicalNode typicalNode2;

    private double youngsModule;
    private double balanceLength; // [TODO] I believe, that this should be rather considered as multiplier
    private double balanceX;
    private double balanceY;
    private double balanceZ;

    public Connection() {
    }

    public Connection(TypicalNode typicalNode1, TypicalNode typicalNode2, double youngsModule, double balanceLength, double balanceX, double balanceY, double balanceZ) {
        this.typicalNode1 = typicalNode1;
        this.typicalNode2 = typicalNode2;
        this.youngsModule = youngsModule;
        this.balanceLength = balanceLength;
        this.balanceX = balanceX;
        this.balanceY = balanceY;
        this.balanceZ = balanceZ;
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

    public double getBalanceX() {
        return balanceX;
    }

    public void setBalanceX(double balanceX) {
        this.balanceX = balanceX;
    }

    public double getBalanceY() {
        return balanceY;
    }

    public void setBalanceY(double balanceY) {
        this.balanceY = balanceY;
    }

    public double getBalanceZ() {
        return balanceZ;
    }

    public void setBalanceZ(double balanceZ) {
        this.balanceZ = balanceZ;
    }
}
