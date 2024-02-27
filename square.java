import swiftbot.*;

public class square {

    private SwiftBotAPI API;

    public square(SwiftBotAPI api) {
        this.API = api;
    }

    public void drawSquare(int side) {

        int toMove = side * 10;

        for (int i = 0; i < 4; i++) {

            API.move(100, 100, toMove);
            API.move(0, 0, 1000);
            // turns the swiftbot by 90 degrees exactly 3 times to complete the square
            if (i < 3) {
                API.move(100, -100, 400); // 400 seconds is 90 degrees
            }

            // sets the underlight to green when the drawing is finished
            if (i == 3) {
                int[] colourToLightUp = { 0, 0, 255 };
                API.fillUnderlights(colourToLightUp);
            }
        }
        System.out.println("swiftbot is drawing a square");

    }

}
