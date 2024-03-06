import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import swiftbot.*;

public class Main {

    // defines lists and maps to store shape information
    static List<String> shapeInfoList = new ArrayList<>();
    static List<Double> triangleAngles = new ArrayList<>();
    static Map<String, Double> shapeAreas = new HashMap<>();
    static String largestShape = "";
    static double largestArea = Double.MIN_VALUE;
    static Map<String, Integer> shapeCounts = new HashMap<>();
    static List<Long> timeTakenList = new ArrayList<>(); // list to store time taken for drawing each shape

    public static void main(String[] args) {

        // initializes SwiftBotAPI and other necessary objects
        SwiftBotAPI API = new SwiftBotAPI();
        Square obj1 = new Square(API);
        Triangle obj2 = new Triangle(API);
        QRCode obj3 = new QRCode(API);

        // ANSI color codes for colored output
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        // displays the welcome message
        System.out.println();
        System.out.println(ANSI_CYAN + "******************************************************");
        System.out.println(ANSI_CYAN + "*                 Welcome to Swiftbot                *");
        System.out.println(ANSI_CYAN + "******************************************************");
        System.out.println(" ");
        System.out.println(ANSI_CYAN + "Press Button A to Scan the QR Code" + ANSI_RESET);

        API.enableButton(Button.A, () -> {

            System.out.println(" ");
            System.out.println(ANSI_CYAN + "Button A Has Been Pressed" + ANSI_CYAN);

            // executes case 1 when button A is pressed. This scans the QR code, decodes it
            // and draws the shape accordingly.
            executeCase1(obj1, obj2, obj3, ANSI_CYAN, ANSI_GREEN, ANSI_RED, ANSI_RESET);
        });

        API.enableButton(Button.X, () -> {

            System.out.println(ANSI_CYAN + "Button X Has Been Pressed" + ANSI_CYAN);

            // executes case 2 when button X is pressed. This creates the text file to store
            // shape information and then terminates the program
            executeCase2();
        });

        // main loop to handle user input
        while (true) {
        }
    }

    // Method to execute case 1
    private static void executeCase1(Square obj1, Triangle obj2, QRCode obj3, String ANSI_CYAN, String ANSI_GREEN,
            String ANSI_RED, String ANSI_RESET) {

        // records the start time before drawing the shape
        long startTime = System.currentTimeMillis();

        // calls the decodeQR() method from the QRCode class file
        obj3.decodeQR();
        String shape = obj3.shape;
        String toContinue = obj3.Continue;

        if ("true".equals(toContinue)) {

            if ("S".equals(shape)) {
                // records the end time after drawing the shape
                long endTime = System.currentTimeMillis();
                // calculates the time taken for drawing the shape
                long timeTaken = endTime - startTime;
                // adds the time taken to the list
                timeTakenList.add(timeTaken);

                int sideSquare = obj3.side;

                // checks if side is greater than 15 and less than 85 or not and draws the
                // square if it is
                if (sideSquare > 15 && sideSquare < 85) {
                    obj1.drawSquare(sideSquare);

                    double area = sideSquare * sideSquare;
                    System.out.println(ANSI_GREEN + "Area of this square is " + area + "cm sq" + ANSI_RESET);
                    System.out.println(" ");

                    shapeInfoList.add("Square: " + sideSquare);
                    shapeAreas.put("Square", area);

                    if (area > largestArea) {
                        largestArea = area;
                        largestShape = "Square";
                    }
                    // updates shape counts
                    shapeCounts.put("Square", shapeCounts.getOrDefault("Square", 0) + 1);
                } else {
                    System.out.println(" ");
                    System.out.println(ANSI_RED + "The Sides Should Be Between 15 and 85" + ANSI_RESET);
                    System.out.println(" ");
                }
            } else if ("T".equals(shape)) {
                // records the end time after drawing the shape
                long endTime = System.currentTimeMillis();
                // calculates the time taken for drawing the shape
                long timeTaken = endTime - startTime;
                // adds the time taken to the list
                timeTakenList.add(timeTaken);

                int side1 = obj3.side1;
                int side2 = obj3.side2;
                int side3 = obj3.side3;

                String canForm = obj2.isTriangle(side1, side2, side3);
                if (canForm.equals("true")) {
                    obj2.drawTriangle(side1, side2, side3);
                    double area = obj2.calculateArea(side1, side2, side3);
                    System.out.println(ANSI_GREEN + "Area of this triangle is " + area + "cm sq" + ANSI_RESET);
                    System.out.println(" ");

                    shapeInfoList.add("Triangle: " + side1 + ", " + side2 + ", " + side3);
                    shapeAreas.put("Triangle", area);

                    if (area > largestArea) {
                        largestArea = area;
                        largestShape = "Triangle";
                    }

                    // calculates angles of the triangle for adding them to the ShapeInfo text file
                    double angle1 = obj2.calculateAngle(side1, side2, side3);
                    double angle2 = obj2.calculateAngle(side2, side3, side1);
                    double angle3 = obj2.calculateAngle(side3, side1, side2);

                    triangleAngles.add(angle1);
                    triangleAngles.add(angle2);
                    triangleAngles.add(angle3);

                    // update shape counts
                    shapeCounts.put("Triangle", shapeCounts.getOrDefault("Triangle", 0) + 1);
                } else {
                    System.out.println(" ");
                    System.out.println(ANSI_RED + "Triangle cannot be formed with the given sides." + ANSI_RESET);
                    System.out.println(ANSI_RED + "Use Another QR Code." + ANSI_RESET);
                    System.out.println(" ");
                }
            }

        } else {
            System.out.println(" ");
            System.out.println("Use A QR Code.");
            System.out.println(" ");
        }

        System.out.println(ANSI_CYAN + "Select an option:");
        System.out.println(ANSI_CYAN + "1. Press A to Scan the QR Code");
        System.out.println(ANSI_CYAN + "2. Press X to Generate the Text File and Exit" + ANSI_CYAN);
    }

    // method to execute case 2
    private static void executeCase2() {
        // if button X is pressed, exit the program and write accumulated information to
        // a file
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_YELLOW = "\u001B[33m";

        System.out.println(" ");
        System.out.println(ANSI_YELLOW + "Shape Information File Generated" + ANSI_RESET);
        System.out.println(" ");
        System.out.println(ANSI_CYAN + "Program Terminated" + ANSI_CYAN);

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("ShapeInfo.txt", true));
            writer.println("Shapes Drawn:");
            for (String info : shapeInfoList) {
                if (info.startsWith("Triangle")) {
                    StringBuilder triangleInfo = new StringBuilder(info);
                    StringBuilder anglesInfo = new StringBuilder(" (Angles: ");
                    for (int i = 0; i < 3; i++) {
                        double angle = triangleAngles.remove(0);
                        anglesInfo.append(angle);
                        if (i < 2) {
                            anglesInfo.append(", ");
                        }
                    }
                    anglesInfo.append(")");
                    triangleInfo.append(anglesInfo);
                    writer.println(triangleInfo);
                } else {
                    writer.println(info);
                }
            }

            writer.println("Largest Shape Drawn: " + largestShape + " (Area: " + largestArea + ")");

            int maxCount = 0;
            String mostFrequentShape = "";
            for (Map.Entry<String, Integer> entry : shapeCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentShape = entry.getKey();
                }
            }
            writer.println(
                    "Most Frequently Drawn Shape: " + mostFrequentShape + " (" + maxCount + " Times)");

            // calculates average time taken to draw
            long total = 0;
            for (long time : timeTakenList) {
                total += time;
            }
            double averageTime = (double) total / timeTakenList.size();

            writer.println("Average Time Taken to Draw: " + averageTime + " milliseconds");

            writer.close();
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error occurred while writing to file: " + e.getMessage() + ANSI_RESET);
        }
        System.exit(0);
    }
}
