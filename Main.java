import java.util.Scanner;

import swiftbot.*;

public class Main {
    public static void main(String[] args) {

        SwiftBotAPI API = new SwiftBotAPI();

        Square obj1 = new Square(API);
        Triangle obj2 = new Triangle(API);
        QRCode obj3 = new QRCode(API);
        Scanner scanner = new Scanner(System.in);

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_YELLOW = "\u001B[33m";

        System.out.println();
        System.out.println(ANSI_CYAN + "******************************************************");
        System.out.println(ANSI_CYAN + "*                 Welcome to Swiftbot                *");
        System.out.println(ANSI_CYAN + "******************************************************");

        while (true) {
            System.out.println(ANSI_CYAN + "Select an option:");
            System.out.println(ANSI_CYAN + "1. Press 1 to Scan the QR Code");
            System.out.println(ANSI_CYAN + "2. Press 2 to Exit" + ANSI_RESET);
            System.out.println(" ");

            System.out.println("Enter Your Selected Option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    obj3.decodeQR();

                    String shape = obj3.shape;

                    if ("S".equals(shape)) {
                        int sideSquare = obj3.side;
                        obj1.drawSquare(sideSquare);

                    } else if ("T".equals(shape)) {
                        int side1 = obj3.side1;
                        int side2 = obj3.side2;
                        int side3 = obj3.side3;

                        String canForm = obj2.isTriangle(side1, side2, side3);
                        if (obj2.isTriangle(side1, side2, side3) == "true") {
                            obj2.drawTriangle(side1, side2, side3);
                        } else {
                            System.out.println(" ");
                            System.out
                                    .println(ANSI_RED + "Triangle cannot be formed with the given sides." + ANSI_RESET);
                            System.out.println(" ");
                        }
                    }
                    break;

                case 2:
                    System.out.println(" ");
                    System.out.println(ANSI_CYAN + "Exiting The Program" + ANSI_RESET);
                    return;

                default:
                    System.out.println(" ");
                    System.out.println(ANSI_RED + "Invalid choice. Please Select From Option 1 and 2" + ANSI_RESET);
                    System.out.println(" ");
            }

        }
    }
}
