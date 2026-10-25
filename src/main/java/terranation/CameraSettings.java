package terranation;

import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

// CameraSettings sets up the camera that follows the car.
public class CameraSettings {

    private CameraSettings() {
    }

    public static ChaseCamera attach(FlyByCamera flyCam, Camera cam, InputManager inputManager, Spatial target) {
        flyCam.setEnabled(false); // turn off the free flying camera

        ChaseCamera chaseCam = new ChaseCamera(cam, target, inputManager);
        chaseCam.setSmoothMotion(true);
        chaseCam.setTrailingEnabled(true);
        chaseCam.setDefaultDistance(10f);
        chaseCam.setMaxDistance(15f);
        chaseCam.setMinDistance(5f);
        chaseCam.setMaxVerticalRotation((float) Math.PI / 2.5f);
        return chaseCam;
    }
}
