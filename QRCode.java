import swiftbot.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class QRCode {

    // ANSI color codes for colored output
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_RESET = "\u001B[0m";

    
    private SwiftBotAPI API;

    // initializes the QRCode object with a SwiftBotAPI instance
    public QRCode(SwiftBotAPI api) {
        this.API = api;
    }

    // Properties to store information about the QR code
    public int side;     // side length for square
    public int side1;    // first side length for triangle
    public int side2;    // second side length for triangle
    public int side3;    // third side length for triangle
    public String shape;
    public String Continue;

    // method to decode the QR code
    public void decodeQR() {
        // ArrayList to store the decoded text
        ArrayList<String> decodedInfo = new ArrayList<>();
    
        try {
            BufferedImage img = API.getQRImage();
            String decodedText = API.decodeQRImage(img);
    
            if (!decodedText.isEmpty()) {
                Continue = "true";
                // splits the decoded text
                String[] parts = decodedText.split(" ");
    
                // adds the decoded text to the ArrayList, trimming each part to remove whitespace characters
                for (String part : parts) {
                    decodedInfo.add(part.trim());
                }
    
                if (decodedInfo.get(0).equals("S")) {
                    String decodedShape = "S";
                    shape = decodedShape;
    
                    // Parsing side length for square with error handling
                    try {
                        side = Integer.parseInt(decodedInfo.get(1));
                    } catch (NumberFormatException e) {
                        System.out.println(ANSI_RED + "Error parsing side length for square: " + e.getMessage());
                    }
                } else if (decodedInfo.get(0).equals("T")) {
                    String decodedShape = "T";
                    shape = decodedShape;
    
                    // Parsing side lengths for triangle with error handling
                    try {
                        side1 = Integer.parseInt(decodedInfo.get(1));
                        side2 = Integer.parseInt(decodedInfo.get(2));
                        side3 = Integer.parseInt(decodedInfo.get(3));
                    } catch (NumberFormatException e) {
                        System.out.println(ANSI_RED + "Error parsing side lengths for triangle: " + e.getMessage());
                    }
                } else {
                    System.out.println(" ");
                    System.out.println(ANSI_RED + "No Shape Found");
                    System.out.println(" ");
                }
            } else {
                Continue = "false";
                System.out.println(" ");
                System.out.println(ANSI_RED + "No QR Code Found" + ANSI_RESET);
                System.out.println(" ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
