package listeners;
import game.GameLevel;
import sprites.Ball;
import sprites.Block;
import other.Counter;

/**
 * This is the class for BallRemover that implements HitListener.
 * This remover is in charge of removing balls from the gameLevel, as well as keeping count
 * of the number of balls that remain.
 */
public class BallRemover implements HitListener {
    //fields of gameLevel and counter
    private GameLevel gameLevel;
    private Counter remainingBalls;
    /**
     * This is the constructor for a new BallRemover.
     * @param gameLevel the gameLevel this remover is in
     * @param remainingBalls the balls counter
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    /**
     * The beingHit block will be the death-region block.
     * Balls that hit that block will be removed from the gameLevel.
     * @param beingHit the block that is being hit
     * @param hitter the ball that hit the beingHit block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        //remove the ball from the gameLevel
        hitter.removeFromGame(this.gameLevel);
        //decrease the number of balls by 1
        this.remainingBalls.decrease(1);
    }
}