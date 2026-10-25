package terranation;
import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;

// Main is the starting point of the game. It "extends" SimpleApplication,
// which means it inherits the engine's window, game loop and camera.
// The methods below are "overrides": they replace engine methods with our own code.
public class Main extends SimpleApplication {
    private Car car; // the car object that will be controlled by the player
    private boolean isAccelerating = false;
    private boolean isBraking = false;
    private float steeringInput = 0f;
    private boolean isReversing = false;

    // main() is where Java starts running the program.
    public static void main(String[] args) {
        Main game = new Main();

        // AppSettings holds window options such as title and size.
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Terranation");
        settings.setWidth(1280);
        settings.setHeight(720);

        game.setSettings(settings);
        game.setShowSettings(false); // skip the engine's startup options dialog
        game.setDisplayFps(false);   // hide the frame counter
        game.setDisplayStatView(false); // hide the stats overlay

        game.start(); // opens the window and starts the game loop
    }

    // Called once when the window opens. Later this will build the game world.
    @Override
    public void simpleInitApp() {
            viewPort.setBackgroundColor(ColorRGBA.Blue); 

            DirectionalLight sun = new DirectionalLight();
            sun.setDirection(new Vector3f(-0.5f, -1f, -0.5f).normalizeLocal());
            rootNode.addLight(sun);
            

            //the car 
            Box carMesh = new Box(1f, 0.5f, 2f);
            Geometry carGeo = new Geometry("Car", carMesh);
            Material carMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            carMat.setColor("Color", ColorRGBA.Red);
            carGeo.setMaterial(carMat);
            rootNode.attachChild(carGeo);
            
            car = new Car(carGeo);
            carGeo.setLocalTranslation(0, 0.5f, 0); // position the car above the floor

            //Keyboard mapping
            inputManager.addMapping("Accelerate", new com.jme3.input.controls.KeyTrigger(com.jme3.input.KeyInput.KEY_W));
            inputManager.addMapping("Brake", new com.jme3.input.controls.KeyTrigger(com.jme3.input.KeyInput.KEY_S));
            inputManager.addMapping("TurnLeft", new com.jme3.input.controls.KeyTrigger(com.jme3.input.KeyInput.KEY_A));
            inputManager.addMapping("TurnRight", new com.jme3.input.controls.KeyTrigger(com.jme3.input.KeyInput.KEY_D));

            inputManager.addListener((com.jme3.input.controls.ActionListener) (name, isPressed, tpf) -> {
                if (name.equals("Accelerate")) isAccelerating = isPressed;
                if (name.equals("Brake")) isReversing = isPressed;
            }, "Accelerate", "Brake");

            inputManager.addListener((com.jme3.input.controls.AnalogListener) (name, value, tpf) -> {
                if (name.equals("TurnLeft")) steeringInput = 1f;
                else if (name.equals("TurnRight")) steeringInput = -1f;
            }, "TurnLeft", "TurnRight");


            //camera settings
            flyCam.setEnabled(false);

            ChaseCamera chaseCam = new ChaseCamera(cam, carGeo, inputManager);

            chaseCam.setSmoothMotion(true);
            chaseCam.setTrailingEnabled(true);
            chaseCam.setDefaultDistance(10f);
            chaseCam.setMaxDistance(15f);
            chaseCam.setMinDistance(5f);
            chaseCam.setMaxVerticalRotation((float) Math.PI /2.5f);

            //Floor with a repeating grid texture so you can see the car moving
            Texture gridTexture = assetManager.loadTexture("Textures/grid.jpg");
            gridTexture.setWrap(Texture.WrapMode.Repeat);

            Quad floorMesh = new Quad(200f, 200f);
            Geometry floor = new Geometry("Floor", floorMesh);
            floor.rotate(-FastMath.HALF_PI, 0, 0);          // lay the quad flat, facing up
            floor.setLocalTranslation(-100f, -0.05f, 100f); // after rotating it covers x 0..200 and z -200..0, so shift it to center on the origin
            floorMesh.scaleTextureCoordinates(new Vector2f(25f, 25f)); // repeat the grid 25 times in each direction

            Material floorMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            floorMat.setTexture("ColorMap", gridTexture);
            floor.setMaterial(floorMat);
            rootNode.attachChild(floor);
    }

    // Called every frame. tpf is the time since the last frame.
    @Override
    public void simpleUpdate(float tpf) {
        car.handleInput(isAccelerating, isReversing, tpf);
        car.steer(steeringInput, tpf);
        car.update(tpf);

        steeringInput = 0f;
    }
}
