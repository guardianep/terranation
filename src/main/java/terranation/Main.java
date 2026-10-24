package terranation;
import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

// Main is the starting point of the game. It "extends" SimpleApplication,
// which means it inherits the engine's window, game loop and camera.
// The methods below are "overrides": they replace engine methods with our own code.
public class Main extends SimpleApplication {
    private Car car; // the car object that will be controlled by the player
    private boolean isAccelerating = false;
    private boolean isBraking = false;
    private float steeringInput = 0f;

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
            // the FLOOR
            Box floorMesh = new Box(100,0.1f,100f);
            Geometry floor = new Geometry("Floor",floorMesh); 
            Material floorMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            floorMat.setColor("Color", ColorRGBA.DarkGray);
            floor.setMaterial(floorMat);
            floor.move(0,-0.1f,0);
            rootNode.attachChild(floor);

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
                if (name.equals("Brake")) isBraking = isPressed;
            }, "Accelerate", "Brake");

            inputManager.addListener((com.jme3.input.controls.AnalogListener) (name, value, tpf) -> {
                if (name.equals("TurnLeft")) steeringInput = 1.5f;
                else if (name.equals("TurnRight")) steeringInput = -1.5f;
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



        }


            //updater for the car to be abe able to do stuff
            @Override 
            public void simpleUpdate(float tpf) {
                car.accelerate(tpf, isAccelerating);
                car.brake(tpf, isBraking);
                car.steer(tpf, steeringInput);
                car.update(tpf);

                steeringInput = 0f;
            
                //Camera follows the car
                Vector3f carPos = car.getPosition();
                Vector3f camOffset = new Vector3f(0f, 4f, -6f); //behind and above the car
                cam.setLocation(carPos.add(camOffset));
                cam.lookAt(carPos, Vector3f.UNIT_Y);
            }
}
