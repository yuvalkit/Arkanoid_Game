package other;
import geometry.Point;

/**
 * This is the class for Velocity.
 */
public class Velocity {
    //the change in position on the x and y axes
    private double dx;
    private double dy;
    /**
     * The constructor for a new velocity.
     * @param dx the change on x axis
     * @param dy the change on y axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    /**
     * Take a point with position (x,y) and return a new point
     * with position (x+dx, y+dy).
     * @param p a point
     * @return the point after the change on axes
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }
    /**
     * Return the x axis change value.
     * @return x axis change value
     */
    public double getDx() {
        return this.dx;
    }
    /**
     * Return the y axis change value.
     * @return y axis change value
     */
    public double getDy() {
        return this.dy;
    }
    /**
     * Calculates the velocity dx and dy values from angle and speed
     * by using the formula to find the x and y axes change with angle
     * and hypotenuse.
     * The calculations are based on the fact that angle 0 means up movement,
     * and that y axis is upside down in the GUI screen.
     * @param angle the angle of the movement
     * @param speed the speed of the movement
     * @return the velocity from the angle and speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        //change the angle to radians because Math.sin needs radian value
        double dx = speed * Math.sin(Math.toRadians(angle));
        //multiplies by -1 because the y axis is upside down in the GUI.
        double dy = -1 * speed * Math.cos(Math.toRadians(angle));
        return new Velocity(dx, dy);
    }
}