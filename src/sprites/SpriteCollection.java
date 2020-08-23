package sprites;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;

/**
 * This is the class for SpriteCollection.
 */
public class SpriteCollection {
    //list of sprites
    private List<Sprite> sprites;
    /**
     * Constructor for sprite collection, make a new list of sprites.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }
    /**
     * Adds a sprite to the sprites list.
     * @param s a sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }
    /**
     * Adds a sprite to the sprites list in a given specific index.
     * @param s a sprite
     * @param index a given index
     */
    public void addSpriteByPlace(Sprite s, int index) {
        //add the given sprite to the given index
        this.sprites.add(index, s);
    }
    /**
     * Remove the given sprite from the sprites list.
     * @param s a sprite object
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
    /**
     * Call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }
    /**
     * Call drawOn(d) on all sprites.
     * @param d a draw surface
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.drawOn(d);
        }
    }
}