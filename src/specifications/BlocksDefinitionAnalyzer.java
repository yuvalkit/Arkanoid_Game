package specifications;
import other.ColorsParser;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the class for BlocksDefinitionAnalyzer.
 */
public class BlocksDefinitionAnalyzer {
    //fields
    private String blocksFileToAnalyze;
    private boolean defaultSymbolFlag;
    private boolean defaultHeightFlag;
    private boolean defaultWidthFlag;
    private boolean defaultHitPointsFlag;
    private boolean defaultFillFlag;
    private boolean defaultStrokeFlag;
    private String defaultSymbol;
    private int defaultHeight;
    private int defaultWidth;
    private int defaultHitPoints;
    private Map<Integer, Color> defaultFillColors;
    private Map<Integer, Image> defaultFillImages;
    private Color defaultStroke;
    private ColorsParser colorsParser;
    private Map<String, BlockCreator> blockCreators;
    private Map<String, Integer> spacerWidths;
    private int validityCounter;
    private String symbol;
    private int height;
    private int width;
    private int hitPoints;
    private Color stroke;
    /**
     * Constructor for a BlocksDefinitionAnalyzer.
     * Set fields to a default value.
     * @param blocksFileToAnalyze a string of a blocks file to be analyzed
     */
    public BlocksDefinitionAnalyzer(String blocksFileToAnalyze) {
        this.blocksFileToAnalyze = blocksFileToAnalyze;
        this.defaultSymbolFlag = false;
        this.defaultHeightFlag = false;
        this.defaultWidthFlag = false;
        this.defaultHitPointsFlag = false;
        this.defaultFillFlag = false;
        this.defaultStrokeFlag = false;
        this.defaultSymbol = null;
        this.defaultHeight = 0;
        this.defaultWidth = 0;
        this.defaultHitPoints = 0;
        this.defaultFillColors = new HashMap<>();
        this.defaultFillImages = new HashMap<>();
        this.defaultStroke = null;
        this.colorsParser = new ColorsParser();
        this.blockCreators = new HashMap<>();
        this.spacerWidths = new HashMap<>();
        this.validityCounter = 0;
        this.symbol = null;
        this.height = 0;
        this.width = 0;
        this.hitPoints = 0;
        this.stroke = null;
    }
    /**
     * Analyzes the string of the blocks file and set all the block fields from it.
     */
    public void analyzeFile() {
        String[] lines = this.blocksFileToAnalyze.split("\n");
        String comparisonString;
        //goes on all the lines of the blocks file
        for (String s : lines) {
            if (s.startsWith("default ")) {
                this.makeDefaultProperties(s);
            }
            if (s.startsWith("bdef ")) {
                this.makeBdefProperties(s);
            }
            if (s.startsWith("sdef ")) {
                this.makeSdefProperties(s);
            }
        }
    }
    /**
     * Check a default properties line and add the values to the default fields.
     * @param line a default properties line
     */
    private void makeDefaultProperties(String line) {
        String comparisonString = "default ";
        line = line.replaceAll(comparisonString, "");
        String[] properties = line.split(" ");
        //go on all the properties of the given line
        for (String s : properties) {
            comparisonString = "symbol:";
            if (s.startsWith(comparisonString)) {
                s = s.replaceAll(comparisonString, "");
                this.defaultSymbolFlag = true;
                this.defaultSymbol = s;
            }
            comparisonString = "height:";
            if (s.startsWith(comparisonString)) {
                s = s.replaceAll(comparisonString, "");
                this.defaultHeightFlag = true;
                this.defaultHeight = Integer.parseInt(s);
            }
            comparisonString = "width:";
            if (s.startsWith(comparisonString)) {
                s = s.replaceAll(comparisonString, "");
                this.defaultWidthFlag = true;
                this.defaultWidth = Integer.parseInt(s);
            }
            comparisonString = "hit_points:";
            if (s.startsWith(comparisonString)) {
                s = s.replaceAll(comparisonString, "");
                this.defaultHitPointsFlag = true;
                this.defaultHitPoints = Integer.parseInt(s);
            }
            comparisonString = "fill:";
            if (s.startsWith(comparisonString)) {
                this.defaultFillFlag = true;
                s = s.replaceAll(comparisonString, "");
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
                        this.defaultFillImages.put(0, image);
                    }
                }
                comparisonString = "color";
                if (s.startsWith(comparisonString)) {
                    s = s.replaceAll(comparisonString, "");
                    this.defaultFillColors.put(0, this.colorsParser.colorFromString(s));
                }
            }
            comparisonString = "fill-";
            if (s.startsWith(comparisonString)) {
                this.defaultFillFlag = true;
                s = s.replaceAll(comparisonString, "");
                String[] values = s.split(":");
                int number = Integer.parseInt(values[0]);
                s = s.replaceFirst(values[0] + ":", "");
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
                        this.defaultFillImages.put(number, image);
                    }
                }
                comparisonString = "color";
                if (s.startsWith(comparisonString)) {
                    s = s.replaceAll(comparisonString, "");
                    this.defaultFillColors.put(number, colorsParser.colorFromString(s));
                }
            }
            comparisonString = "stroke:";
            if (s.startsWith(comparisonString)) {
                s = s.replaceAll(comparisonString, "");
                s = s.replaceAll("color", "");
                s = s.replace("(", "");
                s = s.replace(")", "");
                this.defaultStrokeFlag = true;
                this.defaultStroke = colorsParser.colorFromString(s);
            }
        }
    }
    /**
     * Check a bdef properties line and add the values to the block fields.
     * @param line a bdef properties line
     */
    private void makeBdefProperties(String line) {
        String comparisonString = "bdef ";
        line = line.replaceAll(comparisonString, "");
        this.validityCounter = 0;
        boolean singleFillFlag = false;
        //make new copies of the default maps for colors and images
        Map<Integer, Color> fillColors = new HashMap<>(this.defaultFillColors);
        Map<Integer, Image> fillImages = new HashMap<>(this.defaultFillImages);
        this.updateDefaults();
        String[] properties = line.split(" ");
        //go on all the properties of the given line
        for (String s : properties) {
            comparisonString = "symbol:";
            if (s.startsWith(comparisonString)) {
                if (!this.defaultSymbolFlag) {
                    this.validityCounter++;
                }
                s = s.replaceAll(comparisonString, "");
                this.symbol = s;
            }
            comparisonString = "height:";
            if (s.startsWith(comparisonString)) {
                if (!this.defaultHeightFlag) {
                    this.validityCounter++;
                }
                s = s.replaceAll(comparisonString, "");
                this.height = Integer.parseInt(s);
            }
            comparisonString = "width:";
            if (s.startsWith(comparisonString)) {
                if (!this.defaultWidthFlag) {
                    this.validityCounter++;
                }
                s = s.replaceAll(comparisonString, "");
                this.width = Integer.parseInt(s);
            }
            comparisonString = "hit_points:";
            if (s.startsWith(comparisonString)) {
                if (!this.defaultHitPointsFlag) {
                    this.validityCounter++;
                }
                s = s.replaceAll(comparisonString, "");
                this.hitPoints = Integer.parseInt(s);
            }
            comparisonString = "fill:";
            if (s.startsWith(comparisonString)) {
                if (!this.defaultFillFlag) {
                    singleFillFlag = true;
                    this.validityCounter++;
                }
                s = s.replaceAll(comparisonString, "");
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
                        fillImages.put(0, image);
                    }
                }
                comparisonString = "color";
                if (s.startsWith(comparisonString)) {
                    s = s.replaceAll(comparisonString, "");
                    fillColors.put(0, this.colorsParser.colorFromString(s));
                }
            }
            comparisonString = "fill-";
            if (s.startsWith(comparisonString)) {
                s = s.replaceAll(comparisonString, "");
                String[] values = s.split(":");
                int number = Integer.parseInt(values[0]);
                if (!singleFillFlag && (number == 1)) {
                    this.validityCounter++;
                    singleFillFlag = true;
                }
                s = s.replaceFirst(values[0] + ":", "");
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
                        fillImages.put(number, image);
                    }
                }
                comparisonString = "color";
                if (s.startsWith(comparisonString)) {
                    s = s.replaceAll(comparisonString, "");
                    fillColors.put(number, this.colorsParser.colorFromString(s));
                }
            }
            comparisonString = "stroke:";
            if (s.startsWith(comparisonString)) {
                s = s.replaceAll(comparisonString + "color", "");
                s = s.replace("(", "");
                s = s.replace(")", "");
                this.stroke = this.colorsParser.colorFromString(s);
            }
        }
        //check if all the fields in the file are valid
        if (this.validityCounter != 5) {
            throw new RuntimeException("Missing fields in the blocks file");
        }
        //creates a block creator from the new initialized fields
        GenericBlockCreator blockCreator = new GenericBlockCreator(this.height, this.width, this.hitPoints,
                fillColors, fillImages, this.stroke);
        this.blockCreators.put(this.symbol, blockCreator);
    }
    /**
     * Update all the fields of a block to be the same as the default fields.
     * Also count them with the validity counter so it will know that the fields are valid.
     */
    private void updateDefaults() {
        if (this.defaultSymbolFlag) {
            this.validityCounter++;
            this.symbol = this.defaultSymbol;
        }
        if (this.defaultHeightFlag) {
            this.validityCounter++;
            this.height = this.defaultHeight;
        }
        if (this.defaultWidthFlag) {
            this.validityCounter++;
            this.width = this.defaultWidth;
        }
        if (this.defaultHitPointsFlag) {
            this.validityCounter++;
            this.hitPoints = this.defaultHitPoints;
        }
        if (this.defaultFillFlag) {
            this.validityCounter++;
        }
        if (this.defaultStrokeFlag) {
            this.stroke = this.defaultStroke;
        }
    }
    /**
     * Check a sdef properties line and add the space values to the spacer widths map.
     * @param line a sdef properties line
     */
    private void makeSdefProperties(String line) {
        line = line.replaceAll("sdef symbol:", "");
        String tempSymbol = String.valueOf(line.charAt(0));
        line = line.replaceFirst(tempSymbol, "");
        line = line.replaceFirst(" width:", "");
        int tempWidth = Integer.parseInt(line);
        this.spacerWidths.put(tempSymbol, tempWidth);
    }
    /**
     * Return the block creators map.
     * @return a block creators map.
     */
    public Map<String, BlockCreator> getBlockCreators() {
        return this.blockCreators;
    }
    /**
     * Return the spacer widths map.
     * @return a spacer widths map.
     */
    public Map<String, Integer> getSpacerWidths() {
        return this.spacerWidths;
    }
}