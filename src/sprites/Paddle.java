package sprites;
import collision.Collidable;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import other.Velocity;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is the class for Paddle that implements Sprite and Collidable.
 */
public class Paddle implements Sprite, Collidable {
    //fields
    private biuoop.KeyboardSensor keyboard;
    private Rectangle shape;
    private boolean hasImage;
    private int paddleSpeed;
    private GUI gui;
    private int screenWidth;
    private int screenHeight;
    private int bordersThickness;
    private Image paddleImage;
    //the 5 regions of the paddle
    private Line region1;
    private Line region2;
    private Line region3;
    private Line region4;
    private Line region5;
    /**
     * Constructor for a paddle.
     * @param upperLeft the top left point of the paddle
     * @param width the width of the paddle
     * @param height the height of the paddle
     * @param hasImage if this paddle has a image
     * @param keyboard the keyboard sensor for the paddle
     * @param speed a given speed
     */
    public Paddle(Point upperLeft, double width, double height, boolean hasImage, KeyboardSensor keyboard,
                  int speed) {
        this.shape = new Rectangle(upperLeft, width, height);
        this.hasImage = hasImage;
        this.keyboard = keyboard;
        //make the regions of the paddle
        this.makeRegions(upperLeft.getX(), upperLeft.getY(), width);
        //set the paddle speed
        this.paddleSpeed = speed;
        //get the paddle image
        this.paddleImage = this.getPaddleImage();
    }
    /**
     * Set the borders details in which the paddle is in.
     * @param width the width of the screen
     * @param height the height of the screen
     * @param thickness the thickness of the borders in the screen
     */
    public void setBorders(int width, int height, int thickness) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.bordersThickness = thickness;
    }
    /**
     * Moves the paddle to the left if possible.
     */
    public void moveLeft() {
        //the amount to move is negative because left is to move against x axis
        int moveAmount = -1 * this.paddleSpeed;
        //the x value of the top left point of the paddle after the move left
        double newUpLeftX = this.shape.getUpperLeft().getX() + moveAmount;
        //if the new x value is outside of borders
        if (newUpLeftX < this.bordersThickness) {
            //set the move amount so the paddle will exactly touch the border
            moveAmount = -1 * (int) (this.shape.getUpperLeft().getX()
                    - this.bordersThickness);
        }
        //moves the paddle to the new spot to the left
        this.move(moveAmount);
    }
    /**
     * Moves the paddle to the right if possible.
     */
    public void moveRight() {
        int moveAmount = this.paddleSpeed;
        //the x value of the top right point of the paddle after the move right
        double newUpRightX = this.shape.getUpperLeft().getX()
                + this.shape.getWidth() + moveAmount;
        //if the new x value is outside of borders
        if (newUpRightX > (this.screenWidth - this.bordersThickness)) {
            //the current x value of the top right point of the paddle
            double upRightX = this.shape.getUpperLeft().getX()
                    + this.shape.getWidth();
            //set the move amount so the paddle will exactly touch the border
            moveAmount = (int) (this.screenWidth - this.bordersThickness
                    - upRightX);
        }
        //moves the paddle to the new spot to the right
        this.move(moveAmount);
    }
    /**
     * The time passed method of the sprites.Sprite interface.
     * Checks if the paddle needs to go right or left.
     */
    @Override
    public void timePassed() {
        //if left key was pressed, move paddle to the left
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
        //if right key was pressed, move paddle to the right
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }
    /**
     * Draws the paddle on the given DrawSurface.
     * @param d the given DrawSurface
     */
    @Override
    public void drawOn(DrawSurface d) {
        //get the paddle shape details
        int upLeftX = (int) this.shape.getUpperLeft().getX();
        int upLeftY = (int) this.shape.getUpperLeft().getY();
        int width = (int) this.shape.getWidth();
        int height = (int) this.shape.getHeight();
        //if there is a image to draw on the paddle
        if (this.hasImage && this.paddleImage != null) {
            d.drawImage(upLeftX, upLeftY, this.paddleImage);
        } else {
            //set the paddle color
            d.setColor(Color.ORANGE);
            //draws the paddle
            d.fillRectangle(upLeftX, upLeftY, width, height);
            //set black for the paddle outline
            d.setColor(Color.BLACK);
            //draws the paddle outline
            d.drawRectangle(upLeftX, upLeftY, width, height);
        }
    }
    @Override
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //get dx and dy of velocity
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        //does pythagoras formula to find the ball speed from his dx and dy
        double ballSpeed = Math.sqrt((dx * dx) + (dy * dy));
        //if the ball hits region 3, it just changes his y axis direction
        if (this.region3.isInLineRange(collisionPoint)) {
            dy *= -1;
            return new Velocity(dx, dy);
        }
        //if the ball hits region 1, it changes his angle to 300
        if (this.region1.isInLineRange(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(300, ballSpeed);
        }
        //if the ball hits region 2, it changes his angle to 330
        if (this.region2.isInLineRange(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(330, ballSpeed);
        }
        //if the ball hits region 4, it changes his angle to 30
        if (this.region4.isInLineRange(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(30, ballSpeed);
        }
        //if the ball hits region 5, it changes his angle to 60
        if (this.region5.isInLineRange(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(60, ballSpeed);
        }
        //if the balls hits the paddle from the right or left sides
        if (this.shape.getRightLine().isInLineRange(collisionPoint)
                || this.shape.getLeftLine().isInLineRange(collisionPoint)) {
            //set dx to opposite x axis direction
            dx *= -1;
            return new Velocity(dx, dy);
        }
        return currentVelocity;
    }
    /**
     * Adds the paddle to the collidables and sprites lists of the given game.
     * @param g a given game
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
    /**
     * Make the 5 regions of the paddle.
     * The regions are the top line of the paddle divided to
     * 5 equally-spaced lines, each of the regions sends the object
     * hitting the paddle to a different angle.
     * @param upperLeftX the x value of top left point of the paddle
     * @param upperLeftY the y value of top left point of the paddle
     * @param width the width of the paddle
     */
    private void makeRegions(double upperLeftX, double upperLeftY,
                            double width) {
        //the length of each of the 5 regions
        double regionLength = width / 5;
        Point upperLeft = new Point(upperLeftX, upperLeftY);
        //set the end point for each of the regions
        Point region1End = new Point((upperLeftX + regionLength), upperLeftY);
        Point region2End = new Point((upperLeftX + (2 * regionLength)),
                upperLeftY);
        Point region3End = new Point((upperLeftX + (3 * regionLength)),
                upperLeftY);
        Point region4End = new Point((upperLeftX + (4 * regionLength)),
                upperLeftY);
        Point region5End = new Point((upperLeftX + (5 * regionLength)),
                upperLeftY);
        //set each region line with the correct start and end points
        this.region1 = new Line(upperLeft, region1End);
        this.region2 = new Line(region1End, region2End);
        this.region3 = new Line(region2End, region3End);
        this.region4 = new Line(region3End, region4End);
        this.region5 = new Line(region4End, region5End);
    }
    /**
     * Moves the paddle by the given move amount.
     * @param moveAmount the amount to move the paddle
     */
    private void move(int moveAmount) {
        //get the new x value of the top left point after the move
        double upperLeftX = this.shape.getUpperLeft().getX() + moveAmount;
        double upperLeftY = this.shape.getUpperLeft().getY();
        Point upperLeft = new Point(upperLeftX, upperLeftY);
        double width = this.shape.getWidth();
        double height = this.shape.getHeight();
        //set the new shape after the move for the paddle
        this.shape = new Rectangle(upperLeft, width, height);
        //set the new regions after the move for the paddle
        this.makeRegions(upperLeftX, upperLeftY, width);
    }
    /**
     * Moves the paddle to the middle of the screen.
     */
    public void moveToMiddle() {
        //get the width and height of this paddle
        double width = this.shape.getWidth();
        double height = this.shape.getHeight();
        //set x value for the paddle to be in the middle of the screen
        double upperLeftX = (double) (this.screenWidth / 2) - (width / 2);
        //set y value for the paddle to be on the bottom of the screen
        double upperLeftY = this.screenHeight - this.bordersThickness - height;
        //make a point from the x and y above
        Point upperLeft = new Point(upperLeftX, upperLeftY);
        //set the paddle shape to the new location in the middle
        this.shape = new Rectangle(upperLeft, width, height);
        //set the regions for that location also
        this.makeRegions(upperLeftX, upperLeftY, width);
    }
    /**
     * Returns the image for the paddle if there is one.
     * @return the paddle image
     */
    private Image getPaddleImage() {
        //this is the path for the paddle image
        String imageName = "block_images/paddle_image.jpg";
        InputStream is = null;
        BufferedImage image = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
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
        return image;
    }
}