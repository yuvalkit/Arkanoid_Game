package collision;
import geometry.Point;

/**
 * This is the class for CollisionInfo.
 */
public class CollisionInfo {
    //the point at which the collision occurs
    private Point collisionPoint;
    //the collidable object involved in the collision
    private Collidable collisionObject;
    /**
     * Constructor for a new collision information.
     * @param collisionPoint the point at which the collision occurs
     * @param collisionObject the collidable object involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }
    /**
     * Return the collision point.
     * @return collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }
    /**
     * Return the collision object.
     * @return collision object
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}