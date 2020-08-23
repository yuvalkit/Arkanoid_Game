package sprites;
import biuoop.DrawSurface;

/**
 * This is the interface for Sprite.
 */
public interface Sprite {
    /**
     * Draw the sprite to the screen.
     * @param d the given DrawSurface
     */
    void drawOn(DrawSurface d);
    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();
}