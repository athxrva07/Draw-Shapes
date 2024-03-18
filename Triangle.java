import swiftbot.*;

public class Triangle {

    private SwiftBotAPI API;

    public Triangle(SwiftBotAPI api) {
        this.API = api;
    }

    // ANSI color codes for colored output
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    // checks if triangle can be formed or not
    public String isTriangle(int side1, int side2, int side3) {
        try {
            if ((side1 > 15 && side1 < 85) && (side2 > 15 && side2 < 85) && (side3 > 15 && side3 < 85)
                    && (side1 + side2 > side3) && (side1 + side3 > side2) && (side2 + side3 > side1)) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            System.out.println(RED + "Error occurred while validating the sides: " + e.getMessage() + RESET);
            return "false";
        }
    }

    // draws a triangle with given side lengths
    public void drawTriangle(int side1, int side2, int side3) {
        try {
            // calculates angles of the triangle using the side lengths
            double angleA = triangleAngle(side1, side1, side2);
            double angleB = triangleAngle(side2, side3, side1);

            // Converts angles to integers for turning the Swiftbot
            int angleAint = (int) angleA;
            int angleBint = (int) angleB;

            // calculates degrees to turn for each angle
            int toTurnA = (180 - angleAint) * 5;
            int toTurnB = (180 - angleBint) * 5;

            // calculate lengths to draw each side of the triangle
            int drawSide1 = side1 * 80;
            int drawSide2 = side2 * 80;
            int drawSide3 = side3 * 80;

            System.out.println(" ");
            System.out.println(CYAN + "Swiftbot Is Drawing a Triangle" + RESET);

            // draws the first side of the triangle
            API.move(50, 50, drawSide1);
            API.move(0, 0, 1000);

            // urns Swiftbot to draw the second side
            API.move(100, -100, toTurnA);
            API.move(0, 0, 1000);

            // moves to draw the second side
            API.move(50, 50, drawSide2);
            API.move(0, 0, 1000);

            // moves to draw the third side
            API.move(100, -100, toTurnB);
            API.move(0, 0, 1000);

            // move Swiftbot to draw the third side
            API.move(50, 50, drawSide3);
            API.move(0, 0, 1000);

            // blink the underlights
            int[] colourToLightUp = { 0, 0, 255 };
            API.fillUnderlights(colourToLightUp);
            API.disableUnderlights();

        } catch (Exception e) {
            System.out.println(RED + "Error Occurred While Drawing Triangle: " + e.getMessage() + RESET);
        }
    }

    // calculate the angles of the triangle
    public double triangleAngle(double side1, double side2, double side3) {
        try {
            double n = side2 * side2 + side3 * side3 - side1 * side1;
            double d = 2 * side2 * side3;
            double radians = Math.acos(n / d);
            return Math.toDegrees(radians);
        } catch (Exception e) {
            System.out.println(RED + "Error occurred while calculating angles: " + e.getMessage() + RESET);
            return 0.0;
        }
    }

    // calculate the area of the triangle
    public double triangleArea(int side1, int side2, int side3) {
        try {
            // heron's formula to calculate the area of the triangle
            double s = (side1 + side2 + side3) / 2.0;
            double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
            return area;
        } catch (Exception e) {
            System.out.println(RED + "Error occurred while calculating the area: " + e.getMessage() + RESET);
            return 0.0;
        }
    }
}