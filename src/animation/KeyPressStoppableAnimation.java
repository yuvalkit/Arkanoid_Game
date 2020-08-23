package animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * This is the class for KeyPressStoppableAnimation that implements Animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    //an inner animation field
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;
    /**
     * The constructor for a KeyPressStoppableAnimation.
     * @param sensor a keyboard sensor
     * @param key a given string key to check if was pressed
     * @param animation a given animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        //the stop flag starts as false
        this.stop = false;
        //starts as true
        this.isAlreadyPressed = true;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        //does the inner one frame of animation
        this.animation.doOneFrame(d);
        //if the given key was pressed
        if (this.sensor.isPressed(this.key)) {
            //if the key was pressed before the animation started, we ignore that key press
            if (this.isAlreadyPressed) {
                return;
            }
            //else, the animation should stop
            this.stop = true;
        }
        //if the given key is not being pressed
        if (!this.sensor.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }
    }
    @Override
    public boolean shouldStop() {
        //returns the stop flag
        return this.stop;
    }
}