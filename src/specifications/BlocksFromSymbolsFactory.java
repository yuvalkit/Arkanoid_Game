package specifications;
import sprites.Block;
import java.util.Map;

/**
 * This is the class for BlocksFromSymbolsFactory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, BlockCreator> blockCreators;
    private Map<String, Integer> spacerWidths;
    /**
     * Constructor for a BlocksFromSymbolsFactory.
     * @param blockCreators a block creators map
     * @param spacerWidths a spacer widths map
     */
    public BlocksFromSymbolsFactory(Map<String, BlockCreator> blockCreators, Map<String, Integer> spacerWidths) {
        this.blockCreators = blockCreators;
        this.spacerWidths = spacerWidths;
    }
    /**
     * Returns true if 's' is a valid space symbol.
     * @param s a symbol
     * @return true if s is a valid space symbol, false otherwise
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }
    /**
     * Returns true if 's' is a valid block symbol.
     * @param s a symbol
     * @return true if s is a valid block symbol, false otherwise
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }
    /**
     * Return a block according to the definitions associated with the symbol s.
     * The block will be located at position (xpos, ypos).
     * @param s a symbol
     * @param xpos the x position
     * @param ypos the y position
     * @return a block from the correct definitions
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }
    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     * @param s a symbol
     * @return width in pixels of the given space symbol
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}