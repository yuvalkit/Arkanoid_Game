package animation;
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

/**
 * This is the class for AnimationRunner.
 */
public class AnimationRunner {
    //fields
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;
    /**
     * The constructor for a AnimationRunner.
     * @param gui the gui of the game
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        //creates a new sleeper
        this.sleeper = new biuoop.Sleeper();
        //sets the frames per second of the game to 60
        this.framesPerSecond = 60;
    }
    /**
     * Does the animation loop of the given animation, with calculated timing.
     * @param animation a given animation
     */
    public void run(Animation animation) {
        //animation frames details
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        //the while loop will end when the animation should stop
        while (true) {
            //get the start time
            long startTime = System.currentTimeMillis();
            //the draw surface
            DrawSurface d = this.gui.getDrawSurface();
            //do one frame of the animation
            animation.doOneFrame(d);
            //if the animation should stop, stops the run
            if (animation.shouldStop()) {
                return;
            }
            //show on gui
            this.gui.show(d);
            //timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
    /**
     * Returns the gui.
     * @return this gui
     */
    public GUI getGui() {
        return this.gui;
    }
}