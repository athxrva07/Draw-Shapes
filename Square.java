import swiftbot.*;

public class Square {

    private SwiftBotAPI API;

    public Square(SwiftBotAPI api) {
        this.API = api;
    }

    // ANSI color codes for colored output
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";

    // method to draw a square with given side length
    public void drawSquare(int sideLength) {
        try {
            System.out.println(" ");
            System.out.println(CYAN + "Swiftbot Is Drawing a Square" + RESET);

            // calculates the time the swiftbot should move to draw each side of the square
            int timeToMove = sideLength * 80;

            // loop to draw the 4 sides of the square
            for (int i = 0; i < 4; i++) {
                API.move(50, 50, timeToMove);
                API.move(0, 0, 1000); // pauses after drawing the side

                // turns the swiftbot by 90 degrees exactly 3 times to complete the square
                if (i < 3) {
                    API.move(100, -100, 410);
                }

                // blinks the underlight green when the drawing is finished
                if (i == 3) {
                    int[] colourToLightUp = { 0, 0, 255 };
                    API.fillUnderlights(colourToLightUp);
                    API.disableUnderlights();
                }

            }
        } catch (Exception e) {
            System.out.println(RED + "Error Occurred While Drawing Square: " + e.getMessage() + RESET);
        }
    }
}