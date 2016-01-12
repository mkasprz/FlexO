package flexo.visualization;

import flexo.model.Connection;
import flexo.model.SimpleNode;
import javafx.geometry.Point3D;
import javafx.scene.paint.Material;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;

public class VisualizedConnection extends Cylinder implements VisualizedElement {

    private Connection connection;

    public VisualizedConnection(Connection connection, int radius, Material material, int multiplier) {
        this.connection = connection;
        setRadius(radius);
        setMaterial(material);
        refresh(multiplier);
    }

    @Override
    public void refresh(int multiplier) {
        SimpleNode simpleNode1 = connection.getTypicalNode1();
        SimpleNode simpleNode2 = connection.getTypicalNode2();

        Point3D point1 = new Point3D(simpleNode1.getX() * multiplier, simpleNode1.getY() * multiplier, simpleNode1.getZ() * multiplier);
        Point3D point2 = new Point3D(simpleNode2.getX() * multiplier, simpleNode2.getY() * multiplier, simpleNode2.getZ() * multiplier);

        Point3D middlePoint = point1.midpoint(point2);
        setTranslateX(middlePoint.getX());
        setTranslateY(middlePoint.getY());
        setTranslateZ(middlePoint.getZ());

        Point3D displacementVector = point1.subtract(point2);
        setRotationAxis(displacementVector.crossProduct(Rotate.Y_AXIS));
        setRotate(-displacementVector.angle(Rotate.Y_AXIS));

        setHeight(point1.distance(point2));
    }

    public Connection getConnection() {
        return connection;
    }
}
