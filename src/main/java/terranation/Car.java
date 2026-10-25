package terranation;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;


public class Car {
    private float speed = 0f;
    private float maxSpeed = 10f;
    private float maxReverseSpeed = -10f;
    private float acceleration = 15f;
    private float deceleration = 10f;
    private float heading = 0f;  // the angle of rotation calculated in radians
    private Geometry body; // the geometry of the car
    private float turnSpeed = 1.6f;

    // Builds a car with the standard sedan numbers.
    public Car(Geometry body) {
        this(body, CarAttributes.defaultCar());
    }

    // Builds a car whose speed, acceleration and braking come from a preset (see CarAttributes).
    public Car(Geometry body, CarAttributes specs) {
        this.body = body;
        this.maxSpeed = specs.getMaxSpeed();
        this.maxReverseSpeed = specs.getMaxReverseSpeed();
        this.acceleration = specs.getAcceleration();
        this.deceleration = specs.getDeceleration();
        this.turnSpeed = specs.getTurnSpeed();
    }

    public void handleInput(boolean pressingForward, boolean pressingBackward, float tpf) {
         if (pressingForward) {
            if (speed <0) {
            speed += acceleration * 2 * tpf;
            if (speed > 0) speed =0;
         } else {
            speed += acceleration * tpf;
            if (speed > maxSpeed) speed = maxSpeed;
         }
        } else if (pressingBackward) {
            if (speed > 0) {
                // so that when moving 
                speed -= deceleration * 2 * tpf;
                if (speed < 0 ) speed =0 ; 
            } else {
                 speed -= deceleration * tpf;
                 if (speed < maxReverseSpeed) speed = maxReverseSpeed;
            }

        }else {
            
            if (speed > 0) {
                speed -= deceleration * tpf;
                if (speed < 0) speed = 0;
            } else if (speed < 0) {
                speed += deceleration * tpf;
                if (speed > 0) speed = 0;
            }
        }
    }
    

    public void steer(float amount, float tpf) {
        if (Math.abs(speed) > 0.1f) {

            float steeringDir = (speed > 0) ? 1f : -1f;
            heading += amount * tpf * turnSpeed * steeringDir;
            body.setLocalRotation(new com.jme3.math.Quaternion().fromAngleAxis(heading, Vector3f.UNIT_Y));
        }
         
            
    }
    public void update (float tpf) {
        //move along on vectors based on heading directions
        Vector3f direction = new Vector3f((float) Math.sin(heading), 0, (float) Math.cos(heading));
         body.move(direction.mult(speed * tpf));
    }

    // Lets other classes (like the camera) know which 3D shape the car is.
    public Geometry getBody() {
        return body;
    }

    public Vector3f getPosition() {
        return body.getLocalTranslation();

    }
}
