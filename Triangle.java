import swiftbot.*;

public class Triangle {

    private SwiftBotAPI API;

    public Triangle(SwiftBotAPI api) {
        this.API = api;
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    // checks if the triangle can be formed or not
    public String isTriangle(int side1, int side2, int side3) {
        try {
            if ((side1 + side2 > side3) && (side1 + side3 > side2) && (side2 + side3 > side1)) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error Occurred While Validating the Sides: " + e.getMessage() + ANSI_RESET);
            return "false";
        }
    }

    // swiftbot moves to form a triangle
    public void drawTriangle(int side1, int side2, int side3) {
        try {
            double angleA = calculateAngle(side1, side1, side2);
            double angleB = calculateAngle(side2, side3, side1);

            int angleAint = (int) angleA;
            int angleBint = (int) angleB;

            int toTurnA = (180 - angleAint) * 5;
            int toTurnB = (180 - angleBint) * 5;

            int drawSide1 = side1 * 80;
            int drawSide2 = side2 * 80;
            int drawSide3 = side3 * 80;

            System.out.println(" ");
            System.out.println(ANSI_GREEN + "<< Swiftbot Is Drawing a Triangle >>" + ANSI_RESET);

            API.move(100, 94, drawSide1);
            API.move(0, 0, 1000);

            API.move(100, -98, toTurnA);
            API.move(0, 0, 1000);

            API.move(100, 94, drawSide2);
            API.move(0, 0, 400);

            API.move(100, -98, toTurnB);
            API.move(0, 0, 1000);

            API.move(100, 94, drawSide3);
            API.move(0, 0, 500);

            // turns on the underlights
            int[] colourToLightUp = { 0, 0, 255 };
            API.fillUnderlights(colourToLightUp);
            API.disableUnderlights();

            // calculates and prints the area of the triangle
            double areaTriangle = calculateAngle(side1, side2, side3);
            System.out.println(ANSI_GREEN + "Area of this square is " + areaTriangle + "cm sq" + ANSI_RESET);
            System.out.println(" ");

        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error Occurred While Drawing Triangle: " + e.getMessage() + ANSI_RESET);
        }
    }

    // calculates the angles of the triangle
    public double calculateAngle(double side1, double side2, double side3) {
        try {
            double numerator = side2 * side2 + side3 * side3 - side1 * side1;
            double denominator = 2 * side2 * side3;
            double radians = Math.acos(numerator / denominator);
            return Math.toDegrees(radians);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error Occurred While Calculating Angles: " + e.getMessage() + ANSI_RESET);
            return 0.0;
        }
    }

    // calculates the area of the triangle
    public double calculateArea(int side1, int side2, int side3) {
        try {
            double s = (side1 + side2 + side3) / 2.0;
            double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
            return area;
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error Occurred While Calculating Area: " + e.getMessage() + ANSI_RESET);
            return 0.0;
        }
    }
}
