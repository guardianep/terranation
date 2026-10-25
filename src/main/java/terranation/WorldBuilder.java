package terranation;

import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;

// WorldBuilder sets up everything the car drives in: the sky color, the sun and the floor.
// It is a "utility class": it only has static methods, so you never create a WorldBuilder object.
public class WorldBuilder {

    private WorldBuilder() {
        // private constructor so nobody can accidentally create one
    }

    // Builds the whole world. Called once from Main.simpleInitApp().
    public static void build(AssetManager assetManager, Node rootNode, ViewPort viewPort) {
        viewPort.setBackgroundColor(ColorRGBA.Blue);
        addSun(rootNode);
        addFloor(assetManager, rootNode);
    }

    private static void addSun(Node rootNode) {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.5f, -1f, -0.5f).normalizeLocal());
        rootNode.addLight(sun);
    }

    // A big flat floor with a repeating grid texture so you can see the car moving.
    private static void addFloor(AssetManager assetManager, Node rootNode) {
        Texture gridTexture = assetManager.loadTexture("Textures/grid.jpg");
        gridTexture.setWrap(Texture.WrapMode.Repeat);

        Quad floorMesh = new Quad(200f, 200f);
        Geometry floor = new Geometry("Floor", floorMesh);
        floor.rotate(-FastMath.HALF_PI, 0, 0);           // lay the quad flat, facing up
        floor.setLocalTranslation(-100f, -0.05f, 100f);  // after rotating it covers x 0..200 and z -200..0, so shift it to center on the origin
        floorMesh.scaleTextureCoordinates(new Vector2f(25f, 25f)); // repeat the grid 25 times in each direction

        Material floorMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        floorMat.setTexture("ColorMap", gridTexture);
        floor.setMaterial(floorMat);
        rootNode.attachChild(floor);
    }
}
