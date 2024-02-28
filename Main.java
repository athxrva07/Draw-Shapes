import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import swiftbot.*;

public class Main {
    public static void main(String[] args) {

        // Initialize SwiftBotAPI and other necessary objects
        SwiftBotAPI API = new SwiftBotAPI();
        Square obj1 = new Square(API);
        Triangle obj2 = new Triangle(API);
        QRCode obj3 = new QRCode(API);
        Scanner scanner = new Scanner(System.in);

        // Define ANSI color codes for colored output
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        // Initialize lists and maps to store shape information
        List<String> shapeInfoList = new ArrayList<>();
        List<Double> triangleAngles = new ArrayList<>();
        Map<String, Double> shapeAreas = new HashMap<>();
        String largestShape = "";
        double largestArea = Double.MIN_VALUE;
        Map<String, Integer> shapeCounts = new HashMap<>();

        // Display welcome message
        System.out.println();
        System.out.println(ANSI_CYAN + "******************************************************");
        System.out.println(ANSI_CYAN + "*                 Welcome to Swiftbot                *");
        System.out.println(ANSI_CYAN + "******************************************************");

        // Main loop to handle user input
        while (true) {
            // Display options to the user
            System.out.println(ANSI_CYAN + "Select an option:");
            System.out.println(ANSI_CYAN + "1. Press 1 to Scan the QR Code");
            System.out.println(ANSI_CYAN + "2. Press 2 to Exit" + ANSI_CYAN);

            // Get user's choice
            System.out.println("Enter Your Selected Option: ");
            int choice = scanner.nextInt();

            // Perform actions based on user's choice
            switch (choice) {
                case 1:
                    // If option 1 is selected, decode QR code and determine the shape
                    obj3.decodeQR();
                    String shape = obj3.shape;

                    // Process based on the detected shape
                    if ("S".equals(shape)) {
                        // If the shape is a square, draw it and calculate its area
                        int sideSquare = obj3.side;
                        // checks if side is greater than 15 and less than 85 or not
                        // draws the square if it is
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
                            // Update shape counts
                            shapeCounts.put("Square", shapeCounts.getOrDefault("Square", 0) + 1);
                        } else {
                            System.out.println(" ");
                            System.out.println(ANSI_RED + "The Sides Should Be Between 15 and 85" + ANSI_RESET);
                            System.out.println(" ");
                        }
                    } else if ("T".equals(shape)) {
                        // If the shape is a triangle, draw it, calculate its area and angle
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
                            double angle1 = obj2.calculateAngle(side1, side2, side3);
                            double angle2 = obj2.calculateAngle(side2, side3, side1);
                            double angle3 = obj2.calculateAngle(side3, side1, side2);
                            triangleAngles.add(angle1);
                            triangleAngles.add(angle2);
                            triangleAngles.add(angle3);
                            // Update shape counts
                            shapeCounts.put("Triangle", shapeCounts.getOrDefault("Triangle", 0) + 1);
                        } else {
                            System.out.println(" ");
                            System.out.println(ANSI_RED + "Triangle cannot be formed with the given sides." + ANSI_RESET);
                            System.out.println(" ");
                        }
                    }
                    break;

                case 2:
                    // If option 2 is selected, exit the program and write accumulated information
                    // to a file
                    System.out.println(" ");
                    System.out.println(ANSI_GREEN + "Shape Information File Generated" + ANSI_RESET);
                    System.out.println(ANSI_CYAN + "Exiting The Program" + ANSI_CYAN);

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
                                "Most Frequently Drawn Shape: " + mostFrequentShape + " (" + maxCount + " times)");

                        writer.close();
                    } catch (Exception e) {
                        System.out.println("Error occurred while writing to file: " + e.getMessage());
                    }
                    return;

                default:
                    System.out.println(" ");
                    System.out.println("Invalid choice. Please Select From Option 1 and 2");
                    System.out.println(" ");
            }
        }
    }
}
