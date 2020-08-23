package specifications;
import sprites.Block;
import java.awt.Image;
import java.awt.Color;
import java.util.Map;

/**
 * This is the class for GenericBlockCreator that implements BlockCreator.
 */
public class GenericBlockCreator implements BlockCreator {
    //block fields
    private int height;
    private int width;
    private int hitPoints;
    private Map<Integer, Color> fillColors;
    private Map<Integer, Image> fillImages;
    private Color stroke;
    /**
     * Constructor for a GenericBlockCreator.
     * @param height a height of a block
     * @param width a width of a block
     * @param hitPoints the hit points of a block
     * @param fillColors the colors to fill of a block
     * @param fillImages the images to fill of a block
     * @param stroke the stroke of a block
     */
    public GenericBlockCreator(int height, int width, int hitPoints, Map<Integer, Color> fillColors,
                               Map<Integer, Image> fillImages, Color stroke) {
        this.height = height;
        this.width = width;
        this.hitPoints = hitPoints;
        this.fillColors = fillColors;
        this.fillImages = fillImages;
        this.stroke = stroke;
    }
    @Override
    public Block create(int xpos, int ypos) {
        Block block = new Block(xpos, ypos, this.width, this.height, this.hitPoints, this.stroke);
        block.setBlockFills(this.fillColors, this.fillImages);
        return block;
    }
}