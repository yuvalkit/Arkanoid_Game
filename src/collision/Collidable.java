package collision;
import geometry.Point;
import geometry.Rectangle;
import other.Velocity;
import sprites.Ball;

/**
 * This is the interface for Collidable.
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     * @return the shape
     */
    Rectangle getCollisionRectangle();
    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * the return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     * @param hitter the ball that makes the hit
     * @param collisionPoint the collision point
     * @param currentVelocity current velocity of the object
     * @return new velocity for the object
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}