import swiftbot.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class QRCode {

    private SwiftBotAPI API;

    public QRCode(SwiftBotAPI api) {
        this.API = api;
    }

    // ANSI color codes for colored output
    final String RED = "\u001B[31m";
    final String RESET = "\u001B[0m";

    public int sideLength = 0;
    public int side1Length = 0;
    public int side2Length = 0;
    public int side3Length = 0;
    public String Continue;
    public String shape;

    // method to decode the QR code
    public void decodeQR() {
        // ArrayList to store the decoded text
        ArrayList<String> decodedInfo = new ArrayList<>();

        try {
            BufferedImage img = API.getQRImage();
            String decodedText = API.decodeQRImage(img);

            if (!decodedText.isEmpty()) {
                Continue = "true";
                // splits the decoded text at " "
                String[] parts = decodedText.split(" ");

                // adds the decoded text to the ArrayList
                for (String part : parts) {
                    decodedInfo.add(part.trim());
                }

                if (decodedInfo.get(0).equals("S")) {
                    String decodedShape = "S";
                    shape = decodedShape;

                    // parse side length for square with error handling
                    try {
                        sideLength = Integer.parseInt(decodedInfo.get(1));
                    } catch (NumberFormatException e) {
                        System.out.println(RED + "Error parsing side length for square: " + e.getMessage());
                    }
                } else if (decodedInfo.get(0).equals("T")) {
                    String decodedShape = "T";
                    shape = decodedShape;

                    // parse side lengths for triangle with error handling
                    try {
                        side1Length = Integer.parseInt(decodedInfo.get(1));
                        side2Length = Integer.parseInt(decodedInfo.get(2));
                        side3Length = Integer.parseInt(decodedInfo.get(3));
                    } catch (NumberFormatException e) {
                        System.out.println(RED + "Error parsing side lengths for triangle: " + e.getMessage());
                    }
                } else {
                    System.out.println(" ");
                    System.out.println(RED + "No shape was found. Use another QR code");
                    System.out.println(" ");
                }
            } else {
                Continue = "false";
                System.out.println(RED + "No QR code was found" + RESET);
                System.out.println(" ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}