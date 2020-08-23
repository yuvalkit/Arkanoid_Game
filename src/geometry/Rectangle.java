package geometry;
import java.util.List;
import java.util.ArrayList;

/**
 * This is the class for Rectangle.
 */
public class Rectangle {
    //the fields of a rectangle
    private Point upperLeft;
    private double width;
    private double height;
    //the 4 sides lines of the rectangle
    private Line rightLine;
    private Line leftLine;
    private Line upLine;
    private Line downLine;
    /**
     * Constructor for a new rectangle.
     * creates a new rectangle with location and width/height.
     * @param upperLeft the top left point of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        double upLeftX = upperLeft.getX();
        double upLeftY = upperLeft.getY();
        //make the other 3 corner points of the rectangle
        Point upperRight = new Point((upLeftX + width), upLeftY);
        Point lowerLeft = new Point(upLeftX, upLeftY + height);
        Point lowerRight = new Point((upLeftX + width), (upLeftY + height));
        //make a line for each of the 4 rectangle sides
        this.rightLine = new Line(upperRight, lowerRight);
        this.leftLine = new Line(upperLeft, lowerLeft);
        this.upLine = new Line(upperLeft, upperRight);
        this.downLine = new Line(lowerLeft, lowerRight);
    }
    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     * @param line a line
     * @return a list of intersection points, may be empty
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        //make list of points
        List<Point> intersections = new ArrayList<>();
        //if there's an intersection on the right line, adds that point to list
        if (line.isIntersecting(this.rightLine)) {
            intersections.add(line.intersectionWith(this.rightLine));
        }
        //if there's an intersection on the left line, adds that point to list
        if (line.isIntersecting(this.leftLine)) {
            intersections.add(line.intersectionWith(this.leftLine));
        }
        //if there's an intersection on the up line, adds that point to list
        if (line.isIntersecting(this.upLine)) {
            intersections.add(line.intersectionWith(this.upLine));
        }
        //if there's an intersection on the down line, adds that point to list
        if (line.isIntersecting(this.downLine)) {
            intersections.add(line.intersectionWith(this.downLine));
        }
        //return the list of intersection points
        return intersections;
    }
    /**
     * Return the width of the rectangle.
     * @return width
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * Return the height of the rectangle.
     * @return height
     */
    public double getHeight() {
        return this.height;
    }
    /**
     * Return the upper left point of the rectangle.
     * @return upper left point
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
    /**
     * Return the right line of the rectangle.
     * @return right line
     */
    public Line getRightLine() {
        return this.rightLine;
    }
    /**
     * Return the left line of the rectangle.
     * @return left line
     */
    public Line getLeftLine() {
        return this.leftLine;
    }
    /**
     * Return the up line of the rectangle.
     * @return up line
     */
    public Line getUpLine() {
        return this.upLine;
    }
    /**
     * Return the down line of the rectangle.
     * @return down line
     */
    public Line getDownLine() {
        return this.downLine;
    }
}