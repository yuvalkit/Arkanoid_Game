package sprites;
import game.GameLevel;
import other.Counter;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * This is the class for LivesIndicator that implements Sprite.
 */
public class LivesIndicator implements Sprite {
    //fields for lives counter and width of the screen
    private Counter lives;
    private int width;
    //font parameters
    private int fontSize;
    private int textY;
    /**
     * The constructor for a new lives indicator.
     * @param lives a lives counter
     * @param width a width of a screen
     * @param fontSize the font size
     * @param textY the y value of the text
     */
    public LivesIndicator(Counter lives, int width, int fontSize, int textY) {
        this.lives = lives;
        this.width = width;
        this.fontSize = fontSize;
        this.textY = textY;
    }
    @Override
    public void drawOn(DrawSurface d) {
        String title = "Lives:";
        String livesValue = String.valueOf(this.lives.getValue());
        d.setColor(Color.WHITE);
        int x = (this.width / 15) * 2;
        d.drawText(x, this.textY, title, this.fontSize);
        x += 40;
        Color color;
        if (this.lives.getValue() > 4) {
            color = Color.GREEN;
        } else if (this.lives.getValue() > 2) {
            color = Color.ORANGE;
        } else {
            color = Color.RED;
        }
        d.setColor(color);
        d.drawText(x, this.textY, livesValue, this.fontSize);
    }
    /**
     * A timePassed method for the LivesIndicator because he implements Sprite.
     * Currently we do nothing with this method.
     */
    @Override
    public void timePassed() {
    }
    /**
     * Add the lives indicator to the sprites list of the given game.
     * @param g a given game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}