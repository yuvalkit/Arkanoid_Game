package sprites;
import collision.Collidable;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import listeners.HitNotifier;
import listeners.HitListener;
import other.Velocity;
import biuoop.DrawSurface;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the class for Block that implements Collidable, Sprite and HitNotifier.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    //fields
    private Rectangle shape;
    private java.awt.Color color;
    private int hitPoints;
    private Color stroke;
    private Map<Integer, Color> fillColors;
    private Map<Integer, Image> fillImages;
    private List<HitListener> hitListeners;
    /**
     * Constructor for a new block.
     * @param upperLeftX the x value of the top left point of the block
     * @param upperLeftY the y value of the top left point of the block
     * @param width the width of the block
     * @param height the height of the block
     * @param hitPoints the amount of hit points of the block
     * @param stroke the stroke of the block
     */
    public Block(double upperLeftX, double upperLeftY, double width, double height, int hitPoints, Color stroke) {
        Point upperLeft = new Point(upperLeftX, upperLeftY);
        this.shape = new Rectangle(upperLeft, width, height);
        this.hitPoints = hitPoints;
        this.stroke = stroke;
        this.hitListeners = new ArrayList<>();
    }
    /**
     * Set the block fill maps.
     * @param colors the colors fill map
     * @param images the images fill map
     */
    public void setBlockFills(Map<Integer, Color> colors, Map<Integer, Image> images) {
        this.fillColors = colors;
        this.fillImages = images;
    }
    /**
     * Set one color for the block.
     * @param oneColor a given color
     */
    public void setOneColor(Color oneColor) {
        this.fillColors = new HashMap<>();
        this.fillImages = new HashMap<>();
        //put the color in the first index of the colors map
        this.fillColors.put(0, oneColor);
    }
    /**
     * Set one image for the block.
     * @param oneImage a given image
     */
    public void setOneImage(Image oneImage) {
        this.fillColors = new HashMap<>();
        this.fillImages = new HashMap<>();
        //put the image in the first index of the images map
        this.fillImages.put(0, oneImage);
    }
    @Override
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }
    /**
     * Return the number of hit points of this block.
     * @return number of hit points
     */
    public int getHitPoints() {
        return this.hitPoints;
    }
    /**
     * Return the width of the block.
     * @return the block width
     */
    public double getWidth() {
        return this.shape.getWidth();
    }
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //get dx and dy of velocity
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        //if the collision happened on the right or left sides of the shape
        if (this.shape.getRightLine().isInLineRange(collisionPoint)
                || this.shape.getLeftLine().isInLineRange(collisionPoint)) {
            //set dx to opposite x axis direction
            dx *= -1;
        }
        //if the collision happened on the up or down sides of the shape
        if (this.shape.getUpLine().isInLineRange(collisionPoint)
                || this.shape.getDownLine().isInLineRange(collisionPoint)) {
            //set dy to opposite y axis direction
            dy *= -1;
        }
        //if this block still has hit points
        if (this.hitPoints > 0) {
            //reduce the amount by 1 because it was hit
            this.hitPoints--;
        }
        this.notifyHit(hitter);
        return new Velocity(dx, dy);
    }
    /**
     * Draws the block on the given DrawSurface.
     * @param d the given DrawSurface
     */
    @Override
    public void drawOn(DrawSurface d)  {
        //get the block shape details
        int upLeftX = (int) this.shape.getUpperLeft().getX();
        int upLeftY = (int) this.shape.getUpperLeft().getY();
        int width = (int) this.shape.getWidth();
        int height = (int) this.shape.getHeight();
        //get the default color and images
        Color defaultColor = this.fillColors.get(0);
        Image defaultImage = this.fillImages.get(0);
        //get the color and image for the current hit points
        Color tempColor = this.fillColors.get(this.hitPoints);
        Image image = this.fillImages.get(this.hitPoints);
        //if can draw the color
        if (tempColor != null) {
            d.setColor(tempColor);
            d.fillRectangle(upLeftX, upLeftY, width, height);
        //if can draw the image
        } else if (image != null) {
            d.drawImage(upLeftX, upLeftY, image);
        //if can't draw color or image
        } else {
            //if there is a default image
            if (defaultImage != null) {
                d.drawImage(upLeftX, upLeftY, defaultImage);
            //draw the default color
            } else {
                d.setColor(defaultColor);
                d.fillRectangle(upLeftX, upLeftY, width, height);
            }
        }
        //if there is a stroke to draw
        if (this.stroke != null) {
            d.setColor(this.stroke);
            d.drawRectangle(upLeftX, upLeftY, width, height);
        }
    }
    /**
     * A timePassed method for the block because he implements Sprite.
     * Currently we do nothing with this method.
     */
    @Override
    public void timePassed() {
    }
    /**
     * Adds this block to the collidables and sprites lists of the given game.
     * @param g a given game
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
    /**
     * Notifies all the listeners of this block about a hit event with a given hitter.
     * @param hitter a ball that hitted that block.
     */
    private void notifyHit(Ball hitter) {
        //make a copy of the hitListeners before iterating over them
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        //notify all listeners about a hit event
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    /**
     * Remove this block from a given gameLevel.
     * @param gameLevel a gameLevel
     */
    public void removeFromGame(GameLevel gameLevel) {
        //remove from collidables and sprites lists
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }
}