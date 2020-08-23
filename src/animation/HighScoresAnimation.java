package animation;
import biuoop.DrawSurface;
import scores.HighScoresTable;
import scores.ScoreInfo;
import sprites.Background;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is the class for HighScoresAnimation that implements Animation.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scores;
    private Background background;
    /**
     * The constructor for HighScoresAnimation.
     * @param scores a high score table
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
        //creates new background for this animation
        this.background = new Background(this.getBackgroundImage());
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        //draws the high score table background image if possible
        this.background.drawOn(d);
        int x = 240;
        int y = 100;
        String mainTitle = "High Scores";
        int mainTitleFont = 60;
        String playerTitle = "Player Name";
        int playerX = x - 100;
        int subTitleY = y + 80;
        String scoreTitle = "Score";
        int subTitleFont = 40;
        int scoreX = x + 325;
        d.setColor(Color.BLACK);
        d.drawText(x + 2, y, mainTitle, mainTitleFont);
        d.drawText(x - 2, y, mainTitle, mainTitleFont);
        d.drawText(x, y + 2, mainTitle, mainTitleFont);
        d.drawText(x, y - 2, mainTitle, mainTitleFont);
        d.setColor(Color.YELLOW);
        d.drawText(x, y, mainTitle, mainTitleFont);
        d.setColor(Color.BLACK);
        d.drawText(playerX, subTitleY, playerTitle, subTitleFont);
        d.drawText(scoreX, subTitleY, scoreTitle, subTitleFont);
        subTitleFont -= 10;
        d.setColor(new Color(167, 8, 0));
        //run on all the scores and draw them to the screen
        for (ScoreInfo s : this.scores.getHighScores()) {
            subTitleY += 50;
            String player = s.getName();
            String score = String.valueOf(s.getScore());
            d.drawText(playerX, subTitleY, player, subTitleFont);
            d.drawText(scoreX, subTitleY, score, subTitleFont);
        }
        d.setColor(Color.BLACK);
        d.drawText(250, 550, "Press space to continue", subTitleFont);
    }

    @Override
    public boolean shouldStop() {
        //this animation runs forever and does not wait for a key-press, so returns false as should stop
        return false;
    }
    /**
     * Returns the image for this animation background.
     * @return the high score table background image
     */
    private Image getBackgroundImage() {
        //this is the path for the high score background image
        String imageName = "background_images/high_scores_background.png";
        InputStream is = null;
        BufferedImage image = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
            if (is != null) {
                image = ImageIO.read(is);
            }
        } catch (IOException e) {
            System.err.println("Failed opening image");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.err.println("Failed closing image");
                }
            }
        }
        return image;
    }
}