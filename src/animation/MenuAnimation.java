package animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprites.Background;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the class for MenuAnimation that implements Menu.
 * @param <T> a given parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    //fields
    private String title;
    private KeyboardSensor keyboardSensor;
    private List<String> selectionKeys;
    private List<String> selectionTitles;
    private List<T> selectionValues;
    private Map<String, Menu<T>> subMenusMap;
    private T status;
    private boolean stop;
    private AnimationRunner runner;
    private Background background;
    private boolean isAlreadyPressed;
    /**
     * The constructor for a MenuAnimation.
     * @param title the title of this menu
     * @param keyboardSensor a keyboard sensor for the menu
     * @param runner an animation runner for the menu
     */
    public MenuAnimation(String title, KeyboardSensor keyboardSensor, AnimationRunner runner) {
        this.title = title;
        this.keyboardSensor = keyboardSensor;
        this.runner = runner;
        this.selectionKeys = new ArrayList<>();
        this.selectionTitles = new ArrayList<>();
        this.selectionValues = new ArrayList<>();
        this.subMenusMap = new HashMap<>();
        //clear the selection so the menu will be set to new
        this.clearSelection();
        //creates new background for this menu
        this.background = new Background(this.getBackgroundImage());
    }
    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.selectionKeys.add(key);
        this.selectionTitles.add(message);
        this.selectionValues.add(returnVal);
    }
    @Override
    public T getStatus() {
        return this.status;
    }
    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.selectionKeys.add(key);
        this.selectionTitles.add(message);
        //there is no returned value so adds a "null" to the selection values
        this.selectionValues.add(null);
        //adds this sub menu to the sub menus map with the given key
        this.subMenusMap.put(key, subMenu);
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        //draws this menu background if possible
        this.background.drawOn(d);
        int x = 50;
        int y = 100;
        int changedX;
        int changedY;
        int font = 40;
        //set colors and draws the menu title
        Color[] titleColors = {new Color(216, 38, 20), new Color(216, 74, 0), new Color(253, 175, 0)};
        d.setColor(titleColors[0]);
        d.drawText(x + 2, y, this.title, font);
        d.drawText(x - 2, y, this.title, font);
        d.drawText(x, y + 2, this.title, font);
        d.drawText(x, y - 2, this.title, font);
        d.setColor(titleColors[2]);
        d.drawText(x, y, this.title, font);
        y += 20;
        x += 30;
        //run on all the selections and draw them
        for (int i = 0; i < this.selectionTitles.size(); i++) {
            y += 50;
            changedX = x;
            changedY = y;
            String tempTitle = "(" + this.selectionKeys.get(i) + ") " + this.selectionTitles.get(i);
            for (Color color : titleColors) {
                d.setColor(color);
                d.drawText(changedX, changedY, tempTitle, font);
                changedX++;
                changedY++;
            }
        }
        //run on all the selections
        for (int j = 0; j < this.selectionValues.size(); j++) {
            //the key of the selection
            String key = this.selectionKeys.get(j);
            //if this key was pressed
            if (this.keyboardSensor.isPressed(key)) {
                //if the key was pressed before the animation started, we ignore that key press
                if (this.isAlreadyPressed) {
                    return;
                }
                //if this key is for a sub menu
                if (this.subMenusMap.containsKey(key)) {
                    //creates new sub menu from the sub menu of the key
                    Menu<T> subMenu = this.subMenusMap.get(key);
                    //runs the sub menu
                    this.runner.run(subMenu);
                    //gets the sub menu status as this menu status
                    this.status = subMenu.getStatus();
                    //clears the selection for the sub menu
                    subMenu.clearSelection();
                //if this key is not for a sub menu
                } else {
                    //the status is the returned value of the selection
                    this.status = this.selectionValues.get(j);
                }
                //should stop now and breaks
                this.stop = true;
                break;
            }
        }
        //flag becomes false now
        this.isAlreadyPressed = false;
    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
    @Override
    public void clearSelection() {
        //clears the status and should not stop now, also set the safety flag for previous pressing to true
        this.status = null;
        this.stop =  false;
        this.isAlreadyPressed = true;
    }
    /**
     * Returns the image for this menu background.
     * @return the menu background image
     */
    private Image getBackgroundImage() {
        //this is the path for the menu background image
        String imageName = "background_images/menu_background.png";
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