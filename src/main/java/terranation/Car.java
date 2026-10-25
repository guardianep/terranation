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
    private float maxRpm;
    private float idleRpm;
    private int numGears;
    private float powerHold= 1.5f;

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
        this.maxRpm = specs.getMaxRpm();
        this.idleRpm = specs.getIdleRpm();
        this.numGears = specs.getNumGears();
        this.powerHold = specs.getPowerHold();
    }

    public void handleInput(boolean pressingForward, boolean pressingBackward, float tpf) {
         if (pressingForward) {
            if (speed <0) {
            speed += acceleration * 2 * tpf;
            if (speed > 0) speed =0;
         } else {
            speed += getEngineAcceleration() * tpf;;
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

    //decides ghow hard the engine can push and accelerate depending on the gear it is in

    private float getEngineAcceleration() {
        int gear = Math.max(getGear(), 1); // ensure gear is at least 1
        float gearProgress = (numGears <= 1) ? 0f : (gear - 1f) / (numGears - 1f);
        float gearFactor = 1f - 0.6f * gearProgress;
        float topSpeedFactor = 1f - (float) Math.pow(speed / maxSpeed, powerHold);
        return acceleration * gearFactor * topSpeedFactor;
    }


    public int getGear() {
        if (speed < 0.1f) return 0; //this means reverse which the hud SHOULD display as R
        int gear = 1 + (int) (speed / maxSpeed * numGears);
        return Math.min(gear, numGears); // ensure gear does not exceed numGears
    }



    public float getRpm() {
        float bandSize = maxSpeed / numGears;
        float speedInGear = Math.abs(speed) - (Math.max(getGear(), 1) -1) * bandSize;
        float fraction = Math.min(speedInGear / bandSize, 1f);
        return idleRpm + fraction * (maxRpm - idleRpm);
    }

    // Lets other classes (like the camera) know which 3D shape the car is.
    public Geometry getBody() {
        return body;
    }
    public float geteSpeed() {
        return speed;
    }

    public Vector3f getPosition() {
        return body.getLocalTranslation();

    }
}
