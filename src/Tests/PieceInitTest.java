package Tests;

import Core.Piece;

import static Core.Piece.*;

public class PieceInitTest {

    public static void doTests() {
        Piece p = P(WHITE, NOTHING, RED, NOTHING, BLUE, NOTHING);
        System.out.println(p.getDetails());
    }

    public static void main(String[] args) {
        doTests();
    }
}
