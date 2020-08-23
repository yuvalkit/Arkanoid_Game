package animation;
import biuoop.DrawSurface;
import sprites.Background;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is the class for PauseScreen that implements Animation.
 */
public class PauseScreen implements Animation {
    private Background background;
    /**
     * The constructor for a PauseScreen.
     */
    public PauseScreen() {
        //creates new background for this pause screen
        this.background = new Background(this.getPauseScreenImage());
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        //draws the pause screen background if possible
        this.background.drawOn(d);
        int font = 40;
        int x = 110;
        int y = 50;
        String title = "paused - press space to continue";
        d.setColor(Color.WHITE);
        d.drawText(x + 1, y, title, font);
        d.drawText(x - 1, y, title, font);
        d.drawText(x, y + 1, title, font);
        d.drawText(x, y - 1, title, font);
        d.setColor(new Color(25, 133, 255));
        d.drawText(x, y, title, font);
    }
    @Override
    public boolean shouldStop() {
        //this animation runs forever and does not wait for a key-press, so returns false as should stop
        return false;
    }
    /**
     * Returns the background image for this pause menu.
     * @return the menu background image
     */
    private Image getPauseScreenImage() {
        //this is the path for the pause menu background image
        String imageName = "background_images/pause_background.jpg";
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