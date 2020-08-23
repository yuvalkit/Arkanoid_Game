package levels;
import specifications.BlocksDefinitionReader;
import specifications.BlocksFromSymbolsFactory;
import other.ColorsParser;
import other.Velocity;
import sprites.Block;
import sprites.Background;
import sprites.Sprite;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for GenericLevel that implements LevelInformation.
 */
public class GenericLevel implements LevelInformation {
    //fields
    private String levelStringFromFile;
    private String levelName;
    private List<Velocity> ballVelocities;
    private List<Block> blocks;
    private Background background;
    private int paddleSpeed;
    private int paddleWidth;
    private int numBlocksToRemove;
    private ColorsParser colorsParser;
    private int blocksStartX;
    private int blocksStartY;
    private int rowHeight;
    private String blocksDetails;
    private BlocksFromSymbolsFactory blocksFactory;
    /**
     * Constructor for a GenericLevel.
     * Set fields to a default value.
     */
    public GenericLevel() {
        this.levelName = null;
        this.ballVelocities = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.background = null;
        this.paddleSpeed = 0;
        this.paddleWidth = 0;
        this.numBlocksToRemove = 0;
        this.colorsParser = new ColorsParser();
        this.blocksStartX = 0;
        this.blocksStartY = 0;
        this.rowHeight = 0;
        this.blocksFactory = null;
    }
    @Override
    public int numberOfBalls() {
        return this.initialBallVelocities().size();
    }
    @Override
    public List<Velocity> initialBallVelocities() {
        return this.ballVelocities;
    }
    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }
    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }
    @Override
    public String levelName() {
        return this.levelName;
    }
    @Override
    public Sprite getBackground() {
        return this.background;
    }
    @Override
    public List<Block> blocks() {
        return this.blocks;
    }
    @Override
    public int numberOfBlocksToRemove() {
        return this.numBlocksToRemove;
    }
    @Override
    public void extractStringToLevel(String level) {
        int validityCounter = 0;
        boolean isBlocksPart = false;
        InputStream blockDefinitionsFile = null;
        this.blocksDetails = "";
        String[] lines = level.split("\n");
        String comparisonString;
        //go on all the lines of the file string and add each specific field to this generic level fields
        for (String s : lines) {
            comparisonString = "level_name:";
            if (s.startsWith(comparisonString)) {
                validityCounter++;
                s = s.replaceFirst(comparisonString, "");
                this.levelName = s;
            }
            comparisonString = "ball_velocities:";
            if (s.startsWith(comparisonString)) {
                validityCounter++;
                s = s.replaceAll(comparisonString, "");
                String[] velocityParams = s.split(" ");
                for (String vS : velocityParams) {
                    String[] angleAndSpeed = vS.split(",");
                    int angle = Integer.parseInt(angleAndSpeed[0]);
                    int speed = Integer.parseInt(angleAndSpeed[1]);
                    Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
                    this.ballVelocities.add(v);
                }
            }
            comparisonString = "background:";
            if (s.startsWith(comparisonString)) {
                validityCounter++;
                s = s.replaceFirst(comparisonString, "");
                s = s.replace("(", "");
                s = s.replace(")", "");
                comparisonString = "image";
                if (s.startsWith(comparisonString)) {
                    s = s.replaceFirst(comparisonString, "");
                    InputStream is = null;
                    BufferedImage image = null;
                    try {
                        is = ClassLoader.getSystemClassLoader().getResourceAsStream(s);
                        if (is != null) {
                            image = ImageIO.read(is);
                        }
                    } catch (IOException e) {
                        System.err.println("Failed opening image");
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                System.err.println("Failed closing image");
                            }
                        }
                    }
                    if (image != null) {
                        this.background = new Background(image);
                    }
                }
                comparisonString = "color";
                if (s.startsWith(comparisonString)) {
                    s = s.replaceAll(comparisonString, "");
                    Color color = this.colorsParser.colorFromString(s);
                    this.background = new Background(color);
                }
            }
            comparisonString = "paddle_speed:";
            if (s.startsWith(comparisonString)) {
                validityCounter++;
                s = s.replaceAll(comparisonString, "");
                this.paddleSpeed = Integer.parseInt(s);
            }
            comparisonString = "paddle_width:";
            if (s.startsWith(comparisonString)) {
                validityCounter++;
                s = s.replaceAll(comparisonString, "");
                this.paddleWidth = Integer.parseInt(s);
            }
            comparisonString = "block_definitions:";
            if (s.startsWith(comparisonString)) {
                validityCounter++;
                s = s.replaceFirst(comparisonString, "");
                blockDefinitionsFile = ClassLoader.getSystemClassLoader().getResourceAsStream(s);
            }
            comparisonString = "blocks_start_x:";
            if (s.startsWith(comparisonString)) {
                validityCounter++;
                s = s.replaceAll(comparisonString, "");
                this.blocksStartX = Integer.parseInt(s);
            }
            comparisonString = "blocks_start_y:";
            if (s.startsWith(comparisonString)) {
                validityCounter++;
                s = s.replaceAll(comparisonString, "");
                this.blocksStartY = Integer.parseInt(s);
            }
            comparisonString = "row_height:";
            if (s.startsWith(comparisonString)) {
                validityCounter++;
                s = s.replaceAll(comparisonString, "");
                this.rowHeight = Integer.parseInt(s);
            }
            comparisonString = "num_blocks:";
            if (s.startsWith(comparisonString)) {
                validityCounter++;
                s = s.replaceAll(comparisonString, "");
                this.numBlocksToRemove = Integer.parseInt(s);
            }
            if (s.equals("START_BLOCKS")) {
                isBlocksPart = true;
                continue;
            }
            if (s.equals("END_BLOCKS")) {
                isBlocksPart = false;
            }
            if (isBlocksPart) {
                this.blocksDetails = this.blocksDetails.concat(s).concat("\n");
            }
        }
        //check if all the fields in the file are valid
        if (validityCounter != 10) {
            throw new RuntimeException("Missing fields in the levels file");
        }
        if (blockDefinitionsFile != null) {
            Reader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(blockDefinitionsFile));
                this.blocksFactory = BlocksDefinitionReader.fromReader(reader);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                        blockDefinitionsFile.close();
                    } catch (IOException e) {
                        System.err.println("Error with closing file");
                    }
                }
            }
        } else {
            throw new RuntimeException("Missing block definitions file in the levels file");
        }
    }
    @Override
    public void createBlocks() {
        //first clears the old blocks list
        this.blocks.clear();
        int y = this.blocksStartY;
        String[] lines = this.blocksDetails.split("\n");
        //go on all the lines of the blocks part in the file
        for (String s : lines) {
            int x = this.blocksStartX;
            //make an array from the chars of the line
            char[] chars = s.toCharArray();
            //go on all chars
            for (char c : chars) {
                String sign = String.valueOf(c);
                //if its a block symbol, creates a block
                if (this.blocksFactory.isBlockSymbol(sign)) {
                    Block block = this.blocksFactory.getBlock(sign, x, y);
                    this.blocks.add(block);
                    x += block.getWidth();
                }
                //if its a space symbol, move that space
                if (this.blocksFactory.isSpaceSymbol(sign)) {
                    x += this.blocksFactory.getSpaceWidth(sign);
                }
            }
            //goes down by 1 row
            y += this.rowHeight;
        }
    }
}