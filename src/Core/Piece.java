package Core;

import java.awt.*;

public class Piece {

    private static int COUNTER = 0;
    public static final int WHITE =    COUNTER++;
    public static final int BLUE =     COUNTER++;
    public static final int GREEN =    COUNTER++;
    public static final int RED =      COUNTER++;
    public static final int ORANGE =   COUNTER++;
    public static final int YELLOW =   COUNTER++;
    public static final int NOTHING = -2;

    static final String[] COLORS_NAME_LOOKUP = new String[COUNTER];

    static final String[] COLORS_SHORTCODE_LOOKUP = new String[COUNTER];

    static {
        // color names spelled out
        COLORS_NAME_LOOKUP[WHITE] = "White";
        COLORS_NAME_LOOKUP[BLUE] = "Blue";
        COLORS_NAME_LOOKUP[GREEN] = "Green";
        COLORS_NAME_LOOKUP[RED] = "Red";
        COLORS_NAME_LOOKUP[ORANGE] = "Orange";
        COLORS_NAME_LOOKUP[YELLOW] = "Yellow";

        // color names (abbreviated versions)
        COLORS_SHORTCODE_LOOKUP[WHITE] = "W";
        COLORS_SHORTCODE_LOOKUP[BLUE] = "B";
        COLORS_SHORTCODE_LOOKUP[GREEN] = "G";
        COLORS_SHORTCODE_LOOKUP[RED] = "R";
        COLORS_SHORTCODE_LOOKUP[ORANGE] = "O";
        COLORS_SHORTCODE_LOOKUP[YELLOW] = "Y";
    }

    // todo: add bounds & error checking
    public static String getClrName(int clrIndex) {
        if(clrIndex == NOTHING) {
            return "NONE";
            //return "";
        }
        return COLORS_NAME_LOOKUP[clrIndex];
    }

    String colors = "";
    String basicName = "";

    // position on cube
    int x = 0, y = 0, z = 0;

    int top;
    int bottom;
    int back;
    int front;
    int left;
    int right;

    private Piece() {
        this.top = this.bottom = this.back = this.front = this.left = this.right = NOTHING;
    }

    // return an exact replica of this Piece
    public Piece clone() {
        Piece theClone = new Piece();
        theClone.colors = this.colors;
        theClone.basicName = this.basicName;
        theClone.x = this.x;
        theClone.y = this.y;
        theClone.z = this.z;
        theClone.top = this.top;
        theClone.bottom = this.bottom;
        theClone.left = this.left;
        theClone.right = this.right;
        theClone.front = this.front;
        theClone.back = this.back;
        return theClone;
    }

    public static Color getColorByName(String colorName) {
        switch(colorName.toUpperCase()) {
            case "WHITE": return Color.WHITE;
            case "BLUE": return Color.BLUE;
            case "GREEN": return Color.GREEN;
            case "RED": return Color.RED;
            case "ORANGE": return Color.ORANGE;
            case "YELLOW": return Color.YELLOW;
        }
        return Color.GREEN;
    }

    /*** TODO: change all strings from old orientation names ('yNeg', 'yP', etc) to new ones ('top', 'bottom', etc) */
    public Color getCurrentColor(String pieceSide) {
        int sideColorIndex = 0;
        switch(pieceSide.toUpperCase()) {
            case "YNEG": sideColorIndex = this.top; break;
            case "YP":   sideColorIndex = this.bottom; break;
            case "XNEG": sideColorIndex = this.left; break;
            case "XP":   sideColorIndex = this.right; break;
            case "ZNEG": sideColorIndex = this.front; break;
            case "ZP": sideColorIndex = this.back; break;
        }
        return getColorByName(COLORS_NAME_LOOKUP[sideColorIndex]);
    }

    public static String getOrientationNameByIndex(int orientationIndex) {
        switch(orientationIndex) {
            case 0: return "yNeg";
            case 1: return "yP";
            case 2: return "xNeg";
            case 3: return "xP";
            case 4: return "zNeg";
            case 5: return "zP";
        }
        return "";
    }

    public static int getOrientationIndexByName(String orientationName) {
        switch(orientationName) {
            case "yNeg": return 0;
            case "yP": return 1;
            case "xNeg": return 2;
            case "xP": return 3;
            case "zNeg": return 4;
            case "zP": return 5;
        }
        return -1;
    }

    public static Piece P(int top, int bottom, int left, int right, int front, int back) {
        Piece p = new Piece();
        p.top = top;
        p.bottom = bottom;
        p.left = left;
        p.right = right;
        p.front = front;
        p.back = back;
        return p;
    }

    public Piece rotateAroundYToLeft() {
        int _front = this.front;
        this.front = this.left;
        this.left = this.back;
        this.back = this.right;
        this.right = _front;
        return this;
    }

    public Piece rotateAroundYToRight() {
        int _front = this.front;
        this.front = this.right;
        this.right = this.back;
        this.back = this.left;
        this.left = _front;
        return this;
    }

    public Piece rotateAroundZToLeft() {
        int _top = this.top;
        this.top = this.right;
        this.right = this.bottom;
        this.bottom = this.left;
        this.left = _top;
        return this;
    }

    public Piece rotateAroundZToRight() {
        int _top = this.top;
        this.top = this.left;
        this.left = this.bottom;
        this.bottom = this.right;
        this.right = _top;
        return this;
    }

    public Piece rotateAroundXToBack() {
        int _top = this.top;
        this.top = this.front;
        this.front = this.bottom;
        this.bottom = this.back;
        this.back = _top;
        return this;
    }

    public Piece rotateAroundXToFront() {
        int _top = this.top;
        this.top = this.back;
        this.back = this.bottom;
        this.bottom = this.front;
        this.front = _top;
        return this;
    }

    public boolean isInSameState(Piece other) {
        return this.x == other.x && this.y == other.y && this.z == other.z
                && this.top == other.top
                && this.back == other.back
                && this.left == other.left
                && this.right == other.right
                && this.front == other.front
                && this.back == other.back;
    }

    public Piece setPosition(int y, int x, int z) {
        this.y = y;
        this.x = x;
        this.z = z;
        return this;
    }

    public String getPieceHash() {
        String pieceHash = "";

        pieceHash += top < 0 ? "N" : COLORS_SHORTCODE_LOOKUP[this.top];
        pieceHash += bottom < 0 ? "N" : COLORS_SHORTCODE_LOOKUP[this.bottom];
        pieceHash += left < 0 ? "N" : COLORS_SHORTCODE_LOOKUP[this.left];
        pieceHash += right < 0 ? "N" : COLORS_SHORTCODE_LOOKUP[this.right];
        pieceHash += front < 0 ? "N" : COLORS_SHORTCODE_LOOKUP[this.front];
        pieceHash += back < 0 ? "N" : COLORS_SHORTCODE_LOOKUP[this.back];

        return pieceHash;
    }

    public String toString() {
        return this.getDetails();
    }

    public String getDetails() {
        return "\n\tTop Face: " + getClrName(this.top)
                + "\n\t" + "Bottom Face: " + getClrName(this.bottom)
                + "\n\t" + "Left Face: " + getClrName(this.left)
                + "\n\t" + "Right Face: " + getClrName(this.right)
                + "\n\t" + "Front Face: " + getClrName(this.front)
                + "\n\t" + "Back Face: " + getClrName(this.back)
                + "\n\t" + "Position: " + "(y: " + y + ", x: " + x + ", z: " + z + ")";
    }

//    public static void main(String[] args) {
//        Piece piece = new Piece();
//
//        piece.top = WHITE;
//        piece.right = ORANGE;
//        piece.front = BLUE;
//
//        System.out.println("Before rotations:\n" + piece);
//
//        for(int r = 0; r < 4; r++) {
//            piece.rotateAroundZToLeft();
//            System.out.println("\n\n");
//            System.out.println("Piece after rotation " + r + ": " + piece);
//        }
//    }

}
