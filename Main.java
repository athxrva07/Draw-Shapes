import swiftbot.*;
import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
    static List<String> shapeInfo = new ArrayList<>(); // stores name of the shape along with side
    static List<Long> timeTakenList = new ArrayList<>(); // stores time taken for drawing each shape
    static List<Double> triangleAngles = new ArrayList<>(); // stores the angles of triangle drawn
    static Map<String, Integer> shapeCount = new HashMap<>(); // stores how many time each shape is drawn
    static Map<String, Double> shapeAreas = new HashMap<>(); // stores area of each shape drawn
    static String largestShapeDrawn = "";
    static double largestArea = Double.MIN_VALUE;

    public static void main(String[] args) {

        SwiftBotAPI API = new SwiftBotAPI();
        Square square = new Square(API);
        Triangle triangle = new Triangle(API);
        QRCode qrcode = new QRCode(API);

        // ANSI color codes for colored output
        final String GREEN = "\u001B[32m";
        final String CYAN = "\u001B[36m";
        final String RED = "\u001B[31m";
        final String RESET = "\u001B[0m";

        // display the welcome message
        System.out.println();
        System.out.println(GREEN + "======================================================");
        System.out.println(GREEN + "                  Welcome to Swiftbot                 ");
        System.out.println(GREEN + "======================================================");
        System.out.println(" ");
        System.out.println(GREEN + "Press Button A to Scan the QR Code" + RESET);
        System.out.println(GREEN + "Press Button X to Terminate" + RESET);

        API.enableButton(Button.A, () -> {

            System.out.println(" ");
            System.out.println(GREEN + "Button A has been pressed." + RESET);

            // when button A is pressed, this method scans the QR code, decodes it and draws
            // the shape
            case1(square, triangle, qrcode, CYAN, GREEN, RED, RESET);
        });

        API.enableButton(Button.X, () -> {

            System.out.println("Button X has been pressed.");

            // when button X is pressed, this method creates the text file to store shape
            // information and then terminates the program
            case2();
        });

        while (true) {
        }
    }

    private static void case1(Square square, Triangle triangle, QRCode qrcode, String CYAN, String GREEN, String RED,
            String RESET) {

        // calls the decodeQR() method from the QRCode class file
        qrcode.decodeQR();
        String shape = qrcode.shape;
        String Continue = qrcode.Continue;

        if ("true".equals(Continue)) {

            // records the start time before drawing the shape
            long startTime = System.currentTimeMillis();

            if ("S".equals(shape)) {

                int side = qrcode.sideLength;

                // checks if side is greater than 15 and less than 85 or not and draws the
                // square if it is
                if (side > 15 && side < 85) {
                    square.drawSquare(side);

                    // records the end time after drawing the shape
                    long endTime = System.currentTimeMillis();
                    // calculates the time taken for drawing the shape
                    long timeTaken = endTime - startTime;
                    // adds the time taken to the list
                    timeTakenList.add(timeTaken);

                    double areaSquare = side * side;
                    System.out.println(" ");
                    System.out.println(CYAN + "Area of this square is " + areaSquare + "cm sq" + RESET);
                    System.out.println(" ");

                    shapeInfo.add("Square: " + side);
                    shapeAreas.put("Square", areaSquare);

                    if (areaSquare > largestArea) {
                        largestArea = areaSquare;
                        largestShapeDrawn = "Square";
                    }
                    // updates shape counts and increases it by 1 each time it is drawn
                    shapeCount.put("Square", shapeCount.getOrDefault("Square", 0) + 1);
                } else {
                    System.out.println(" ");
                    System.out.println(RED + "The sides should Be between 15 and 85" + RESET);
                    System.out.println(" ");
                }
            } else if ("T".equals(shape)) {

                int side1 = qrcode.side1Length;
                int side2 = qrcode.side2Length;
                int side3 = qrcode.side3Length;

                String canForm = triangle.isTriangle(side1, side2, side3);
                if (canForm.equals("true")) {
                    triangle.drawTriangle(side1, side2, side3);
                    double area = triangle.triangleArea(side1, side2, side3);

                    // records the end time after drawing the shape
                    long endTime = System.currentTimeMillis();
                    // calculates the time taken for drawing the shape
                    long timeTaken = endTime - startTime;
                    // adds the time taken to the list
                    timeTakenList.add(timeTaken);

                    System.out.println(" ");
                    System.out.println(CYAN + "Area of this triangle is " + area + "cm sq" + RESET);
                    System.out.println(" ");

                    shapeInfo.add("Triangle: " + side1 + ", " + side2 + ", " + side3);
                    shapeAreas.put("Triangle", area);

                    if (area > largestArea) {
                        largestArea = area;
                        largestShapeDrawn = "Triangle";
                    }

                    // calculate and add angles of the triangle for adding them to the ShapeInfo
                    // text file
                    double angle1 = triangle.triangleAngle(side1, side2, side3);
                    double angle2 = triangle.triangleAngle(side2, side3, side1);
                    double angle3 = triangle.triangleAngle(side3, side1, side2);

                    triangleAngles.add(angle1);
                    triangleAngles.add(angle2);
                    triangleAngles.add(angle3);

                    // update shape counts and increase it by 1 each time it is drawn
                    shapeCount.put("Triangle", shapeCount.getOrDefault("Triangle", 0) + 1);
                } else {
                    System.out.println(" ");
                    System.out.println(RED + "Triangle cannot be formed with the given sides." + RESET);
                    System.out.println(RED + "Use Another QR Code." + RESET);
                    System.out.println(" ");
                }
            }
        } else {
            System.out.println("Use a QR Code");
            System.out.println(" ");
        }
        System.out.println(GREEN + "1. Press A to scan the QR code");
        System.out.println(GREEN + "2. Press X to generate the text file and exit" + RESET);
    }

    private static void case2() {
        // if button X is pressed, exit the program and write THE information to a file
        final String YELLOW = "\u001B[33m";
        final String GREEN = "\u001B[32m";
        final String RED = "\u001B[31m";
        final String RESET = "\u001B[0m";

        System.out.println(" ");
        System.out.println(YELLOW + "Text file is generated" + RESET);
        System.out.println(GREEN + "Program Terminated" + GREEN);

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("ShapeInfo.txt", true));
            writer.println("Shapes Drawn:");
            for (String info : shapeInfo) {
                if (info.startsWith("Triangle")) {
                    StringBuilder trianglesInfo = new StringBuilder(info);
                    StringBuilder anglesInfo = new StringBuilder(" (Angles: ");
                    for (int i = 0; i < 3; i++) {
                        double angle = triangleAngles.remove(0);
                        anglesInfo.append(angle);
                        if (i < 2) {
                            anglesInfo.append(", ");
                        }
                    }
                    anglesInfo.append(")");
                    trianglesInfo.append(anglesInfo);
                    writer.println(trianglesInfo);
                } else {
                    writer.println(info);
                }
            }

            writer.println("Largest shape drawn shape is " + largestShapeDrawn + " (Area: " + largestArea + ")");

            int maxCount = 0;
            String mostFrequentShape = "";
            for (Map.Entry<String, Integer> entry : shapeCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentShape = entry.getKey();
                }
            }
            writer.println("Most frequently drawn shape is " + mostFrequentShape + " (" + maxCount + " Times)");

            // calculate the average time taken to draw
            long total = 0;
            for (long time : timeTakenList) {
                total += time;
            }
            double averageTime = (double) total / timeTakenList.size();
            writer.println("Average time taken to draw is " + averageTime + " milliseconds");

            writer.close();

        } catch (Exception e) {
            System.out.println(RED + "Error occurred while writing to file: " + e.getMessage() + RESET);
        }
        System.exit(0);
    }
}
