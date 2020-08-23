package listeners;
import sprites.Ball;
import sprites.Block;

/**
 * This is the interface for HitListener.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit the block that is being hit
     * @param hitter the ball that hit the beingHit block
     */
    void hitEvent(Block beingHit, Ball hitter);
}