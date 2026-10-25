package terranation;

public class CarAttributes {
    private String modelName;
    private float maxSpeed;
    private float maxReverseSpeed;
    private float acceleration;
    private float deceleration;
    private float maxRpm;
    private float idleRpm;
    private int numGears;
    private float turnSpeed;

    // Constructor to build custom attribute profiles
    public CarAttributes(String modelName, float maxSpeed, float maxReverseSpeed, float acceleration, float deceleration, float turnSpeed, float maxRpm, float idleRpm, int numGears) {
        this.modelName = modelName;
        this.maxSpeed = maxSpeed;
        this.maxReverseSpeed = maxReverseSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.maxRpm = maxRpm;
        this.idleRpm = idleRpm;
        this.numGears = numGears;
        this.turnSpeed = turnSpeed; // Initialize turnSpeed to a default value (e.g., 1.0f)
    }

    // --- PRESET 1: Standard Sedan ---
    public static CarAttributes defaultCar() {
        return new CarAttributes("Sedan", 10f, -10f, 15f, 10f, 1.6f, 6000f, 800f, 3);
    }

    // --- PRESET 2: Fast Sports Car ---
    public static CarAttributes sportsCar() {
        return new CarAttributes("Sports Car", 25f, -12f, 25f, 15f, 2.0f, 8500f, 1100f, 5);
    }

    // --- PRESET 3: Heavy Truck ---
    public static CarAttributes heavyTruck() {
        return new CarAttributes("Truck", 7f, -5f, 8f, 14f, 1.0f, 4000f, 600f, 2);
    }

    // Getters
    public String getModelName() { return modelName; }
    public float getMaxSpeed() { return maxSpeed; }
    public float getMaxReverseSpeed() { return maxReverseSpeed; }
    public float getAcceleration() { return acceleration; }
    public float getDeceleration() { return deceleration; }
    public float getMaxRpm() { return maxRpm; }
    public float getIdleRpm() { return idleRpm; }
    public int getNumGears() { return numGears; }
    public float getTurnSpeed() { return turnSpeed; }
}