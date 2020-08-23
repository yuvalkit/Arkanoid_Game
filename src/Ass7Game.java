import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import animation.Menu;
import animation.MenuAnimation;
import animation.HighScoresAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import levels.LevelInformation;
import specifications.LevelSetsReader;
import other.Task;
import scores.HighScoresTable;
import java.io.File;
import java.util.List;

/**
 * This is the class for Ass7Game.
 */
public class Ass7Game {
    /**
     * This is the main for this class.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //creates a new file for the high score table with the given name of the file
        File highScoreFile = new File("highscores");
        //this high score table loads a table from the file above
        HighScoresTable table = HighScoresTable.loadFromFile(highScoreFile);
        //set the screen size
        int screenWidth = 800;
        int screenHeight = 600;
        //set the thickness of the screen borders
        int bordersThickness = 25;
        //creates the gui of the game
        GUI gui = new GUI("Ass7Game", screenWidth, screenHeight);
        //creates an animation runner for this game
        AnimationRunner runner = new AnimationRunner(gui);
        //creates a keyboard sensor for this game
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        String file;
        //if there is an argument
        if (args.length != 0) {
            //the file uses the first argument
            file = args[0];
        //if there are no arguments
        } else {
            //the file uses a default level-sets file path
            file = "level_sets.txt";
        }
        //creates a LevelSetsReader and sends the file above for it to analyze
        LevelSetsReader levelSetsReader = new LevelSetsReader();
        levelSetsReader.fromFile(file);
        //get the list of keys and names from the level set
        List<String> levelKeys = levelSetsReader.getLevelKeys();
        List<String> levelNames = levelSetsReader.getLevelNames();
        //get the list of list of levels from the level set
        List<List<LevelInformation>> levelSets = levelSetsReader.getLevelSets();
        //creates the level sets sub menu
        Menu<Task<Void>> levelSetsMenu = new MenuAnimation<>("Level Sets", keyboard, runner);
        //runs on all the keys
        for (int i = 0; i < levelKeys.size(); i++) {
            String key = levelKeys.get(i);
            String name = levelNames.get(i);
            List<LevelInformation> levels = levelSets.get(i);
            //adds a selection to the level sets menu
            levelSetsMenu.addSelection(key, name, new Task<Void>() {
                @Override
                public Void run() {
                    //creates a game flow object
                    GameFlow gameFlow = new GameFlow(runner, keyboard, table, highScoreFile, name);
                    //set the game flow screen parameters
                    gameFlow.setScreenParameters(screenWidth, screenHeight, bordersThickness);
                    //run the current levels
                    gameFlow.runLevels(levels);
                    //must return null because the task is set to void
                    return null;
                }
            });
        }
        //creates the main menu with the Arkanoid title and the name of my game
        Menu<Task<Void>> mainMenu = new MenuAnimation<>("Arkanoid - Game Of Balls", keyboard, runner);
        //adds the level sets sub menu to the main menu
        mainMenu.addSubMenu("s", "Start Game", levelSetsMenu);
        //adds a high score selection to the main menu
        mainMenu.addSelection("h", "High Scores", new Task<Void>() {
            @Override
            public Void run() {
                //runs a high score table animation
                runner.run(new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY,
                        new HighScoresAnimation(table)));
                //must return null because the task is set to void
                return null;
            }
        });
        //adds a quit selection to the main menu
        mainMenu.addSelection("q", "Quit", new Task<Void>() {
            @Override
            public Void run() {
                //close the gui
                gui.close();
                //exit the system
                System.exit(0);
                //must return null because the task is set to void
                return null;
            }
        });
        //the loop of the game that stops only when quiting the game
        while (true) {
            //runs the main menu
            runner.run(mainMenu);
            //get the status of the menu as a task
            Task<Void> task = mainMenu.getStatus();
            //runs that task
            task.run();
            //clear the current selection of the main menu
            mainMenu.clearSelection();
        }
    }
}