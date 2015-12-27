package flexo.visualization;

import flexo.model.SimpleNode;
import javafx.scene.paint.Material;
import javafx.scene.shape.Sphere;

public class VisualizedNode extends Sphere implements VisualizedElement {

    SimpleNode simpleNode;

    public VisualizedNode(SimpleNode simpleNode, int radius, Material material, int multiplier) {
        super(radius);
        this.simpleNode = simpleNode;
        setMaterial(material);
        refresh(multiplier);
    }

    @Override
    public void refresh(int multiplier) {
        setTranslateX(simpleNode.getX() * multiplier);
        setTranslateY(simpleNode.getY() * multiplier);
        setTranslateZ(simpleNode.getZ() * multiplier);
    }

}
