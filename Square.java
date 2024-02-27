import swiftbot.*;
import java.io.*;

public class Square {

    private SwiftBotAPI API;

    public Square(SwiftBotAPI api) {
        this.API = api;
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    public void drawSquare(int side) {
        try {
            // checks if the sides are between 15 and 85 or not
            if (side > 15 && side < 85) {

                System.out.println(" ");
                System.out.println(ANSI_GREEN + "<< Swiftbot Is Drawing a Square >>" + ANSI_RESET);

                int toMove = side * 10; // calculates the time the swiftbot should move for to draw the side

                for (int i = 0; i < 4; i++) {
                    API.move(100, 100, toMove);
                    API.move(0, 0, 1000);

                    // turns the swiftbot by 90 degrees exactly 3 times to complete the square
                    if (i < 3) {
                        API.move(100, -100, 400); // 400 seconds is 90 degrees
                    }

                    // sets the underlight to green when the drawing is finished
                    if (i == 3) {
                        int[] colourToLightUp = { 0, 0, 255 };
                        API.fillUnderlights(colourToLightUp);
                        API.disableUnderlights();

                        // calculates and prints the area of the square
                        int areaSquare = side * side;
                        System.out.println(ANSI_GREEN + "Area of this square is " + areaSquare + "cm sq" + ANSI_RESET);
                        System.out.println(" ");
                    }
                }
            } else {
                System.out.println(" ");
                System.out.println(ANSI_RED + "The Sides Should Be Between 15 and 85" + ANSI_RESET);
                System.out.println(" ");
            }
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error Occurred While Drawing Square: " + e.getMessage() + ANSI_RESET);
        }
    }
}
