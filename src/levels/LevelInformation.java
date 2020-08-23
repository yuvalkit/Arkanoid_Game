package levels;
import other.Velocity;
import sprites.Block;
import sprites.Sprite;
import java.util.List;

/**
 * This is the interface for LevelInformation.
 */
public interface LevelInformation {
    /**
     * Returns the number of balls in this level.
     * @return number of balls
     */
    int numberOfBalls();
    /**
     * Returns the initial velocity of each ball.
     * @return a velocities list of all this level balls
     */
    List<Velocity> initialBallVelocities();
    /**
     * Returns the paddle speed.
     * @return paddle speed
     */
    int paddleSpeed();
    /**
     * Returns the paddle width.
     * @return paddle width
     */
    int paddleWidth();
    /**
     * Returns the name of this level.
     * @return name string
     */
    String levelName();
    /**
     * Returns a sprite with the background of this level.
     * @return a background sprite
     */
    Sprite getBackground();
    /**
     * Returns the Blocks that make up this level, each block contains
     * its size, color and location.
     * @return a list of blocks
     */
    List<Block> blocks();
    /**
     * Returns the number of levels that should be removed
     * before the level is considered to be "cleared".
     * @return the number of blocks to be removed
     */
    int numberOfBlocksToRemove();
    /**
     * Gets a string of a level file and scans it all for fields for the level.
     * Puts each field of the string in a matching field in the level.
     * @param level a string of a level file
     */
    void extractStringToLevel(String level);
    /**
     * Creates blocks from the blocks part of the level file.
     */
    void createBlocks();
}