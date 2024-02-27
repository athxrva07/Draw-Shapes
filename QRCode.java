import swiftbot.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class qrCode {

    final String ANSI_RED = "\u001B[31m";
    final String ANSI_RESET = "\u001B[0m";

    private SwiftBotAPI API;

    public qrCode(SwiftBotAPI api) {
        this.API = api;
    }

    public int side;
    public int side1;
    public int side2;
    public int side3;
    public String shape;

    public void decodeQR() {

        ArrayList<String> partsList = new ArrayList<>();

        try {
            // SwiftBotAPI API = new SwiftBotAPI();
            BufferedImage img = API.getQRImage();

            String decodedText = API.decodeQRImage(img);

            if (!decodedText.isEmpty()) {
                // Split the decoded text at the space character (" ")
                String[] parts = decodedText.split(" ");

                // Add the parts to the ArrayList
                for (String part : parts) {
                    partsList.add(part);
                }

                // Check if the first element is "S" and print "Square"
                if (partsList.get(0).equals("S")) {

                    String decodedShape = "S";
                    shape = decodedShape;
                    String s = partsList.get(1);
                    int squareSide = Integer.parseInt(s.replaceAll("\\D", ""));
                    side = squareSide;
                } else if (partsList.get(0).equals("T")) {

                    String decodedShape = "T";
                    shape = decodedShape;

                    side1 = Integer.parseInt(partsList.get(1));
                    side2 = Integer.parseInt(partsList.get(2));
                    side3 = Integer.parseInt(partsList.get(3));

                } else {
                    System.out.println(" ");
                    System.out.println(ANSI_RED + "No Shape Found");
                    System.out.println(" ");
                }
            } else {
                System.out.println(" ");
                System.out.println(ANSI_RED + "No QR Code Found" + ANSI_RESET);
                System.out.println(" ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
