package game;
import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import biuoop.KeyboardSensor;
import collision.Collidable;
import geometry.Point;
import levels.LevelInformation;
import other.Counter;
import other.Velocity;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import biuoop.DrawSurface;
import sprites.Ball;
import sprites.SpriteCollection;
import sprites.Paddle;
import sprites.Sprite;
import sprites.Block;
import sprites.LivesIndicator;
import sprites.ScoreIndicator;
import sprites.NameIndicator;
import javax.imageio.ImageIO;

/**
 * This is the class for GameLevel that implements Animation.
 */
public class GameLevel implements Animation {
    //collections of sprites and the game environment
    private SpriteCollection sprites;
    private GameEnvironment environment;
    //the counters
    private Counter blocksCounter;
    private Counter ballsCounter;
    private Counter score;
    private Counter numberOfLives;
    //keyboard sensor, paddle and the animation runner
    private KeyboardSensor keyboard;
    private Paddle paddle;
    private AnimationRunner runner;
    //a flag for if the game level is still running
    private boolean running;
    //the screen parameters
    private int screenWidth;
    private int screenHeight;
    private int bordersThickness;
    //the information of the current level and the name of the level set
    private LevelInformation level;
    private String levelSetName;
    /**
     * Constructor for a game level.
     * @param level a given level information
     * @param keyboard a keyboard sensor
     * @param runner an animation runner
     * @param score a score counter
     * @param lives the player lives counter
     * @param levelSetName the name of the level set that this level is in
     */
    public GameLevel(LevelInformation level, KeyboardSensor keyboard, AnimationRunner runner, Counter score,
                     Counter lives, String levelSetName) {
        this.level = level;
        this.keyboard = keyboard;
        this.runner = runner;
        this.score = score;
        this.numberOfLives = lives;
        this.levelSetName = levelSetName;
        //makes new sprite and collidable collections
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }
    /**
     * Set the screen parameters for this game level.
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
     * Add a given collidable to the environment.
     * @param c a collidable object
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }
    /**
     * Adds a sprite to the sprites list.
     * @param s a sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }
    /**
     * Adds a sprite to the sprites list in a given specific index.
     * @param s a sprite
     * @param index a given index
     */
    public void addSpriteByPlace(Sprite s, int index) {
        //calls to add by place method of the sprites collection field with the given sprite and index
        this.sprites.addSpriteByPlace(s, index);
    }
    /**
     * Returns the blocks counter of this game level.
     * @return the blocks counter
     */
    public Counter getBlocksCounter() {
        return this.blocksCounter;
    }
    /**
     * Initialize a new game level by creating all the objects for the level.
     */
    public void initialize() {
        //blocks counter starts at this level number of blocks to remove
        this.blocksCounter = new Counter(this.level.numberOfBlocksToRemove());
        //balls counter starts at 0 and will go up when adding a new ball
        this.ballsCounter = new Counter(0);
        //make the removers
        BlockRemover blocksRemover = new BlockRemover(this, this.blocksCounter);
        BallRemover ballsRemover = new BallRemover(this, this.ballsCounter);
        //make the score listener
        ScoreTrackingListener scoreTrack = new ScoreTrackingListener(this.score);
        //add the background sprite to the sprites list
        this.makeBackground();
        //add screen borders to the game
        this.makeScreenBorders();
        //add blocks to the game
        this.makeBlocks(blocksRemover, scoreTrack);
        //add paddle to the game
        this.makePaddle();
        //add the death-region block to the game
        this.makeDeathRegion(ballsRemover);
        //add the score, lives and name of level indicators to the game
        this.makeIndicators();
    }
    /**
     * Plays one turn of the game level by an animation runner loop.
     */
    public void playOneTurn() {
        //create balls on paddle in the middle of the screen each turn
        this.createBallsOnTopOfPaddle();
        //set countdown timer to 2 seconds
        int countdownNumOfSeconds = 2;
        //set countdown count from 3 and have a "GO!" sign also, so total of 4
        int countdownCountFrom = 4;
        //running a countdown animation with this game level sprites
        this.runner.run(new CountdownAnimation(countdownNumOfSeconds, countdownCountFrom, this.sprites));
        //enable running
        this.running = true;
        //uses the animation runner to run the current animation - which is one turn of the game
        this.runner.run(this);
    }
    /**
     * Make 3 borders on the edges of the screen and the indicators bar block.
     */
    private void makeScreenBorders() {
        //these are the paths for the borders and indicator bar images
        String sideImageName = "block_images/side_border_block.jpg";
        String upImageName = "block_images/up_border_block.jpg";
        String indicatorsImageName = "block_images/indicators_block.png";
        //set 3 input streams
        InputStream is1 = null;
        InputStream is2 = null;
        InputStream is3 = null;
        //set 3 buffered images
        BufferedImage sideImage = null;
        BufferedImage upImage = null;
        BufferedImage indicatorsImage = null;
        try {
            //get the image of each border
            is1 = ClassLoader.getSystemClassLoader().getResourceAsStream(sideImageName);
            is2 = ClassLoader.getSystemClassLoader().getResourceAsStream(upImageName);
            is3 = ClassLoader.getSystemClassLoader().getResourceAsStream(indicatorsImageName);
            //if successfully loaded the images so the input streams are not null
            if ((is1 != null) && (is2 != null) && (is3 != null)) {
                //get images
                sideImage = ImageIO.read(is1);
                upImage = ImageIO.read(is2);
                indicatorsImage = ImageIO.read(is3);
            }
        } catch (IOException e) {
            System.err.println("Failed opening images");
        } finally {
            //closing all the input streams
            if (is1 != null) {
                try {
                    is1.close();
                } catch (IOException e) {
                    System.err.println("Failed closing image");
                }
            }
            if (is2 != null) {
                try {
                    is2.close();
                } catch (IOException e) {
                    System.err.println("Failed closing image");
                }
            }
            if (is3 != null) {
                try {
                    is3.close();
                } catch (IOException e) {
                    System.err.println("Failed closing image");
                }
            }
        }
        //make the lives, score and name indicators block on the top of the screen
        Block indicatorsBlock = new Block(0, 0, this.screenWidth, this.bordersThickness, 0, null);
        Color color = Color.BLACK;
        //if there is a image for the indicators bar
        if (indicatorsImage != null) {
            //set that image to this block
            indicatorsBlock.setOneImage(indicatorsImage);
        } else {
            //sets the color to this block
            indicatorsBlock.setOneColor(color);
        }
        //make the borders gray by default
        color = Color.GRAY;
        //the height of the 2 sides borders
        int sidesHeight = this.screenHeight - (2 * this.bordersThickness);
        //make the up border
        Block upBorder = new Block(0, this.bordersThickness, this.screenWidth, this.bordersThickness, 0, null);
        //if there is a image for the up border
        if (upImage != null) {
            //sets that image to this block
            upBorder.setOneImage(upImage);
        } else {
            //sets the color to this block
            upBorder.setOneColor(color);
        }
        //make the left border
        Block leftBorder = new Block(0, (2 * this.bordersThickness), this.bordersThickness, sidesHeight, 0, null);
        Block rightBorder = new Block((this.screenWidth - this.bordersThickness), (2 * this.bordersThickness),
                this.bordersThickness, sidesHeight, 0, null);
        //if there is a image for the side borders
        if (sideImage != null) {
            //sets that image to these blocks
            leftBorder.setOneImage(sideImage);
            rightBorder.setOneImage(sideImage);
        } else {
            //sets the color to these blocks
            leftBorder.setOneColor(color);
            rightBorder.setOneColor(color);
        }
        //add indicators block and all borders to this game
        indicatorsBlock.addToGame(this);
        upBorder.addToGame(this);
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);
    }
    /**
     * Make the balls of this game level.
     */
    private void makeBalls() {
        Color color;
        int radius;
        //if this is my main theme level set
        if (this.isMainThemeLevelSet()) {
            //set the color to null so the ball will know to set the colors by itself
            color = null;
            radius = 6;
        //if this is not my main theme level set
        } else {
            //make the balls white
            color = Color.WHITE;
            radius = 5;
        }
        //make the start point in the middle of the screen on top of the paddle
        Point startPoint = new Point(((double) this.screenWidth / 2), this.screenHeight - 50);
        //make a velocities list from this level information velocities list
        List<Velocity> velocitiesList = this.level.initialBallVelocities();
        //runs on all velocities
        for (Velocity v : velocitiesList) {
            //makes a new ball with the game parameters
            Ball ball = new Ball(startPoint, radius, color, this.environment, this.screenWidth,
                    this.bordersThickness);
            //set the ball with the current velocity
            ball.setVelocity(v);
            //add the ball to the game
            ball.addToGame(this);
            //the balls counter grows by 1
            this.ballsCounter.increase(1);
        }
    }
    /**
     * Make the blocks for the game.
     * @param blocksRemover a blocks remover listener
     * @param scoreTrack a score tracking listener
     */
    private void makeBlocks(BlockRemover blocksRemover, ScoreTrackingListener scoreTrack) {
        //make a blocks list from this level information blocks list
        List<Block> blocksList = this.level.blocks();
        //runs on all blocks
        for (Block block : blocksList) {
            //adds the current block to the game
            block.addToGame(this);
            //adds the two listeners to this block
            block.addHitListener(blocksRemover);
            block.addHitListener(scoreTrack);
        }
    }
    /**
     * Make the paddle of this game.
     */
    private void makePaddle() {
        //set width and speed for the paddle from the level information
        double width = this.level.paddleWidth();
        int speed = this.level.paddleSpeed();
        //set height
        double height = 20;
        //set x value for the paddle to be in the middle of the screen
        double upperLeftX = (double) (this.screenWidth / 2) - (width / 2);
        //set y value for the paddle to be on the bottom of the screen
        double upperLeftY = this.screenHeight - this.bordersThickness - height;
        Point upperLeft = new Point(upperLeftX, upperLeftY);
        boolean hasImage;
        hasImage = (this.isMainThemeLevelSet() && (this.level.paddleWidth() == 160));
        //make the paddle
        this.paddle = new Paddle(upperLeft, width, height, hasImage, this.keyboard, speed);
        //set the paddle borders
        this.paddle.setBorders(this.screenWidth, this.screenHeight, this.bordersThickness);
        //add the paddle to this game
        this.paddle.addToGame(this);
    }
    /**
     * Make the death-region block that indicates when a ball fall out of screen, and removes it.
     * @param ballsRemover a balls remover listener
     */
    private void makeDeathRegion(BallRemover ballsRemover) {
        //get the x, y and width for the block according to the screen parameters
        double xValue = this.bordersThickness;
        double yValue = this.screenHeight;
        double width = this.screenWidth - (2 * this.bordersThickness);
        //set height and hit points to 0
        double height = 0;
        int hitPoints = 0;
        //make the death-region block
        Block deathRegion = new Block(xValue, yValue, width, height, hitPoints, null);
        deathRegion.setOneColor(Color.BLACK);
        //add the block to this game
        deathRegion.addToGame(this);
        //add the remover as a listener to the block
        deathRegion.addHitListener(ballsRemover);
    }
    /**
     * Make the score and lives indicators for the game.
     */
    private void makeIndicators() {
        int fontSize = 15;
        //set the y of the text to 17 because that will put it at the top of the screen and it will be readable
        int textY = 17;
        //get the screen width
        int width = this.screenWidth;
        //make the indicators
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score, width, fontSize, textY);
        LivesIndicator livesIndicator = new LivesIndicator(this.numberOfLives, width, fontSize, textY);
        NameIndicator nameIndicator = new NameIndicator(this.level.levelName(), width, fontSize, textY);
        //add them to this game
        scoreIndicator.addToGame(this);
        livesIndicator.addToGame(this);
        nameIndicator.addToGame(this);
    }
    /**
     * Adds the current level background to the sprites list of this game level.
     */
    private void makeBackground() {
        //add the background to the 0 index of the sprites list so it will always be drawn first
        this.sprites.addSpriteByPlace(this.level.getBackground(), 0);
    }
    /**
     * Removes a given collidable from this game.
     * @param c a collidable
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }
    /**
     * Removes a given sprite from this game.
     * @param s a sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }
    /**
     * Move the paddle to the middle of the screen and creates this level balls on top of it.
     */
    public void createBallsOnTopOfPaddle() {
        //moves the paddle to the middle of the screen
        this.paddle.moveToMiddle();
        //adds this level balls to the game on top of the paddle
        this.makeBalls();
    }
    @Override
    public boolean shouldStop() {
        //return if this game is not running anymore
        return !this.running;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        //draws all sprites on surface
        this.sprites.drawAllOn(d);
        //notify all the sprites time passed
        this.sprites.notifyAllTimePassed();
        //if there are 0 blocks, the running is stopped
        if (this.blocksCounter.isZero()) {
            this.running = false;
        }
        //if there are 0 balls, the lives decrease by 1 and the running is stopped
        if (this.ballsCounter.isZero()) {
            this.numberOfLives.decrease(1);
            this.running = false;
        }
        //if the 'p' key is pressed, goes to pause screen
        if (this.keyboard.isPressed("p")) {
            //goes to 'pause' animation screen and wrapping it by a 'space' key stoppable object
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                    new PauseScreen()));
        }
    }
    /**
     * Check if this is my main theme level set named "Game Of Balls".
     * @return true if it is, false otherwise
     */
    private boolean isMainThemeLevelSet() {
        //return if this level set name equals to my main theme level set
        return this.levelSetName.equals("Game Of Balls");
    }
}