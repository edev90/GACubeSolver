package Tests;

import Core.Piece;

import static Core.Piece.*;

public class PieceOrientationTest {

    private static final void doTests() {
        final int yNeg = WHITE;
        final int yP = NOTHING;
        final int xNeg = RED;
        final int xP = NOTHING;
        final int zNeg = BLUE;
        final int zP = NOTHING;
        Piece p = Piece.P(yNeg, yP, xNeg, xP, zNeg, zP);
        p.setPosition(0, 0, 0);
        System.out.println("Piece details as of right now: " + p.getDetails());
    }

    public static void main(String[] args) {
        doTests();
    }
}
