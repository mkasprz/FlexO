package flexo.visualisation;

import flexo.gui.PropertiesController;
import flexo.model.Setup;
import flexo.modelconverter.ModelConverter;
import flexo.model.TypicalNode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 2015-09-26.
 */
public class VisualisedObjectsCreator {
    public static List<javafx.scene.Node> createVisualisedObjects(Setup scene, int radius, int multiplicant, PropertiesController propertiesController){
        List<TypicalNode> list = ModelConverter.convert(scene);

        final PhongMaterial blackMaterial = new PhongMaterial();
        blackMaterial.setDiffuseColor(Color.BLACK);
        blackMaterial.setSpecularColor(Color.WHITE);

        List<javafx.scene.Node> visibleObjects = new LinkedList<>();
        for (TypicalNode typicalNode : list){
            Sphere sphere = new Sphere(radius);
            sphere.setMaterial(blackMaterial);
            sphere.setTranslateX(typicalNode.getX() * multiplicant);
            sphere.setTranslateY(typicalNode.getY() * multiplicant);
            sphere.setTranslateZ(typicalNode.getZ() * multiplicant);

            sphere.setOnMouseClicked(event -> {
                sphere.setMaterial(new PhongMaterial(Color.RED));
                propertiesController.setSelectedNode(typicalNode);
                propertiesController.setId(typicalNode.getId());
                propertiesController.setX(typicalNode.getX());
                propertiesController.setY(typicalNode.getY());
                propertiesController.setZ(typicalNode.getZ());
                propertiesController.setVisible(true);
            });

            visibleObjects.add(sphere);
        }
        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.GREY);
        greyMaterial.setSpecularColor(Color.WHITE);

        Sphere sphere = new Sphere(radius);
        sphere.setMaterial(greyMaterial);
        sphere.setTranslateX(scene.getCentralNode().getX() * multiplicant);
        sphere.setTranslateY(scene.getCentralNode().getY() * multiplicant);
        sphere.setTranslateZ(scene.getCentralNode().getZ() * multiplicant);

        visibleObjects.add(sphere);
        return visibleObjects;
    }
}
