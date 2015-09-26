package flexo.visualisation;

import flexo.model.Scene;
import flexo.model_converter.ModelConverter;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 2015-09-26.
 */
public class VisualisedObjectsCreator {
    public static List<javafx.scene.Node> createVisualisedObjects(Scene scene, int radius, int multiplicant){
        List<flexo.model.Node> list = ModelConverter.convert(scene);

        final PhongMaterial blackMaterial = new PhongMaterial();
        blackMaterial.setDiffuseColor(Color.BLACK);
        blackMaterial.setSpecularColor(Color.WHITE);

        List<javafx.scene.Node> visibleObjects = new LinkedList<>();
        for (flexo.model.Node node : list){
            Sphere sphere = new Sphere(radius);
            sphere.setMaterial(blackMaterial);
            sphere.setTranslateX(node.getX() * multiplicant);
            sphere.setTranslateY(node.getY() * multiplicant);
            sphere.setTranslateZ(node.getZ() * multiplicant);

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
