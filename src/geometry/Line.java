package geometry;
import java.util.List;

/**
 * This is the class for Line.
 */
public class Line {
    //start and end points
    private Point startPoint;
    private Point endPoint;
    /**
     * First constructor for a new line.
     * Construct from 2 given points.
     * @param start the start point
     * @param end the end point
     */
    public Line(Point start, Point end) {
        this.startPoint = start;
        this.endPoint = end;
    }
    /**
     * Second constructor for a new line.
     * Construct from x and y for start point and x and y for end point.
     * @param x1 the x for the start point
     * @param y1 the y for the start point
     * @param x2 the x for the end point
     * @param y2 the y for the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.startPoint = new Point(x1, y1);
        this.endPoint = new Point(x2, y2);
    }
    /**
     * Return the length of the line.
     * @return the distance between the start and end point
     */
    public double length() {
        return this.startPoint.distance(endPoint);
    }
    /**
     * Calculates the middle point values of the line by summing the x and y
     * values of the start and end points, and then divides them by 2.
     * @return the middle point of the line
     */
    public Point middle() {
        double middleX = (this.startPoint.getX() + this.endPoint.getX()) / 2;
        double middleY = (this.startPoint.getY() + this.endPoint.getY()) / 2;
        return new Point(middleX, middleY);
    }
    /**
     * Return the start point of the line.
     * @return start point
     */
    public Point start() {
        return this.startPoint;
    }
    /**
     * Return the end point of the line.
     * @return end point
     */
    public Point end() {
        return this.endPoint;
    }
    /**
     * Return the biggest x value of the line.
     * @return biggest x
     */
    private double biggestX() {
        //if x of start is bigger or equal to x of end, return x of start
        if (this.startPoint.getX() >= this.endPoint.getX()) {
            return this.startPoint.getX();
        } else {
            //if x of start is smaller than x of end, return x of end
            return this.endPoint.getX();
        }
    }
    /**
     * Return the biggest y value of the line.
     * @return biggest y
     */
    private double biggestY() {
        //if y of start is bigger or equal to y of end, return y of start
        if (this.startPoint.getY() >= this.endPoint.getY()) {
            return this.startPoint.getY();
        } else {
            //if y of start is smaller than y of end, return y of end
            return this.endPoint.getY();
        }
    }
    /**
     * Return the smallest x value of the line.
     * @return smallest x
     */
    private double smallestX() {
        //if x of start is smaller or equal to x of end, return x of start
        if (this.startPoint.getX() <= this.endPoint.getX()) {
            return this.startPoint.getX();
        } else {
            //if x of start is bigger than x of end, return x of end
            return this.endPoint.getX();
        }
    }
    /**
     * Return the smallest y value of the line.
     * @return smallest y
     */
    private double smallestY() {
        //if y of start is smaller or equal to y of end, return y of start
        if (this.startPoint.getY() <= this.endPoint.getY()) {
            return this.startPoint.getY();
        } else {
            //if y of start is bigger than y of end, return y of end
            return this.endPoint.getY();
        }
    }
    /**
     * Checks if the point is in range of line by checking
     * if it's bigger than the smallest x/y of line,
     * and smaller than the biggest x/y of line.
     * @param p a point
     * @return true if the point is in the range of the line,
     * false if it's not
     */
    public boolean isInLineRange(Point p) {
        return (((p.getX() >= this.smallestX())
                && (p.getX() <= this.biggestX()))
                && ((p.getY() >= this.smallestY())
                && (p.getY() <= this.biggestY())));
    }
    /**
     * Calculates the slope of the line.
     * @return the line slope
     */
    private double slope() {
        //calculates the slope by the slope formula
        return ((this.startPoint.getY() - this.endPoint.getY())
                / (this.startPoint.getX() - this.endPoint.getX()));
    }
    /**
     * Check if the line is vertical.
     * @return true if vertical, false if not vertical
     */
    private boolean isVertical() {
        //vertical is when the line has the same x for start and end
        return (this.startPoint.getX() == this.endPoint.getX());
    }
    /**
     * If these 2 lines has same start or end point, returns that point.
     * @param other another line
     * @return the point if there is one, if not, returns null
     */
    private Point hasSamePoint(Line other) {
        //if start point equals to start or end point of other line
        if (this.startPoint.equals(other.start())
            || this.startPoint.equals(other.end())) {
            return this.startPoint;
        //if end point equals to start or end point of other line
        } else if (this.endPoint.equals(other.start())
                || this.endPoint.equals(other.end())) {
            return this.endPoint;
        //if there is no match
        } else {
            return null;
        }
    }
    /**
     * Return the x value of the intersection between these 2 lines as if
     * they were infinite lines that intersect for sure.
     * @param other another line
     * @return if one of the lines is vertical, returns his x value because
     * an infinite line will always meet a vertical infinite line.
     * if there is no vertical, returns the x of their intersection by
     * formula.
     */
    private double xOfIntersection(Line other) {
        //if there is a vertical line
        if (this.isVertical()) {
            return this.startPoint.getX();
        } else if (other.isVertical()) {
            return other.start().getX();
        //if there is no vertical line
        } else {

            /*
             * This is the formula for the x value of 2 infinite lines
             * intersection point, it is formed from rearranging and
             * isolating the x from the equation of
             * the first line equation = the second line equation.
             */
            return (((this.slope() * this.startPoint.getX())
                    - (other.slope() * other.start().getX())
                    + other.start().getY() - this.startPoint.getY())
                    / (this.slope() - other.slope()));
        }
    }
    /**
     * Return the y value of the intersection between these 2 lines as if
     * they were infinite lines that intersect for sure.
     * The y value is reached by placing the x value of intersection
     * in any of the lines equation.
     * @param other another line
     * @return if the first line is vertical, returns the y from the
     * other line equation, if any of the lines are horizontal, returns their
     * y as the intersection y because this will always be the y intersection,
     * if the other line is vertical or none is vertical,
     * returns the y from the first line equation.
     */
    private double yOfIntersection(Line other) {
        if (this.isVertical()) {
            //placing the x of intersection in other line equation
            return ((other.slope() * this.xOfIntersection(other))
                    - (other.slope() * other.startPoint.getX())
                    + other.startPoint.getY());
        } else {
            //if this line is horizontal returns his y
            if (this.slope() == 0) {
                return this.startPoint.getY();
            }
            //if other line is horizontal returns his y
            if (other.slope() == 0) {
                return other.start().getY();
            }
            //placing the x of intersection in first line equation
            return ((this.slope() * this.xOfIntersection(other))
                    - (this.slope() * this.startPoint.getX())
                    + this.startPoint.getY());
        }
    }
    /**
     * Check if these 2 lines intersect.
     * @param other another line
     * @return true if the lines intersect, false if they don't
     */
    public boolean isIntersecting(Line other) {
        //if the lines are the same line, they don't intersect
        if (this.equals(other)) {
            return false;
        }
        //if this line is a point and other line is vertical
        if (this.ifLineIsPoint() && other.isVertical()) {
            //check if the point is on other line
            return other.isInLineRange(this.startPoint);
        }
        //if other line is a point and this line is vertical
        if (other.ifLineIsPoint() && this.isVertical()) {
            //check if the point is on this line
            return this.isInLineRange(other.start());
        //if both lines are vertical
        } else if (this.isVertical() && other.isVertical()) {
            //return true if they have a meeting edge point, false otherwise
            return (this.hasSamePoint(other) != null);
        //if both lines have same slope
        } else if (this.slope() == other.slope()) {
            //return true if they have a meeting edge point, false otherwise
            return (this.hasSamePoint(other) != null);
        } else {
            //this point is the intersection point of the 2 lines
            //as if the 2 lines were infinite lines
            Point intersectionPoint = new Point(this.xOfIntersection(other),
                    this.yOfIntersection(other));
            //true if intersection point is in lines range, false otherwise
            return (this.isInLineRange(intersectionPoint)
                && other.isInLineRange(intersectionPoint));
        }
    }
    /**
     * Returns the intersection point of these 2 lines.
     * @param other another line
     * @return the intersection point if the lines intersect, null otherwise.
     */
    public Point intersectionWith(Line other) {
        //if they intersect
        if (this.isIntersecting(other)) {
            //if this line is a point and other is vertical, return that point
            if (this.ifLineIsPoint() && other.isVertical()) {
                return this.startPoint;
            }
            //if other line is a point and this is vertical, return that point
            if (other.ifLineIsPoint() && this.isVertical()) {
                return other.startPoint;
            }
            //if both are vertical
            if (this.isVertical() && other.isVertical()) {
                //return their meeting edge point
                return this.hasSamePoint(other);
            //if both lines have same slope
            } else if (this.slope() == other.slope()) {
                //return their meeting edge point
                return this.hasSamePoint(other);
            } else {
                //return a point with x and y values of intersection
                return new Point(this.xOfIntersection(other),
                        this.yOfIntersection(other));
            }
        //if they don't intersect
        } else {
            return null;
        }
    }
    /**
     * Check if line is a point (if start point = end point).
     * @return true if the line is a point, false otherwise
     */
    private boolean ifLineIsPoint() {
        return (this.startPoint.equals(this.endPoint));
    }
    /**
     * Check if these 2 lines are equal.
     * @param other another line
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        //check if start1=start2 and end1=end2 or start1=end2 and end1=start2
        return ((this.startPoint.equals(other.start())
                && this.endPoint.equals(other.end()))
                || (this.startPoint.equals(other.end())
                && this.endPoint.equals(other.start())));
    }
    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     * @param rect a rectangle
     * @return closest intersection point to the start of the line, or null
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        //get a list of intersection points of the rectangle with this line
        List<Point> list = rect.intersectionPoints(this);
        //if there are no intersections, return null
        if (list.isEmpty()) {
            return null;
        //if there are intersection points, finds the closest one
        } else {
            //starts with the first point
            Point closestPoint = list.get(0);
            //runs on the list
            for (Point p : list) {
                //if this is the same point
                if (closestPoint.equals(p)) {
                    continue;
                }
                //distance between the start point to the current closest point
                double oldDistance = closestPoint.distance(this.startPoint);
                //distance between the start point to the current point in list
                double newDistance = p.distance(this.startPoint);
                //if the point in list is closer than the current closest
                if (newDistance < oldDistance) {
                    //point becomes closest
                    closestPoint = p;
                }
            }
            return closestPoint;
        }
    }
}