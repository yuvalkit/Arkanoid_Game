package scores;
import java.io.Serializable;

/**
 * This is the class for ScoreInfo that implements Serializable.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;
    /**
     * Constructor for a ScoreInfo.
     * @param name the name of the player
     * @param score the score value
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }
    /**
     * Return the name of the player.
     * @return a name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Return the score of the player.
     * @return a score
     */
    public int getScore() {
        return this.score;
    }
}