package specifications;
import levels.LevelInformation;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for LevelSetsReader.
 */
public class LevelSetsReader {
    private List<String> levelKeys;
    private List<String> levelNames;
    private List<List<LevelInformation>> levelSets;
    private LevelSpecificationReader levelReader;
    /**
     * Constructor for a LevelSetsReader.
     * Set all fields to new objects.
     */
    public LevelSetsReader() {
        this.levelKeys = new ArrayList<>();
        this.levelNames = new ArrayList<>();
        this.levelSets = new ArrayList<>();
        this.levelReader = new LevelSpecificationReader();
    }
    /**
     * Analyze the given file and make a list of level sets from it.
     * @param file a string of a level set file path
     */
    public void fromFile(String file) {
        LineNumberReader lineNumberReader = null;
        String line;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(file);
        if (is != null) {
            try {
                //make a line number reader from the given file
                lineNumberReader = new LineNumberReader(new InputStreamReader(is));
                //run on all the lines of file
                while ((line = lineNumberReader.readLine()) != null) {
                    //if odd number line
                    if (lineNumberReader.getLineNumber() % 2 == 1) {
                        String key = String.valueOf(line.charAt(0));
                        line = line.replaceFirst(key + ":", "");
                        this.levelKeys.add(key);
                        this.levelNames.add(line);
                    //if even number line
                    } else {
                        Reader lineReader = null;
                        InputStream is2 = ClassLoader.getSystemClassLoader().getResourceAsStream(line);
                        if (is2 != null) {
                            try {
                                lineReader = new BufferedReader(new InputStreamReader(is2));
                                this.levelSets.add(this.levelReader.fromReader(lineReader));
                            } finally {
                                if (lineReader != null) {
                                    try {
                                        lineReader.close();
                                        is2.close();
                                    } catch (IOException e) {
                                        System.err.println("Error with closing file");
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            } finally {
                if (lineNumberReader != null) {
                    try {
                        lineNumberReader.close();
                        is.close();
                    } catch (IOException e) {
                        System.err.println("Error with closing file");
                    }
                }
            }
        }

    }
    /**
     * Return the level keys list.
     * @return level keys list
     */
    public List<String> getLevelKeys() {
        return this.levelKeys;
    }
    /**
     * Return the level names list.
     * @return level names list
     */
    public List<String> getLevelNames() {
        return this.levelNames;
    }
    /**
     * Return the level sets list.
     * @return level sets list
     */
    public List<List<LevelInformation>> getLevelSets() {
        return this.levelSets;
    }
}