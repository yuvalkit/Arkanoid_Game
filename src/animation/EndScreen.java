package animation;
import biuoop.DrawSurface;
import other.Counter;
import sprites.Background;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is the class for EndScreen that implements Animation.
 */
public class EndScreen implements Animation {
    private Counter score;
    private boolean didWin;
    private Background winBackground;
    private Background loseBackground;
    /**
     * The constructor for a EndScreen.
     * @param score the score counter
     * @param didWin a flag indicates if the played won or lost
     */
    public EndScreen(Counter score, boolean didWin) {
        this.score = score;
        this.didWin = didWin;
        //creates new backgrounds for winning and for losing
        this.winBackground = new Background(this.getWinBackgroundImage());
        this.loseBackground = new Background(this.getLoseBackgroundImage());
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        int x;
        int y;
        int font;
        String mainTitle;
        String subTitle = "Your score is " + this.score.getValue();
        String spaceTitle = "Press space to continue";
        Color shadowColor;
        Color titleColor;
        //if the player won
        if (this.didWin) {
            //draws the winning image if possible
            this.winBackground.drawOn(d);
            shadowColor = new Color(231, 172, 0);
            titleColor = Color.YELLOW;
            mainTitle = "You Win!";
            x = (d.getWidth() / 6) + 20;
            y = d.getHeight() / 3;
            font = 120;
            d.setColor(shadowColor);
            d.drawText(x + 3, y, mainTitle, font);
            d.drawText(x - 3, y, mainTitle, font);
            d.drawText(x, y + 3, mainTitle, font);
            d.drawText(x, y - 3, mainTitle, font);
            d.setColor(titleColor);
            d.drawText(x, y, mainTitle, font);
            font = 50;
            x += 42;
            y += 80;
            d.setColor(shadowColor);
            d.drawText(x + 1, y, subTitle, font);
            d.drawText(x - 1, y, subTitle, font);
            d.drawText(x, y + 1, subTitle, font);
            d.drawText(x, y - 1, subTitle, font);
            d.setColor(titleColor);
            d.drawText(x, y, subTitle, font);
            x += 50;
            y += 250;
            font = 30;
            d.drawText(x, y, spaceTitle, font);
        //if the player lost
        } else {
            //draws the losing image if possible
            this.loseBackground.drawOn(d);
            shadowColor = new Color(133, 0, 6);
            titleColor = new Color(200, 0, 7);
            mainTitle = "Game Over";
            x = (d.getWidth() / 6);
            y = (d.getHeight() / 2) - 20;
            font = 100;
            d.setColor(shadowColor);
            d.drawText(x + 3, y, mainTitle, font);
            d.drawText(x - 3, y, mainTitle, font);
            d.drawText(x, y + 3, mainTitle, font);
            d.drawText(x, y - 3, mainTitle, font);
            d.setColor(titleColor);
            d.drawText(x, y, mainTitle, font);
            font = 50;
            x += 60;
            y += 70;
            d.setColor(shadowColor);
            d.drawText(x + 1, y, subTitle, font);
            d.drawText(x - 1, y, subTitle, font);
            d.drawText(x, y + 1, subTitle, font);
            d.drawText(x, y - 1, subTitle, font);
            d.setColor(titleColor);
            d.drawText(x, y, subTitle, font);
            x += 45;
            y += 200;
            font = 30;
            d.drawText(x, y, spaceTitle, font);
        }
    }
    @Override
    public boolean shouldStop() {
        //this animation runs forever and does not wait for a key-press, so returns false as should stop
        return false;
    }
    /**
     * Returns the image for winning.
     * @return the "you win" background image
     */
    private Image getWinBackgroundImage() {
        //this is the path for the winning image
        String imageName = "background_images/you_win_background.png";
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
    /**
     * Returns the image for losing.
     * @return the "game over" background image
     */
    private Image getLoseBackgroundImage() {
        //this is the path for the losing image
        String imageName = "background_images/game_over_background.png";
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