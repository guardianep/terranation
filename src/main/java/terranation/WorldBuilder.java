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

    private static final float FLOOR_SIZE = 200f;   // the floor is a 200 by 200 square
    private static final float GRID_REPEATS = 25f;  // the grid texture repeats 25 times across it
    private static final float TILE = FLOOR_SIZE / GRID_REPEATS; // size of one grid square in game units

    private WorldBuilder() {
        // private constructor so nobody can accidentally create one
    }

    // Builds the whole world. Called once from Main.simpleInitApp().
    // Returns the floor so Main can keep it and move it under the car every frame.
    public static Geometry build(AssetManager assetManager, Node rootNode, ViewPort viewPort) {
        viewPort.setBackgroundColor(ColorRGBA.Blue);
        addSun(rootNode);
        return addFloor(assetManager, rootNode);
    }

    // Makes the map "infinite": slides the floor so it is always centered under the car.
    // It only moves in whole grid squares, so the lines look like they stay put while you drive.
    public static void followFloor(Geometry floor, Vector3f carPosition) {
        float x = Math.round(carPosition.x / TILE) * TILE;
        float z = Math.round(carPosition.z / TILE) * TILE;
        floor.setLocalTranslation(x - FLOOR_SIZE / 2f, -0.05f, z + FLOOR_SIZE / 2f);
    }

    private static void addSun(Node rootNode) {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.5f, -1f, -0.5f).normalizeLocal());
        rootNode.addLight(sun);
    }

    // A big flat floor with a repeating grid texture so you can see the car moving.
    private static Geometry addFloor(AssetManager assetManager, Node rootNode) {
        Texture gridTexture = assetManager.loadTexture("Textures/grid.jpg");
        gridTexture.setWrap(Texture.WrapMode.Repeat);

        Quad floorMesh = new Quad(FLOOR_SIZE, FLOOR_SIZE);
        Geometry floor = new Geometry("Floor", floorMesh);
        floor.rotate(-FastMath.HALF_PI, 0, 0);           // lay the quad flat, facing up
        floor.setLocalTranslation(-FLOOR_SIZE / 2f, -0.05f, FLOOR_SIZE / 2f);  // after rotating it covers x 0..200 and z -200..0, so shift it to center on the origin
        floorMesh.scaleTextureCoordinates(new Vector2f(GRID_REPEATS, GRID_REPEATS)); // repeat the grid in each direction

        Material floorMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        floorMat.setTexture("ColorMap", gridTexture);
        floor.setMaterial(floorMat);
        rootNode.attachChild(floor);
        return floor;
    }
}
