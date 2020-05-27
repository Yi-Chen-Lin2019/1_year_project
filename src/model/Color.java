package model;
import java.awt.*;

public class Color extends Object {
    public static Color BLACK;
    public static Color GREEN;
    public static Color RED;
    public static Color PINK;
    public static Color BLUE;
    public static Color YELLOW;
    public static Color colour;

    public Color() {
       
    }

    public static Color getColour(String col) {

        switch(col.toLowerCase()) {
            case "black":
                colour = Color.BLACK;
            case "green":
                colour = Color.GREEN;
            case "red":
                colour = Color.RED;
            case "pink":
                colour = Color.PINK;
            case "blue":
                colour = Color.BLUE;
            case "yellow":
                colour = Color.YELLOW;    
        }
        return colour;
    }
    
}