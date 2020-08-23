package game;
import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import other.Counter;
import scores.HighScoresTable;
import scores.ScoreInfo;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This is the class for GameFlow.
 */
public class GameFlow {
    //fields
    private Counter score;
    private HighScoresTable table;
    private Counter numberOfLives;
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private int screenWidth;
    private int screenHeight;
    private int bordersThickness;
    private File highScoreFile;
    private String levelSetName;
    /**
     * The constructor for a game flow.
     * @param ar an animation runner
     * @param ks a keyboard sensor
     * @param table a high score table
     * @param highScoreFile the file of the high score table
     * @param levelSetName the name of this level set
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, HighScoresTable table, File highScoreFile,
                    String levelSetName) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.table = table;
        this.highScoreFile = highScoreFile;
        this.levelSetName = levelSetName;
        //the game score starts as 0
        this.score = new Counter(0);
        //the number of lives for the player starts as 7
        this.numberOfLives = new Counter(7);
    }
    /**
     * Set the screen parameters for this game flow.
     * @param width the width of the screen of the game
     * @param height the height of the screen of the game
     * @param thickness the thickness of the screen borders of the game
     */
    public void setScreenParameters(int width, int height, int thickness) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.bordersThickness = thickness;
    }
    /**
     * Run all the given levels one after the other.
     * @param levels a list of level informations
     */
    public void runLevels(List<LevelInformation> levels) {
        //the win state starts as true
        boolean winState = true;
        //run on all the given levels
        for (LevelInformation levelInfo : levels) {
            //creates the blocks for this level
            levelInfo.createBlocks();
            //creates a game level object from the current level information
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner,
                    this.score, this.numberOfLives, this.levelSetName);
            //set the screen parameters of the current game level
            level.setScreenParameters(this.screenWidth, this.screenHeight, this.bordersThickness);
            //initialize the current game level
            level.initialize();
            //while there are still blocks in the level, and the played still has lives
            while (!level.getBlocksCounter().isZero() && !this.numberOfLives.isZero()) {
                //play one turn of the game
                level.playOneTurn();
            }
            //if the played has 0 lives
            if (this.numberOfLives.isZero()) {
                //goes to 'end screen' animation with the score and false for losing
                //wrapping it by a 'space' key stoppable object
                this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                        KeyboardSensor.SPACE_KEY, new EndScreen(this.score, false)));
                //the win state is false
                winState = false;
                break;
            }
            //if there are no more blocks in the level
            if (level.getBlocksCounter().isZero()) {
                //increases the score by 100, and then the for loop goes to the next level
                this.score.increase(100);
            }
        }
        //if successfully finished going on all the levels
        if (winState) {
            //goes to 'end screen' animation with the score and true for winning
            //wrapping it by a 'space' key stoppable object
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                    KeyboardSensor.SPACE_KEY, new EndScreen(this.score, true)));
        }
        //adds this player to the high score if it should
        this.addPlayerToHighScoreTable();
        //runs the high score table animation with this high score table
        //wrapping it by a 'space' key stoppable object
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                KeyboardSensor.SPACE_KEY, new HighScoresAnimation(this.table)));
    }
    /**
     * Adds this player score to the high score table if his score is high enough to enter.
     */
    private void addPlayerToHighScoreTable() {
        int tableSize = this.table.size();
        int playerRank = this.table.getRank(this.score.getValue());
        //if the player rank is smaller than the table size, it means his score should enter the table
        if (playerRank <= tableSize) {
            //creates a dialog manager
            DialogManager dialog = this.animationRunner.getGui().getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            //creates a score info of this player name and score
            ScoreInfo playerScore = new ScoreInfo(name, this.score.getValue());
            //adds that player to the table
            this.table.add(playerScore);
            //try to save the high score table to the file
            try {
                this.table.save(this.highScoreFile);
            } catch (IOException e) {
                System.err.println("Error with high score table file");
            }
        }
    }
}