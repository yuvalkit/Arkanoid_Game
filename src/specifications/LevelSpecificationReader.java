package specifications;
import levels.GenericLevel;
import levels.LevelInformation;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for LevelSpecificationReader.
 */
public class LevelSpecificationReader {
    /**
     * Get a file and returns a list of LevelInformation objects.
     * @param reader a file of levels
     * @return a list of levels
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levels = new ArrayList<>();
        List<String> levelsStrings = this.splitToLevels(reader);
        //run on all the levels
        for (String s : levelsStrings) {
            //make the level and adds it to the levels list
            LevelInformation level = new GenericLevel();
            level.extractStringToLevel(s);
            levels.add(level);
        }
        return levels;
    }
    /**
     * Get a file of levels and split it to the different level strings.
     * @param reader a file of levels
     * @return a list of level strings
     */
    private List<String> splitToLevels(java.io.Reader reader) {
        //cast the reader to a BufferedReader
        BufferedReader bufferedReader = (BufferedReader) reader;
        List<String> levels = new ArrayList<>();
        String line;
        String levelString = "";
        try {
            //run on all the lines of the file
            while ((line = bufferedReader.readLine()) != null) {
                //ignore if blank line or #
                if (line.equals("") || line.startsWith("#")) {
                    continue;
                }
                if (line.equals("START_LEVEL")) {
                    levelString = "";
                    continue;
                }
                if (line.equals("END_LEVEL")) {
                    levels.add(levelString);
                    continue;
                }
                levelString = levelString.concat(line).concat("\n");
            }
        } catch (IOException e) {
            System.err.println("Failed reading file");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.err.println("Error with closing file");
                }
            }
        }
        return levels;
    }
}