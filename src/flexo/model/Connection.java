package flexo.model;

import javax.xml.bind.annotation.XmlIDREF;

public class Connection {

    @XmlIDREF
    private TypicalNode typicalNode1;

    @XmlIDREF
    private TypicalNode typicalNode2;

    private double youngsModulus;
    private double balanceLength;

    public Connection() {
    }

    public Connection(TypicalNode typicalNode1, TypicalNode typicalNode2, double youngsModulus) {
        this.typicalNode1 = typicalNode1;
        this.typicalNode2 = typicalNode2;
        this.youngsModulus = youngsModulus;
        this.balanceLength = getLength();
    }

    public double getLength() {
        return Math.sqrt(Math.pow(typicalNode2.getX() - typicalNode1.getX(), 2) + Math.pow(typicalNode2.getY() - typicalNode1.getY(), 2) + Math.pow(typicalNode2.getZ() - typicalNode1.getZ(), 2));
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

    public double getYoungsModulus() {
        return youngsModulus;
    }

    public void setYoungsModulus(double youngsModulus) {
        this.youngsModulus = youngsModulus;
    }

}
