package flexo.model;

import javax.xml.bind.annotation.XmlIDREF;

public class Connection {

    @XmlIDREF
    private TypicalNode typicalNode1;

    @XmlIDREF
    private TypicalNode typicalNode2;

    private double youngsModulus;
    private double balanceLength; // [TODO] I believe, that this should be rather considered as multiplier
    private double balanceX;
    private double balanceY;
    private double balanceZ;

    public Connection() {
    }

    public Connection(TypicalNode typicalNode1, TypicalNode typicalNode2, double youngsModulus, double balanceLength, double balanceX, double balanceY, double balanceZ) {
        this.typicalNode1 = typicalNode1;
        this.typicalNode2 = typicalNode2;
        this.youngsModulus = youngsModulus;
        this.balanceLength = balanceLength;
        this.balanceX = balanceX;
        this.balanceY = balanceY;
        this.balanceZ = balanceZ;
    }

    public double getLength(){
        return(Math.sqrt(Math.pow(typicalNode2.getX()-typicalNode1.getX(), 2)+Math.pow(typicalNode2.getY()-typicalNode1.getY(), 2)+Math.pow(typicalNode2.getZ()-typicalNode1.getZ(), 2)));
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
