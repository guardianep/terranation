package terranation;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;

// PlayerInput listens to the keyboard and remembers what the player is pressing.
// Main asks it every frame with the getters below.
// This one is a normal class (not static) because it has to remember state between frames.
public class PlayerInput {
    private boolean accelerating = false; // W is held
    private boolean reversing = false;    // S is held
    private float steering = 0f;          // 1 = left, -1 = right, 0 = straight

    public PlayerInput(InputManager inputManager) {
        // Map each key to a name.
        inputManager.addMapping("Accelerate", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Reverse", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("TurnLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("TurnRight", new KeyTrigger(KeyInput.KEY_D));

        // ActionListener fires when a key is pressed or released.
        ActionListener actionListener = (name, isPressed, tpf) -> {
            if (name.equals("Accelerate")) accelerating = isPressed;
            if (name.equals("Reverse")) reversing = isPressed;
        };

        // AnalogListener fires every frame while a key is held down.
        AnalogListener analogListener = (name, value, tpf) -> {
            if (name.equals("TurnLeft")) steering = 1f;
            else if (name.equals("TurnRight")) steering = -1f;
        };

        inputManager.addListener(actionListener, "Accelerate", "Reverse");
        inputManager.addListener(analogListener, "TurnLeft", "TurnRight");
    }

    public boolean isAccelerating() {
        return accelerating;
    }

    public boolean isReversing() {
        return reversing;
    }

    public float getSteering() {
        return steering;
    }

    // The analog listener only sets steering while a key is held, so Main resets it every frame.
    public void resetSteering() {
        steering = 0f;
    }
}
