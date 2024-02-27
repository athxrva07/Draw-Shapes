import swiftbot.*;

public class triangle {

    private SwiftBotAPI API;

    public triangle(SwiftBotAPI api) {
        this.API = api;
    }

    // checks if the triangle can be formed or not
    public String isTriangle(int side1, int side2, int side3) {
        String canForm;
        if ((side1 + side2 > side3) && (side1 + side3 > side2) && (side2 + side3 > side1)) {
            String value = "true";
            canForm = value;
        } else {
            String value = "false";
            canForm = value;
        }
        return canForm;
    }

    // swiftbot moves to form a triangle
    public void drawTriangle(int side1, int side2, int side3) {

        double angleA = calculateAngle(side1, side1, side2);
        double angleB = calculateAngle(side2, side3, side1);

        int angleAint = (int) angleA;
        int angleBint = (int) angleB;

        int toTurnA = (180 - angleAint) * 5;
        int toTurnB = (180 - angleBint) * 5;

        int drawSide1 = side1 * 80;
        int drawSide2 = side2 * 80;
        int drawSide3 = side3 * 80;

        System.out.println("Triangle can be formed with the given sides.");
        System.out.println("Swiftbot is drawing the triangle.");

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

    }

    // calculates the angles
    public double calculateAngle(double side1, double side2, double side3) {

        double numerator = side2 * side2 + side3 * side3 - side1 * side1;

        double denominator = 2 * side2 * side3;

        double radians = Math.acos(numerator / denominator);

        return Math.toDegrees(radians);
    }
}
