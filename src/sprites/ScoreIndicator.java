package sprites;
import game.GameLevel;
import other.Counter;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * This is the class for ScoreIndicator that implements Sprite.
 */
public class ScoreIndicator implements Sprite {
    //fields for score counter and width of the screen
    private Counter score;
    private int width;
    //font parameters
    private int fontSize;
    private int textY;
    /**
     * The constructor for a new score indicator.
     * @param score a score counter
     * @param width a width of a screen
     * @param fontSize the font size
     * @param textY the y value of the text
     */
    public ScoreIndicator(Counter score, int width, int fontSize, int textY) {
        this.score = score;
        this.width = width;
        this.fontSize = fontSize;
        this.textY = textY;
    }
    @Override
    public void drawOn(DrawSurface d) {
        String title = "Score:";
        String scoreValue = String.valueOf(this.score.getValue());
        d.setColor(Color.WHITE);
        //set the x of the score indicator title to be around the middle of the screen next to the lives indicator
        //these are the calculations to do so
        int x = (this.width / 15) * 6;
        //draw the title
        d.drawText(x, this.textY, title, this.fontSize);
        x += 45;
        d.setColor(Color.YELLOW);
        d.drawText(x, this.textY, scoreValue, this.fontSize);
    }
    /**
     * A timePassed method for the ScoreIndicator because he implements Sprite.
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