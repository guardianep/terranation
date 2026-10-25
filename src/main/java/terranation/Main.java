package terranation;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

// Main only starts the game and runs the game loop. Everything else has its own class:
//   WorldBuilder    sky, sun and the grid floor
//   CarBuilder      builds the car and puts it in the world
//   PlayerInput     keyboard controls
//   CameraSettings  the chase camera
//   Car             speed, steering and movement
// Main "extends" SimpleApplication, which means it inherits the engine's window and game loop.
public class Main extends SimpleApplication {
    private Car car;            // the car the player controls
    private PlayerInput input;  // what keys the player is pressing

    // main() is where Java starts running the program.
    public static void main(String[] args) {
        Main game = new Main();

        // AppSettings holds window options such as title and size.
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Terranation");
        settings.setWidth(1280);
        settings.setHeight(720);

        game.setSettings(settings);
        game.setShowSettings(false);    // skip the engine's startup options dialog
        game.setDisplayFps(false);      // hide the frame counter
        game.setDisplayStatView(false); // hide the stats overlay

        game.start(); // opens the window and starts the game loop
    }

    // Called once when the window opens. Builds the game by calling the other classes.
    @Override
    public void simpleInitApp() {
        WorldBuilder.build(assetManager, rootNode, viewPort);
        car = CarBuilder.build(assetManager, rootNode);
        input = new PlayerInput(inputManager);
        CameraSettings.attach(flyCam, cam, inputManager, car.getBody());
    }

    // Called every frame. tpf is the time since the last frame.
    @Override
    public void simpleUpdate(float tpf) {
        car.handleInput(input.isAccelerating(), input.isReversing(), tpf);
        car.steer(input.getSteering(), tpf);
        car.update(tpf);
        input.resetSteering();
    }
}
