package listeners;
import sprites.Ball;
import sprites.Block;
import other.Counter;

/**
 * This is the interface for ScoreTrackingListener that implements HitListener.
 */
public class ScoreTrackingListener implements HitListener {
    //a score counter
    private Counter currentScore;
    /**
     * The constructor for this score tracking listener.
     * @param scoreCounter a score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        //increase the score by 5 if a block was hit
        this.currentScore.increase(5);
        //increase the score by 10 if a block was destroyed (he has 0 hit points)
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(10);
            //if the block is destroyed, also removes this listener from him
            beingHit.removeHitListener(this);
        }
    }
}