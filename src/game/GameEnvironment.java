package game;
import collision.Collidable;
import collision.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import java.util.List;
import java.util.ArrayList;

/**
 * This is the class for GameEnvironment.
 */
public class GameEnvironment {
    //list of collidables
    private List<Collidable> collidables;
    /**
     * Constructor for a game environment, make a new list of collidables.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }
    /**
     * Add the given collidable to the environment.
     * @param c a collidable object
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }
    /**
     * Remove the given collidable from the environment.
     * @param c a collidable object
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }
    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory the line path of the object from start to end
     * @return information about the closest collision, null if there's none
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        //start of line
        Point lineStart = trajectory.start();
        //get the first collision as the closest to begin with
        CollisionInfo closestCollision = this.getFirstCollision(trajectory);
        //if no collision was found
        if (closestCollision == null) {
            return null;
        }
        //make a copy of the collidables list to avoid concurrent errors
        List<Collidable> collidablesCopy = new ArrayList<>(this.collidables);
        //runs on all the collidables in the list
        for (Collidable c : collidablesCopy) {
            //get the shape of c
            Rectangle rect = c.getCollisionRectangle();
            //get the point of the current closest collision
            Point oldCollisionPoint = closestCollision.collisionPoint();
            //get the point of collision between the trajectory and
            //the current collidable
            Point newCollisionPoint =
                    trajectory.closestIntersectionToStartOfLine(rect);
            //if there is a collision
            if (newCollisionPoint != null) {
                //continue if it's the same collision point
                if (oldCollisionPoint.equals(newCollisionPoint)) {
                    continue;
                }
                //get the distance between start of line and collision points
                double oldDistance = lineStart.distance(oldCollisionPoint);
                double newDistance = lineStart.distance(newCollisionPoint);
                //if the current collision is closer than the current closest
                if (newDistance < oldDistance) {
                    //current collision becomes the closest collision
                    closestCollision = new CollisionInfo(newCollisionPoint, c);
                }
            }
        }
        return closestCollision;
    }
    /**
     * Returns the first collision there is with a collidable from the
     * collidables list, return null if there is none.
     * @param line a line
     * @return information about the first collision, null if there's none
     */
    private CollisionInfo getFirstCollision(Line line) {
        //make a copy of the collidables list to avoid concurrent errors
        List<Collidable> collidablesCopy = new ArrayList<>(this.collidables);
        //if there are no collidables in the list
        if (collidablesCopy.isEmpty()) {
            return null;
        }
        //runs on all the collidables in the list
        for (Collidable c : collidablesCopy) {
            //get the shape of c
            Rectangle rect = c.getCollisionRectangle();
            //get the collision point between the line and c shape
            Point collisionPoint = line.closestIntersectionToStartOfLine(rect);
            //if there is a collision, return the information of it
            if (collisionPoint != null) {
                return new CollisionInfo(collisionPoint, c);
            }
        }
        //if no collision was found
        return null;
    }
}