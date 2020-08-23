package geometry;

/**
 * This is the class for Point.
 */
public class Point {
    //x value of the point
    private double xValue;
    //y value of the point
    private double yValue;
    /**
     * This is the constructor for a new point.
     * @param x the x value
     * @param y the y value
     */
    public Point(double x, double y) {
        this.xValue = x;
        this.yValue = y;
    }
    /**
     * Calculates the distance between this point to other point.
     * @param other another point
     * @return the distance
     */
    public double distance(Point other) {
        //does the formula for distance between 2 points
        return Math.sqrt(((this.xValue - other.xValue)
                * (this.xValue - other.xValue))
                + ((this.yValue - other.yValue)
                * (this.yValue - other.yValue)));
    }
    /**
     * Return true if the points are equal, false otherwise.
     * @param other another point
     * @return true or false
     */
    public boolean equals(Point other) {
        //check if both x and y are equal
        return ((this.xValue == other.xValue)
                && (this.yValue == other.yValue));
    }
    /**
     * Return the x value of this point.
     * @return x value
     */
    public double getX() {
        return this.xValue;
    }
    /**
     * Return the y value of this point.
     * @return y value
     */
    public double getY() {
        return this.yValue;
    }
}