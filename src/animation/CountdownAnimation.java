package animation;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.SpriteCollection;
import java.awt.Color;

/**
 * This is the class for CountdownAnimation that implements Animation.
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before
 * it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private int currentCount;
    private SpriteCollection gameScreen;
    private boolean stop;
    /**
     * The constructor for a CountdownAnimation.
     * @param numOfSeconds the amount of seconds the counting should last
     * @param countFrom the number to count down from
     * @param gameScreen the sprites collection of the current game
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        //a current counting that starts from the countFrom
        this.currentCount = countFrom;
        this.gameScreen = gameScreen;
        //the stop flag starts as false
        this.stop = false;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        //new sleeper
        Sleeper sleeper = new biuoop.Sleeper();
        //calculates the appear time of each number, multiplies by 1000 to convert from seconds to milliseconds
        long appearTime = (long) (1000 * (this.numOfSeconds / this.countFrom));
        //sleep only after the first number, because we want to sleep when already in the game
        if (this.currentCount < this.countFrom) {
            //sleep
            sleeper.sleepFor(appearTime);
        }
        //when the count is still more than 0
        if (this.currentCount != 0) {
            //draw to screen the sprites collection of the game
            this.gameScreen.drawAllOn(d);
            //set to red
            d.setColor(Color.RED);
            int font = 70;
            String sign;
            int x = (d.getWidth() / 2);
            int y = (d.getHeight() / 2);
            //if this is the last count
            if (this.currentCount == 1) {
                //draw "GO!" instead of a number
                sign = "GO!";
                x -= 45;
            } else {
                //draw the current count number minus 1 because of the "GO!" change
                sign = Integer.toString(this.currentCount - 1);
            }
            d.setColor(new Color(216, 38, 20));
            d.drawText(x + 2, y, sign, font);
            d.drawText(x - 2, y, sign, font);
            d.drawText(x, y + 2, sign, font);
            d.drawText(x, y - 2, sign, font);
            d.setColor(new Color(253, 196, 0));
            d.drawText(x, y, sign, font);
        }
        //the count drops by 1
        this.currentCount--;
        //if the count is below 0, it means that the counting is finished
        if (this.currentCount < 0) {
            //should stop now
            this.stop = true;
        }
    }
    @Override
    public boolean shouldStop() {
        //returns the stop flag
        return this.stop;
    }
}