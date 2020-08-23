package sprites;
import biuoop.DrawSurface;
import game.GameLevel;
import java.awt.Color;

/**
 * This is the class for NameIndicator that implements Sprite.
 */
public class NameIndicator implements Sprite {
    private String name;
    private int width;
    //font parameters
    private int fontSize;
    private int textY;
    /**
     * The constructor for a new name indicator.
     * @param name a name string
     * @param width a width of a screen
     * @param fontSize the font size
     * @param textY the y value of the text
     */
    public NameIndicator(String name, int width, int fontSize, int textY) {
        this.name = name;
        this.width = width;
        this.fontSize = fontSize;
        this.textY = textY;
    }
    @Override
    public void drawOn(DrawSurface d) {
        String title = "Level Name:";
        d.setColor(Color.WHITE);
        int x = (this.width / 15) * 10;
        d.drawText(x, this.textY, title, this.fontSize);
        x += 85;
        d.setColor(new Color(0, 160, 255));
        d.drawText(x, this.textY, this.name, this.fontSize);
    }
    /**
     * A timePassed method for the NameIndicator because he implements Sprite.
     * Currently we do nothing with this method.
     */
    @Override
    public void timePassed() {
    }
    /**
     * Add the score indicator to the sprites list of the given game.
     * @param g a given game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}