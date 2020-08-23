package sprites;
import biuoop.DrawSurface;
import java.awt.Color;
import java.awt.Image;

/**
 * This is the class for Background that implements Sprite.
 */
public class Background implements Sprite {
    private Color color;
    private Image image;
    /**
     * Constructor for a new background if it's a background with a solid color.
     * @param color a given color
     */
    public Background(Color color) {
        this.color = color;
        //set the background image to null because it's a color only background
        this.image = null;
    }
    /**
     * Constructor for a new background if it's a background with a image.
     * @param image a given image
     */
    public Background(Image image) {
        this.image = image;
        //set the background color to null because it's a image only background
        this.color = null;
    }
    @Override
    public void drawOn(DrawSurface d) {
        //if there's a color to draw
        if (this.color != null) {
            d.setColor(this.color);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        //if there's an image to draw
        } else if (this.image != null) {
            d.drawImage(0, 0, this.image);
        }
    }
    /**
     * A timePassed method because background implements Sprite.
     * Currently we do nothing with this method because the background doesn't change over time.
     */
    @Override
    public void timePassed() {
    }
}