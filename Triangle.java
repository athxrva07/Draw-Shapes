import swiftbot.*;

public class Triangle {

    private SwiftBotAPI API;

    // Constructor to initialize Triangle object with SwiftBotAPI
    public Triangle(SwiftBotAPI api) {
        this.API = api;
    }

    // ANSI color codes for colored output
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    // Method to check if a triangle can be formed with given side lengths
    public String isTriangle(int side1, int side2, int side3) {
        try {
            if ((side1 + side2 > side3) && (side1 + side3 > side2) && (side2 + side3 > side1)) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            // Display error message if any exception occurs during validation
            System.out.println(ANSI_RED + "Error Occurred While Validating the Sides: " + e.getMessage() + ANSI_RESET);
            return "false";
        }
    }

    // Method to draw a triangle with given side lengths
    public void drawTriangle(int side1, int side2, int side3) {
        try {
            // Calculate angles of the triangle using the side lengths
            double angleA = calculateAngle(side1, side1, side2);
            double angleB = calculateAngle(side2, side3, side1);

            // Convert angles to integers for turning the Swiftbot
            int angleAint = (int) angleA;
            int angleBint = (int) angleB;

            // Calculate degrees to turn for each angle
            int toTurnA = (180 - angleAint) * 5;
            int toTurnB = (180 - angleBint) * 5;

            // Calculate lengths to draw each side of the triangle
            int drawSide1 = side1 * 20;
            int drawSide2 = side2 * 20;
            int drawSide3 = side3 * 20;

            System.out.println(" ");
            // Display message indicating triangle drawing has started
            System.out.println(ANSI_GREEN + "<< Swiftbot Is Drawing a Triangle >>" + ANSI_RESET);

            // Move Swiftbot to draw the first side of the triangle
            API.move(50, 50, drawSide1);
            API.move(0, 0, 1000);

            // Turn Swiftbot to draw the second side of the triangle
            API.move(100, -98, toTurnA);
            API.move(0, 0, 1000);

            // Move Swiftbot to draw the second side of the triangle
            API.move(50, 50, drawSide2);
            API.move(0, 0, 1000);

            // Turn Swiftbot to draw the third side of the triangle
            API.move(100, -98, toTurnB);
            API.move(0, 0, 1000);

            // Move Swiftbot to draw the third side of the triangle
            API.move(50, 50, drawSide3);
            API.move(0, 0, 1000);

            // Turn on the underlights
            int[] colourToLightUp = { 0, 0, 255 };
            API.fillUnderlights(colourToLightUp);
            API.disableUnderlights();

            // Calculate the total time taken for drawing the triangle
            int timeTaken = (drawSide1 + drawSide2 + drawSide3 + toTurnA + toTurnB) + (1000 * 5);

        } catch (Exception e) {
            // Display error message if any exception occurs during drawing
            System.out.println(ANSI_RED + "Error Occurred While Drawing Triangle: " + e.getMessage() + ANSI_RESET);
        }
    }

    // Method to calculate the angles of the triangle
    public double calculateAngle(double side1, double side2, double side3) {
        try {
            // Use law of cosines to calculate angles
            double numerator = side2 * side2 + side3 * side3 - side1 * side1;
            double denominator = 2 * side2 * side3;
            double radians = Math.acos(numerator / denominator);
            return Math.toDegrees(radians);
        } catch (Exception e) {
            // Display error message if any exception occurs during angle calculation
            System.out.println(ANSI_RED + "Error Occurred While Calculating Angles: " + e.getMessage() + ANSI_RESET);
            return 0.0;
        }
    }

    // Method to calculate the area of the triangle
    public double calculateArea(int side1, int side2, int side3) {
        try {
            // Use Heron's formula to calculate the area of the triangle
            double s = (side1 + side2 + side3) / 2.0;
            double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
            return area;
        } catch (Exception e) {
            // Display error message if any exception occurs during area calculation
            System.out.println(ANSI_RED + "Error Occurred While Calculating Area: " + e.getMessage() + ANSI_RESET);
            return 0.0;
        }
    }
}
