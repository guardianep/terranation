package terranation;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

// CarBuilder creates the car's 3D shape, adds it to the world and hands back a Car object.
// Later, if you swap the red box for a real model, this is the only file that changes.
public class CarBuilder {

    private CarBuilder() {
    }

    public static Car build(AssetManager assetManager, Node rootNode) {
        Box carMesh = new Box(1f, 0.5f, 2f);
        Geometry carGeo = new Geometry("Car", carMesh);

        Material carMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        carMat.setColor("Color", ColorRGBA.Red);
        carGeo.setMaterial(carMat);

        carGeo.setLocalTranslation(0, 0.5f, 0); // sit on top of the floor
        rootNode.attachChild(carGeo);

        // Pass your choice of car preset here!
        CarAttributes specs = CarAttributes.sportsCar();
        return new Car(carGeo, specs);
    }
}
