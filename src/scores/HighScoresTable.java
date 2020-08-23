package scores;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for HighScoresTable that implements Serializable.
 */
public class HighScoresTable implements Serializable {
    private int size;
    private List<ScoreInfo> table;
    /**
     * Constructor for a high scores table.
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     * @param size the size of the table
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.table = new ArrayList<>();
    }
    /**
     * Adds a high score to the table.
     * @param score a given score information object
     */
    public void add(ScoreInfo score) {
        int newScoreIndex = this.getRank(score.getScore());
        if (newScoreIndex <= this.size) {
            this.table.add(newScoreIndex - 1, score);
        }
        if (this.table.size() > this.size) {
            int lastIndex = this.table.size() - 1;
            this.table.remove(lastIndex);
        }
    }
    /**
     * Return the table size.
     * @return the table size
     */
    public int size() {
        return this.size;
    }
    /**
     * Return the current high scores.
     * The list is sorted such that the highest scores come first.
     * @return the high scores list
     */
    public List<ScoreInfo> getHighScores() {
        return this.table;
    }
    /**
     * Return the rank of the current score: where will it be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not be added to the list.
     * @param score a given score value
     * @return the rank for the given score
     */
    public int getRank(int score) {
        for (int i = 0; i < this.table.size(); i++) {
            if (score >= this.table.get(i).getScore()) {
                return i + 1;
            }
        }
        return this.table.size() + 1;
    }
    /**
     * Clears the table.
     */
    public void clear() {
        this.table.clear();
    }
    /**
     * Load table data from file.
     * Current table data is cleared.
     * @param filename a given high scores file
     * @throws IOException if there is a problem with the file
     */
    public void load(File filename) throws IOException {
        //clears the current table data
        this.clear();
        HighScoresTable tableList;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            tableList = (HighScoresTable) objectInputStream.readObject();
        //if can't find file to open does nothing, because then it will create a new high scores file
        } catch (FileNotFoundException e) {
            return;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find class for object in file");
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    System.err.println("Error with closing file");
                }
            }
        }
        if (tableList != null) {
            //update this high score table fields
            this.size = tableList.size();
            this.table = tableList.getHighScores();
        }
    }
    /**
     * Save table data to the specified file.
     * @param filename a given file
     * @throws IOException if there is a problem with the file
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        }
    }
    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with reading it, an empty table is returned.
     * @param filename a given file
     * @return a high score table object
     */
    public static HighScoresTable loadFromFile(File filename) {
        //set the high score table to be with the size of 5
        HighScoresTable table = new HighScoresTable(5);
        try {
            table.load(filename);
        } catch (IOException e) {
            return table;
        }
        return table;
    }
}