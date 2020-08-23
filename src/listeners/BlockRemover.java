package listeners;
import game.GameLevel;
import sprites.Ball;
import sprites.Block;
import other.Counter;

/**
 * This is the class for BlockRemover that implements HitListener.
 * This remover is in charge of removing blocks from the gameLevel, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    //fields of gameLevel and counter
    private GameLevel gameLevel;
    private Counter remainingBlocks;
    /**
     * This is the constructor for a new BlockRemover.
     * @param gameLevel the gameLevel this remover is in
     * @param removedBlocks the blocks counter
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }
    /**
     * Blocks that are hit and reach 0 hit-points are removed from the gameLevel.
     * @param beingHit the block that is being hit
     * @param hitter the ball that hit the beingHit block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        //if the block has 0 hit points
        if (beingHit.getHitPoints() == 0) {
            //remove the block from the gameLevel
            beingHit.removeFromGame(this.gameLevel);
            //remove this listener from that block
            beingHit.removeHitListener(this);
            //the number of blocks decrease by 1
            this.remainingBlocks.decrease(1);
        }
    }
}