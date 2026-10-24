package terranation;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

// Main is the starting point of the game. It "extends" SimpleApplication,
// which means it inherits the engine's window, game loop and camera.
// The methods below are "overrides": they replace engine methods with our own code.
public class Main extends SimpleApplication {

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
        // Empty for now.
    }
}
