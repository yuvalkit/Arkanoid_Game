package other;
import java.awt.Color;

/**
 * This is the class for ColorsParser.
 */
public class ColorsParser {
    /**
     * Parse color definition and return the specified color.
     * @param s a string that represents some kind of a color
     * @return a color
     */
    public java.awt.Color colorFromString(String s) {
        //if its a color made from RGB parameters
        if (s.startsWith("RGB")) {
            s = s.replaceAll("RGB", "");
            String[] parameters = s.split(",");
            int r = Integer.parseInt(parameters[0]);
            int g = Integer.parseInt(parameters[1]);
            int b = Integer.parseInt(parameters[2]);
            //return a color with the RGB parameters
            return new Color(r, g, b);
        //if its a name of a color
        } else {
            switch(s) {
                case "black":
                    return Color.BLACK;
                case "blue":
                    return Color.BLUE;
                case "cyan":
                    return Color.CYAN;
                case "gray":
                    return Color.GRAY;
                case "lightGray":
                    return Color.LIGHT_GRAY;
                case "green":
                    return Color.GREEN;
                case "orange":
                    return Color.ORANGE;
                case "pink":
                    return Color.PINK;
                case "red":
                    return Color.RED;
                case "white":
                    return Color.WHITE;
                case "yellow":
                    return Color.YELLOW;
                default:
                    return null;
            }
        }
    }
}