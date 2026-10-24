package terranation;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;


public class Car {
    private float speed = 0f;
    private float maxSpeed = 10f;
    private float acceleration = 15f;
    private float deceleration = 10f;
    private float heading = 0f;  // the angle of rotation calculated in radiants
    private Geometry body; // the geometry of the car

    public Car(Geometry body){
        this.body = body;
    }

    public void accelerate(float tpf, boolean forward) {
        if (forward) {
            speed += acceleration * tpf;
            if (speed > maxSpeed) speed = maxSpeed;
        } else {
            if (speed > 0) {
            speed -= deceleration * tpf;
            if (speed < 0) speed = 0;
            }
        }
    }
    public void brake(float tpf, boolean pressing) {
        if (pressing){
            speed -= (deceleration * 2) * tpf;
            if (speed < 0) speed = 0;
        }
    }

    public void steer(float tpf, float amount) {
        if (speed != 0) {
                heading += amount * tpf * (speed / maxSpeed);
                body.setLocalRotation(new Quaternion().fromAngleAxis(heading, Vector3f.UNIT_Y));
        } {
            
        }
    }
    public void update (float tpf) {
        //move along on vectors based on heading directions
        Vector3f direction = new Vector3f((float) Math.sin(heading), 0, (float) Math.cos(heading));
         body.move(direction.mult(speed * tpf));
    }

    public Vector3f getPosition() {
        return body.getLocalTranslation();

    }
}
