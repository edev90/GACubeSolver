package Core;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/***
 *
 * TODO: refactor
 *
 * */

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

    static {
        COLORS_NAME_LOOKUP[WHITE] = "White";
        COLORS_NAME_LOOKUP[BLUE] = "Blue";
        COLORS_NAME_LOOKUP[GREEN] = "Green";
        COLORS_NAME_LOOKUP[RED] = "Red";
        COLORS_NAME_LOOKUP[ORANGE] = "Orange";
        COLORS_NAME_LOOKUP[YELLOW] = "Yellow";
    }

    // todo: add bounds & error checking
    public static String getClrName(int clrIndex) {
        if(clrIndex == NOTHING) {
            return "NONE";
        }
        return COLORS_NAME_LOOKUP[clrIndex];
    }

    String colors = "";
    String basicName = "";
    int x = 0, y = 0, z = 0;
    int yNeg, yP, xNeg, xP, zNeg,  zP;

    private Piece(){
        this.yNeg = this.yP = this.xNeg = this.xP = this.zNeg = this.zP = NOTHING;
    }

    // return an exact replica of this Piece
    public Piece clone() {
        Piece theClone = new Piece();
        theClone.colors = this.colors;
        theClone.basicName = this.basicName;
        theClone.x = this.x;
        theClone.y = this.y;
        theClone.z = this.z;
        theClone.yNeg = this.yNeg;
        theClone.yP = this.yP;
        theClone.xNeg = this.xNeg;
        theClone.xP = this.xP;
        theClone.zNeg = this.zNeg;
        theClone.zP = this.zP;
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

    public Color getCurrentColor(String pieceSide) {
        int sideColorIndex = 0;
        switch(pieceSide.toUpperCase()) {
            case "YNEG": sideColorIndex = this.yNeg; break;
            case "YP":   sideColorIndex = this.yP; break;
            case "XNEG": sideColorIndex = this.xNeg; break;
            case "XP":   sideColorIndex = this.xP; break;
            case "ZNEG": sideColorIndex = this.zNeg; break;
            case "ZP": sideColorIndex = this.zP; break;
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

    // helper - construct a new Piece. Useful in case we change the underlying structure of the Piece class
    public static Piece P(String colors) {
        Piece p = new Piece();
        ArrayList<String> parts = new ArrayList(Arrays.asList(colors.split(":")));
        System.out.println("parts: " + parts);
        return p;
    }

    public static Piece P(int yNeg, int yP, int xNeg, int xP, int zNeg, int zP) {
        Piece p = new Piece();
        p.yNeg = yNeg;
        p.yP = yP;
        p.xNeg = xNeg;
        p.xP = xP;
        p.zNeg = zNeg;
        p.zP = zP;
        return p;
    }

    public static final int YNEG_EQUALS_XP_AND_XNEG_EQUALS_YNEG = 0;
    public static final int XNEG_EQUALS_YP_AND_YP_EQUALS_XNEG = 1;
    public static final int XP_EQUALS_YP_AND_YNEG_EQUALS_XP = 2;
    public static final int YNEG_EQUALS_XP_AND_XP_EQUALS_YP = 3;
    public static final int XP_EQUALS_YNEG_AND_YNEG_EQUALS_XNEG = 4;
    public static final int YNEG_EQUALS_XNEG_AND_XNEG_EQUALS_YP = 5;
    public static final int XP_EQUALS_YP_AND_YP_EQUALS_XNEG = 6;
    public static final int YP_EQUALS_XNEG_AND_XNEG_EQUALS_YNEG = 7;
    public static final int XNEG_EQUALS_YP_AND_YP_EQUALS_XP = 8;
    public static final int XP_EQUALS_YNEG_AND_YP_EQUALS_XP = 9;
    public static final int XP_EQUALS_YP = 10;
    public static final int YNEG_EQUALS_XP = 11;
    public static final int YNEG_EQUALS_XNEG = 12;
    public static final int XNEG_EQUALS_YP = 13;
    public static final int YP_EQUALS_XP = 14;
    public static final int XP_EQUALS_YNEG = 15;

    public static final int XNEG_EQUALS_ZNEG_AND_ZP_EQUALS_XNEG = 16; // (clear zNeg)
    public static final int XNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_XP = 17; // (clear XP)
    public static final int ZNEG_EQUALS_XP_AND_XP_EQUALS_ZNEG = 18; // (clear zNeg)
    public static final int XP_EQUALS_ZNEG_AND_ZP_EQUALS_XP = 19;    // (clear zNeg)
    public static final int ZP_EQUALS_XP_AND_XNEG_EQUALS_ZP = 20;     // (clear XP)
    public static final int ZP_EQUALS_XNEG_AND_XP_EQUALS_ZP = 21; // (clear xNeg)
    public static final int XP_EQUALS_ZP_AND_ZNEG_EQUALS_XP = 22; // (clear zP)
    public static final int XP_EQUALS_ZNEG = 23; // (clear zNeg)
    public static final int ZNEG_EQUALS_XP = 24; // (clear XP)
    public static final int XNEG_EQUALS_ZNEG = 25; // (clear zNeg)
    public static final int ZNEG_EQUALS_XNEG = 26; // (clear xNeg)
    public static final int XP_EQUALS_ZP = 27; // (clear zP)
    public static final int ZP_EQUALS_XP = 28; // (clear XP)
    public static final int ZP_EQUALS_XNEG = 29; // (clear xNeg)

    public static final int YNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_YP = 30;
    public static final int YP_EQUALS_ZP_AND_ZNEG_EQUALS_YP = 31;
    public static final int ZP_EQUALS_YNEG_AND_YP_EQUALS_ZP = 32;
    public static final int ZP_EQUALS_YNEG_AND_YNEG_EQUALS_ZNEG = 33;
    public static final int ZP_EQUALS_YNEG = 34;
    public static final int YNEG_EQUALS_ZNEG = 35;
    public static final int ZNEG_EQUALS_YP = 36;
    public static final int YP_EQUALS_ZP = 37;

    public static final int ZNEG_EQUALS_YNEG_AND_YNEG_EQUALS_ZP = 38;
    public static final int YNEG_EQUALS_ZP_AND_ZP_EQUALS_YP = 39;
    public static final int YP_EQUALS_ZNEG_AND_ZP_EQUALS_YP = 40;
    public static final int YP_EQUALS_ZNEG_AND_ZNEG_EQUALS_YNEG = 41;
    public static final int YNEG_EQUALS_ZP = 42;
    public static final int ZP_EQUALS_YP = 43;
    public static final int YP_EQUALS_ZNEG = 44;
    public static final int ZNEG_EQUALS_YNEG = 45;

    public static final int XNEG_EQUALS_YNEG_AND_YNEG_EQUALS_XP = 46;
    public static final int XNEG_EQUALS_YNEG_AND_YP_EQUALS_XNEG = 47;
    public static final int YP_EQUALS_XNEG = 48;
    public static final int XNEG_EQUALS_YNEG = 49;
    public static final int ZP_EQUALS_XP_AND_XP_EQUALS_ZNEG = 50;
    public static final int ZNEG_EQUALS_XNEG_AND_XP_EQUALS_ZNEG = 52;
    public static final int ZNEG_EQUALS_XNEG_AND_XNEG_EQUALS_ZP = 53;
    public static final int XNEG_EQUALS_ZP = 54;

    public static final int ZNEG_EQUALS_YNEG_AND_YP_EQUALS_ZNEG = 55;

    /* adjust orientation and return the same piece that was adjusted */
    public Piece adjustOrientation(int type) {
        int _yNeg = this.yNeg;
        int _yP = this.yP;
        int _xNeg = this.xNeg;
        int _xP = this.xP;
        int _zNeg = this.zNeg;
        int _zP = this.zP;

        switch(type) {
            case YNEG_EQUALS_XP_AND_XNEG_EQUALS_YNEG:
                this.yNeg = _xP;
                this.xNeg = _yNeg;
                break;
            case XNEG_EQUALS_YP_AND_YP_EQUALS_XNEG:
                this.xNeg = _yP;
                this.yP = _xNeg;
                break;
            case XP_EQUALS_YP_AND_YNEG_EQUALS_XP:
                this.xP = _yP;
                this.yNeg = xP;
                break;
            case YNEG_EQUALS_XP_AND_XP_EQUALS_YP:
                this.yNeg = _xP;
                this.xP = _yP;
                this.yP = NOTHING;
                break;
            case XP_EQUALS_YNEG_AND_YNEG_EQUALS_XNEG:
                this.xP = _yNeg;
                this.yNeg = _xNeg;
                this.xNeg = NOTHING;
                break;
            case YNEG_EQUALS_XNEG_AND_XNEG_EQUALS_YP:
                this.yNeg = _xNeg;
                this.xNeg = _yP;
                this.yP = NOTHING;
                break;
            case XP_EQUALS_YP_AND_YP_EQUALS_XNEG:
                this.xP = _yP;
                this.yP = _xNeg;
                this.xNeg = NOTHING;
                break;
            case YP_EQUALS_XNEG_AND_XNEG_EQUALS_YNEG:
                this.yP = _xNeg;
                this.xNeg = _yNeg;
                this.yNeg = NOTHING;
                break;
            case XNEG_EQUALS_YP_AND_YP_EQUALS_XP:
                this.xNeg = _yP;
                this.yP = _xP;
                this.xP = NOTHING;
                break;
            case XP_EQUALS_YNEG_AND_YP_EQUALS_XP:
                this.xP = _yNeg;
                this.yP = _xP;
                this.yNeg = NOTHING;
                break;
            case XP_EQUALS_YP:
                this.xP = _yP;
                this.yP = NOTHING;
                break;
            case YNEG_EQUALS_XP:
                this.yNeg = _xP;
                this.xP = NOTHING;
                break;
            case YNEG_EQUALS_XNEG:
                this.yNeg = _xNeg;
                this.xNeg = NOTHING;
                break;
            case XNEG_EQUALS_YP:
                this.xNeg = _yP;
                this.yP = NOTHING;
                break;
            case YP_EQUALS_XP:
                this.yP = _xP;
                this.xP = NOTHING;
                break;
            case XP_EQUALS_YNEG:
                this.xP = _yNeg;
                this.yNeg = NOTHING;
                break;
            case XNEG_EQUALS_ZNEG_AND_ZP_EQUALS_XNEG:
                this.xNeg = _zNeg;
                this.zP = _xNeg;
                this.zNeg = NOTHING;
                break;
            case XNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_XP:
                this.xNeg = _zNeg;
                this.zNeg = _xP;
                this.xP = NOTHING;
                break;
            case ZNEG_EQUALS_XP_AND_XP_EQUALS_ZNEG:
                this.zNeg = _xP;
                this.xP = _zNeg;
                this.zNeg = NOTHING;
                break;
            case XP_EQUALS_ZNEG_AND_ZP_EQUALS_XP:
                this.xP = _zNeg;
                this.zP = _xP;
                this.zNeg = NOTHING;
                break;
            case ZP_EQUALS_XP_AND_XNEG_EQUALS_ZP:
                this.zP = _xP;
                this.xNeg = _zP;
                this.xP = NOTHING;
                break;
            case ZP_EQUALS_XNEG_AND_XP_EQUALS_ZP:
                this.zP = _xNeg;
                this.xP = _zP;
                this.xNeg = NOTHING;
                break;
            case XP_EQUALS_ZP_AND_ZNEG_EQUALS_XP:
                this.xP = _zP;
                this.zNeg = _xP;
                this.zP = NOTHING;
                break;
            case XP_EQUALS_ZNEG:
                this.xP = _zNeg;
                this.zNeg = NOTHING;
                break;
            case ZNEG_EQUALS_XP:
                this.zNeg = _xP;
                this.xP = NOTHING;
                break;
            case XNEG_EQUALS_ZNEG:
                this.xNeg = _zNeg;
                this.zNeg = NOTHING;
                break;
            case ZNEG_EQUALS_XNEG:
                this.zNeg = _xNeg;
                this.xNeg = NOTHING;
                break;
            case XP_EQUALS_ZP:
                this.xP = _zP;
                this.zP = NOTHING;
                break;
            case ZP_EQUALS_XP:
                this.zP = _xP;
                this.xP = NOTHING;
                break;
            case ZP_EQUALS_XNEG:
                this.zP = _xNeg;
                this.xNeg = NOTHING;
                break;
            case YNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_YP:
                this.yNeg = _zNeg;
                this.zNeg = _yP;
                this.yP = NOTHING;
                break;
            case YP_EQUALS_ZP_AND_ZNEG_EQUALS_YP:
                this.yP = _zP;
                this.zNeg = _yP;
                this.zP = NOTHING;
                break;
            case ZP_EQUALS_YNEG_AND_YP_EQUALS_ZP:
                this.zP = _yNeg;
                this.yP = _zP;
                this.yNeg = NOTHING;
                break;
            case ZP_EQUALS_YNEG_AND_YNEG_EQUALS_ZNEG:
                this.zP = _yNeg;
                this.yNeg = _zNeg;
                this.zNeg = NOTHING;
                break;
            case ZP_EQUALS_YNEG:
                this.zP = _yNeg;
                this.yNeg = NOTHING;
                break;
            case YNEG_EQUALS_ZNEG:
                this.yNeg = _zNeg;
                this.zNeg = NOTHING;
                break;
            case ZNEG_EQUALS_YP:
                this.zNeg = _yP;
                this.yP = NOTHING;
                break;
            case YP_EQUALS_ZP:
                this.yP = _zP;
                this.zP = NOTHING;
                break;
            case ZNEG_EQUALS_YNEG_AND_YNEG_EQUALS_ZP:
                this.zNeg = _yNeg;
                this.yNeg = _zP;
                this.zP = NOTHING;
                break;
            case YNEG_EQUALS_ZP_AND_ZP_EQUALS_YP:
                this.yNeg = _zP;
                this.zP = _yP;
                this.yP = NOTHING;
                break;
            case YP_EQUALS_ZNEG_AND_ZP_EQUALS_YP:
                this.yP = _zNeg;
                this.zP = _yP;
                this.zNeg = NOTHING;
                break;
            case YP_EQUALS_ZNEG_AND_ZNEG_EQUALS_YNEG:
                this.yP = _zNeg;
                this.zNeg = _yNeg;
                this.yNeg = NOTHING;
                break;
            case YNEG_EQUALS_ZP:
                this.yNeg = _zP;
                this.zP = NOTHING;
                break;
            case ZP_EQUALS_YP:
                this.zP = _yP;
                this.yP = NOTHING;
                break;
            case YP_EQUALS_ZNEG:
                this.yP = _zNeg;
                this.zNeg = NOTHING;
                break;
            case ZNEG_EQUALS_YNEG:
                this.zNeg = _yNeg;
                this.yNeg = NOTHING;
                break;
            case XNEG_EQUALS_YNEG_AND_YNEG_EQUALS_XP:
                this.xNeg = _yNeg;
                this.yNeg = _xP;
                this.xP = NOTHING;
                break;
            case XNEG_EQUALS_YNEG_AND_YP_EQUALS_XNEG:
                this.xNeg = _yNeg;
                this.yP = _xNeg;
                this.yNeg = NOTHING;
                break;
            case YP_EQUALS_XNEG:
                this.yP = _xNeg;
                this.xNeg = NOTHING;
                break;
            case XNEG_EQUALS_YNEG:
                this.xNeg = _yNeg;
                this.yNeg = NOTHING;
                break;
            case ZP_EQUALS_XP_AND_XP_EQUALS_ZNEG:
                this.zP = _xP;
                this.xP = _zNeg;
                this.zNeg = NOTHING;
                break;
            case ZNEG_EQUALS_XNEG_AND_XP_EQUALS_ZNEG:
                this.zNeg = _xNeg;
                this.xP = _zNeg;
                this.xNeg = NOTHING;
                break;
            case ZNEG_EQUALS_XNEG_AND_XNEG_EQUALS_ZP:
                this.zNeg = _xNeg;
                this.xNeg = _zP;
                this.zP = NOTHING;
                break;
            case XNEG_EQUALS_ZP:
                this.xNeg = _zP;
                this.zP = NOTHING;
                break;
            case ZNEG_EQUALS_YNEG_AND_YP_EQUALS_ZNEG:
                this.zNeg = _yNeg;
                this.yP = _zNeg;
                this.yNeg = NOTHING;
        }

        return this;
    }

    public boolean isInSameState(Piece other) {
        return this.x == other.x && this.y == other.y && this.z == other.z
                && this.yNeg == other.yNeg
                && this.yP == other.yP
                && this.xNeg == other.xNeg
                && this.xP == other.xP
                && this.zNeg == other.zNeg
                && this.zP == other.zP;
    }

    public Piece setPosition(int y, int x, int z) {
        this.y = y;
        this.x = x;
        this.z = z;
        return this;
    }

    public String toString() {return this.basicName;}

    public String getDetails() {
        return "\n\tyNeg: " + getClrName(this.yNeg)
               + "\n\t" + "yP: " + getClrName(this.yP)
               + "\n\t" + "xNeg: " + getClrName(this.xNeg)
               + "\n\t" + "xP: " + getClrName(this.xP)
               + "\n\t" + "zNeg: " + getClrName(this.zNeg)
               + "\n\t" + "zP: " + getClrName(this.zP)
               + "\n\t" + "Position: " + "(y: " + y + ", x: " + x + ", z: " + z + ")";
    }
}