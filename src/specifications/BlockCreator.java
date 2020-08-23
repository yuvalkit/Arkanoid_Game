package specifications;
import sprites.Block;

/**
 * This is the interface for BlockCreator.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     * @param xpos the x position
     * @param ypos the y position
     * @return a block that was created in the specified x and y positions
     */
    Block create(int xpos, int ypos);
}