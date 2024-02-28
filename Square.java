import swiftbot.*;
import java.io.*;

public class Square {

    private SwiftBotAPI API;

    // Constructor to initialize Square object with SwiftBotAPI
    public Square(SwiftBotAPI api) {
        this.API = api;
    }

    // ANSI color codes for colored output
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    // Method to draw a square with given side length
    public void drawSquare(int side) {
        try {
            System.out.println(" ");
            // Display message indicating square drawing has started
            System.out.println(ANSI_GREEN + "<< Swiftbot Is Drawing a Square >>" + ANSI_RESET);

            // calculates the time the swiftbot should move to draw each side of the square
            int toMove = side * 10;

            // Loop to draw each side of the square
            for (int i = 0; i < 4; i++) {
                API.move(100, 100, toMove); // Move the swiftbot to draw the side
                API.move(0, 0, 1000); // Pause after drawing the side

                // Turns the swiftbot by 90 degrees exactly 3 times to complete the square
                if (i < 3) {
                    API.move(100, -100, 400); // 400 milliseconds is 90 degrees turn
                }

                // Sets the underlight to green when the drawing is finished
                if (i == 3) {
                    int[] colourToLightUp = { 0, 0, 255 };
                    API.fillUnderlights(colourToLightUp);
                    API.disableUnderlights();
                }

            }
        } catch (Exception e) {
            // Display error message if any exception occurs during drawing
            System.out.println(ANSI_RED + "Error Occurred While Drawing Square: " + e.getMessage() + ANSI_RESET);
        }
    }
}
