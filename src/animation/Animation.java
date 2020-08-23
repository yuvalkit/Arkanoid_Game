package animation;
import biuoop.DrawSurface;

/**
 * This is the interface for Animation.
 */
public interface Animation {
    /**
     * Does the logic of one frame of the animation.
     * @param d a drawsurface to draw on
     */
    void doOneFrame(DrawSurface d);
    /**
     * Returns true if the animation should stop, false otherwise.
     * @return true if should stop, false otherwise
     */
    boolean shouldStop();
}