package specifications;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * This is the class for BlocksDefinitionReader.
 */
public class BlocksDefinitionReader {
    /**
     * Reading a block definitions file and returning a BlocksFromSymbolsFactory object.
     * @param reader a reader of a block definitions file
     * @return a BlocksFromSymbolsFactory from the given block definitions file
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        //cast the reader to a BufferedReader
        BufferedReader bufferedReader = (BufferedReader) reader;
        String line;
        String fileString = "";
        try {
            //goes on all the lines of the file and make one string from it, while ignoring blank lines and #'s
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("") || line.startsWith("#")) {
                    continue;
                }
                fileString = fileString.concat(line).concat("\n");
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
        //make a block analyzer and gives it the file string to analyze
        BlocksDefinitionAnalyzer analyzer = new BlocksDefinitionAnalyzer(fileString);
        analyzer.analyzeFile();
        return new BlocksFromSymbolsFactory(analyzer.getBlockCreators(), analyzer.getSpacerWidths());
    }
}