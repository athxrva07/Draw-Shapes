import swiftbot.*;

public class Square {

    private SwiftBotAPI API;

    // constructor to initialize Square object with SwiftBotAPI
    public Square(SwiftBotAPI api) {
        this.API = api;
    }

    // ANSI color codes for colored output
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    // method to draw a square with given side length
    public void drawSquare(int side) {
        try {
            System.out.println(" ");
            System.out.println(ANSI_GREEN + "<< Swiftbot Is Drawing a Square >>" + ANSI_RESET);

            // calculates the time the swiftbot should move to draw each side of the square
            int toMove = side * 80;

            // loop to draw each side of the square
            for (int i = 0; i < 4; i++) {
                API.move(50, 50, toMove);
                API.move(0, 0, 1000); // pauses after drawing the side

                // turns the swiftbot by 90 degrees exactly 3 times to complete the square
                if (i < 3) {
                    API.move(100, -100, 410); // 410 milliseconds is 90 degrees turn
                }

                // Sets the underlight to green when the drawing is finished
                if (i == 3) {
                    int[] colourToLightUp = { 0, 0, 255 };
                    API.fillUnderlights(colourToLightUp);
                    API.disableUnderlights();
                }

            }
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error Occurred While Drawing Square: " + e.getMessage() + ANSI_RESET);
        }
    }
}
