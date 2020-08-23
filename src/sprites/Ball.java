package sprites;
import collision.Collidable;
import collision.CollisionInfo;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import other.Velocity;
import game.GameEnvironment;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * This is the class for Ball that implements Sprite.
 */
public class Ball implements Sprite {
    //the fields of a ball
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment game;
    private int screenWidth;
    private int bordersThickness;
    /**
     * Constructor for a new ball.
     * @param center the center point of the ball
     * @param r the radius of the ball
     * @param color the color of the ball
     * @param game the game environment the ball is in
     * @param screenWidth the width of the screen the ball is in
     * @param bordersThickness the thickness of the borders of the screen
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment game, int screenWidth,
                int bordersThickness) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.game = game;
        this.screenWidth = screenWidth;
        this.bordersThickness = bordersThickness;
    }
    /**
     * Return the x value of the center point.
     * @return x value of center
     */
    public double getX() {
        return this.center.getX();
    }
    /**
     * Return the y value of the center point.
     * @return y value of center
     */
    public double getY() {
        return this.center.getY();
    }
    /**
     * Return the radius of the ball.
     * @return the radius
     */
    public int getSize() {
        return this.radius;
    }
    /**
     * Return the color of the ball.
     * @return the color
     */
    public java.awt.Color getColor() {
        return this.color;
    }
    /**
     * Draws the ball on the given DrawSurface.
     * @param surface the given DrawSurface
     */
    @Override
    public void drawOn(DrawSurface surface) {
        //if there is no color, draw the ball with the main game theme colors of fire
        if (this.color == null) {
            Color[] fireColors = {new Color(255, 190, 40), new Color(255, 141, 36), new Color(255, 72, 11),
                    new Color(255, 12, 21)};
            int colorRadius = this.radius;
            for (Color c : fireColors) {
                surface.setColor(c);
                //cast to int because that's the type fillCircle can get
                surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), colorRadius);
                colorRadius -= 1;
            }
        } else {
            surface.setColor(this.color);
            //cast to int because that's the type fillCircle can get
            surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
            //draw a black outline on the edges of the ball
            surface.setColor(Color.BLACK);
            //cast to int because that's the type fillCircle can get
            surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
        }
    }
    /**
     * Set a velocity to a ball.
     * @param v a velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }
    /**
     * Set a velocity to a ball.
     * @param dx the change in position on the x axis.
     * @param dy the change in position on the y axis.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }
    /**
     * Return the velocity of the ball.
     * @return the velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }
    /**
     * Moves the ball one step according to his velocity.
     * Also checks if the ball next step will lead it to a collision,
     * if so, moves the ball center to the hit point and changes his
     * velocity to the new velocity after the hit.
     * Also checks if the ball is outside of the borders of the screen,
     * if so, brings it in.
     */
    public void moveOneStep() {
        //check if the ball is out of the borders
        this.isOutOfBorders();
        //x and y values after one step
        double newX = this.center.getX() + this.velocity.getDx();
        double newY = this.center.getY() + this.velocity.getDy();
        Point centerAfterMove = new Point(newX, newY);
        //a line from the current ball center to the new center after one step
        Line trajectory = new Line(this.center, centerAfterMove);
        //get the closest collision point of all the collidables in the game
        CollisionInfo collision = this.game.getClosestCollision(trajectory);
        //if it's null then there is no collision and move the ball one step
        if (collision == null) {
            this.center = this.getVelocity().applyToPoint(this.center);
        //if there is a collision
        } else {
            Point collisionPoint = collision.collisionPoint();
            Collidable collisionObject = collision.collisionObject();
            //move the ball center to "almost" the hit point
            this.center = this.hitLocation(collision);
            //the ball gets the new velocity after the hit
            this.velocity = collisionObject.hit(this, collisionPoint, this.velocity);
        }
    }
    /**
     * Return the point of the hit location with the ball radius difference.
     * @param collision the collision information
     * @return new location near the hit point
     */
    private Point hitLocation(CollisionInfo collision) {
        //get the rectangle and point of the collision
        Rectangle rect = collision.collisionObject().getCollisionRectangle();
        Point collisionPoint = collision.collisionPoint();
        double locationX = collisionPoint.getX();
        double locationY = collisionPoint.getY();
        //if the collision is on the right line of the rectangle
        if (rect.getRightLine().isInLineRange(collisionPoint)) {
            //adds the radius to the new location
            locationX += this.radius;
        }
        //if the collision is on the left line of the rectangle
        if (rect.getLeftLine().isInLineRange(collisionPoint)) {
            //subtracts the radius to the new location
            locationX -= this.radius;
        }
        //if the collision is on the up line of the rectangle
        if (rect.getUpLine().isInLineRange(collisionPoint)) {
            //subtracts the radius to the new location
            locationY -= this.radius;
        }
        //if the collision is on the down line of the rectangle
        if (rect.getDownLine().isInLineRange(collisionPoint)) {
            //adds the radius to the new location
            locationY += this.radius;
        }
        //returns the new location near the hit point
        return new Point(locationX, locationY);
    }
    /**
     * The time passed method of the sprites.Sprite interface.
     * It moves the ball one step ahead.
     */
    @Override
    public void timePassed() {
        this.moveOneStep();
    }
    /**
     * Adds the ball to the sprites list of the given game at the index 1
     * so he will be the first after the background sprite which is on index 0.
     * @param g a given game
     */
    public void addToGame(GameLevel g) {
        g.addSpriteByPlace(this, 1);
    }
    /**
     * Check if the ball is outside of the borders of the screen
     * (in case there are no blocks at the edges of the screen or the ball
     * was created outside of screen).
     * If so, brings the ball back in.
     */
    private void isOutOfBorders() {
        //get the fields for short writing
        double x = this.center.getX();
        double y = this.center.getY();
        int width = this.screenWidth;
        int thickness = this.bordersThickness;
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();

        /*
         * Check if the ball is outside of any of the borders.
         * If so, sets the ball location to the place he was before exiting
         * the border, and changes his velocity so he will move in.
         */
        //if outside of left border
        if (x < thickness) {
            this.center = new Point(thickness, y);
            dx *= -1;
            this.velocity = new Velocity(dx, dy);
        }
        //if outside of right border
        if (x > (width - thickness)) {
            this.center = new Point((width - thickness), y);
            dx *= -1;
            this.velocity = new Velocity(dx, dy);
        }
        //if outside of up border
        if (y < (thickness * 2)) {
            this.center = new Point(x, (thickness * 2));
            dy *= -1;
            this.velocity = new Velocity(dx, dy);
        }
    }
    /**
     * Remove this ball from a given game.
     * @param g a game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}