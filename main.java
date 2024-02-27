import swiftbot.*;

public class main {
    public static void main(String[] args) {

        SwiftBotAPI API = new SwiftBotAPI();

        square obj1 = new square(API);
        triangle obj2 = new triangle(API);
        qrCode obj3 = new qrCode(API);

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
                System.out.println("Triangle cannot be formed with the given sides.");
                System.out.println(side2);
            }
        }
    }
}
