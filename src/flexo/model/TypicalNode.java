package flexo.model;

import javax.xml.bind.annotation.XmlID;

public class TypicalNode extends SimpleNode {

    private int id; // [TODO] Decide if this should be changed to String to avoid unnecessary 'xmlID' field
    private boolean imba = false;

    public TypicalNode() {
    }

    public TypicalNode(double x, double y, double z, int id) {
        super(x, y, z);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlID
    public String getIdAsString() {
        return Integer.toString(id);
    }

    public boolean isImba(){
        return imba;
    }

    public void setImba(boolean imba){
        this.imba = imba;
    }

}
