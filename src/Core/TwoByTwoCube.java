package Core;

import static Core.Piece.*;

public class TwoByTwoCube extends Cube {

    protected TwoByTwoCube() {
        //init();
    }

    // Set up cube with initial configuration
    protected void init() {
        matrix = new Piece[][][]
                {
                        {{P(WHITE, NOTHING, RED, NOTHING, BLUE, NOTHING), P(WHITE, NOTHING, RED, NOTHING, NOTHING, GREEN)}, {P(WHITE, NOTHING, NOTHING, ORANGE, BLUE, NOTHING), P(WHITE, NOTHING, NOTHING, ORANGE, NOTHING, GREEN)}}, // top layer
                        {{P(NOTHING, YELLOW, RED, NOTHING, BLUE, NOTHING), P(NOTHING, YELLOW, RED, NOTHING, NOTHING, GREEN)}, {P(NOTHING, YELLOW, NOTHING, ORANGE, BLUE, NOTHING), P(NOTHING, YELLOW, NOTHING, ORANGE, NOTHING, GREEN)}}, // bottom layer
                };

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < depth; z++) {
                    matrix[y][x][z].setPosition(y, x, z);
                }
            }
        }
    }

    public void rotateFront(int numTurns) {
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][1][0];
            matrix[0][1][0] = matrix[0][0][0].adjustOrientation(XP_EQUALS_YNEG_AND_YNEG_EQUALS_XNEG).setPosition(0,1,0);
            matrix[0][0][0] = matrix[1][0][0].adjustOrientation(YNEG_EQUALS_XNEG_AND_XNEG_EQUALS_YP).setPosition(0,0,0);
            matrix[1][0][0] = matrix[1][1][0].adjustOrientation(XNEG_EQUALS_YP_AND_YP_EQUALS_XP).setPosition(1,0,0);
            matrix[1][1][0] = temp.adjustOrientation(XP_EQUALS_YNEG_AND_YP_EQUALS_XP).setPosition(1,1,0);
        }
    }

    // rotate front counter clockwise
    public void rotateFrontCC(int numTurns) {
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][0];
            matrix[0][0][0] = matrix[0][1][0].adjustOrientation(XNEG_EQUALS_YNEG_AND_YNEG_EQUALS_XP).setPosition(0,0,0);
            matrix[0][1][0] = matrix[1][1][0].adjustOrientation(YNEG_EQUALS_XP_AND_XP_EQUALS_YP).setPosition(0,1,0);
            matrix[1][1][0] = matrix[1][0][0].adjustOrientation(XP_EQUALS_YP_AND_YP_EQUALS_XNEG).setPosition(1,1,0);
            matrix[1][0][0] = temp.adjustOrientation(YP_EQUALS_XNEG_AND_XNEG_EQUALS_YNEG).setPosition(1,0,0);
        }
    }

    public void rotateTop(int numTurns) {
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][1];
            matrix[0][0][1] = matrix[0][0][0].adjustOrientation(XNEG_EQUALS_ZNEG_AND_ZP_EQUALS_XNEG).setPosition(0,0,1);
            matrix[0][0][0] = matrix[0][1][0].adjustOrientation(XNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_XP).setPosition(0,0,0);
            matrix[0][1][0] = matrix[0][1][1].adjustOrientation(XP_EQUALS_ZP_AND_ZNEG_EQUALS_XP).setPosition(0,1,0);
            matrix[0][1][1] = temp.adjustOrientation(ZP_EQUALS_XNEG_AND_XP_EQUALS_ZP).setPosition(0,1,1);
        }
    }

    public void rotateTopCC(int numTurns) {
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][1];
            matrix[0][0][1] = matrix[0][1][1].adjustOrientation(ZP_EQUALS_XP_AND_XNEG_EQUALS_ZP).setPosition(0,0,1);
            matrix[0][1][1] = matrix[0][1][0].adjustOrientation(ZP_EQUALS_XP_AND_XP_EQUALS_ZNEG).setPosition(0,1,1);
            matrix[0][1][0] = matrix[0][0][0].adjustOrientation(ZNEG_EQUALS_XNEG_AND_XP_EQUALS_ZNEG).setPosition(0,1,0);
            matrix[0][0][0] = temp.adjustOrientation(ZNEG_EQUALS_XNEG_AND_XNEG_EQUALS_ZP).setPosition(0,0,0);
        }
    }

    public void rotateRight(int numTurns) {
        final int xPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][xPos][0];
            matrix[0][xPos][0] = matrix[1][xPos][0].adjustOrientation(YNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_YP).setPosition(0, xPos,0);
            matrix[1][xPos][0] = matrix[1][xPos][1].adjustOrientation(YP_EQUALS_ZP_AND_ZNEG_EQUALS_YP).setPosition(1, xPos,1);
            matrix[1][xPos][1] = matrix[0][xPos][1].adjustOrientation(ZP_EQUALS_YNEG_AND_YP_EQUALS_ZP).setPosition(1, xPos,1);
            matrix[0][xPos][1] = temp.adjustOrientation(ZP_EQUALS_YNEG_AND_YNEG_EQUALS_ZNEG).setPosition(0, xPos,1);
        }
    }

    public void rotateRightCC(int numTurns) {
        final int xPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][xPos][0];
            matrix[0][xPos][0] = matrix[0][xPos][1].adjustOrientation(ZNEG_EQUALS_YNEG_AND_YNEG_EQUALS_ZP).setPosition(0, xPos,0);
            matrix[0][xPos][1] = matrix[1][xPos][1].adjustOrientation(YNEG_EQUALS_ZP_AND_ZP_EQUALS_YP).setPosition(0, xPos,1);
            matrix[1][xPos][1] = matrix[1][xPos][0].adjustOrientation(YP_EQUALS_ZNEG_AND_ZP_EQUALS_YP).setPosition(1, xPos,1);
            matrix[1][xPos][0] = temp.adjustOrientation(ZNEG_EQUALS_YNEG_AND_YP_EQUALS_ZNEG).setPosition(1, xPos,0);
        }
    }

    // Clockwise - looking at left side straight on, rotates toward zNeg
    public void rotateLeft(int numTurns) {
        final int xPos = 0;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][xPos][0];
            matrix[0][xPos][0] = matrix[0][xPos][1].adjustOrientation(ZNEG_EQUALS_YNEG_AND_YNEG_EQUALS_ZP).setPosition(0,xPos,0);
            matrix[0][xPos][1] = matrix[1][xPos][1].adjustOrientation(YNEG_EQUALS_ZP_AND_ZP_EQUALS_YP).setPosition(0,xPos,1);
            matrix[1][xPos][1] = matrix[1][xPos][0].adjustOrientation(YP_EQUALS_ZNEG_AND_ZP_EQUALS_YP).setPosition(1,xPos,1);
            matrix[1][xPos][0] = temp.adjustOrientation(YP_EQUALS_ZNEG_AND_ZNEG_EQUALS_YNEG).setPosition(1, xPos,0);
        }
    }

    // rotate toward zP
    public void rotateLeftCC(int numTurns) {
        final int xPos = 0;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[1][xPos][0];
            matrix[1][xPos][0] = matrix[1][xPos][1].adjustOrientation(YP_EQUALS_ZP_AND_ZNEG_EQUALS_YP).setPosition(1, xPos,0);
            matrix[1][xPos][1] = matrix[0][xPos][1].adjustOrientation(ZP_EQUALS_YNEG_AND_YP_EQUALS_ZP).setPosition(1, xPos,1);
            matrix[0][xPos][1] = matrix[0][xPos][0].adjustOrientation(ZP_EQUALS_YNEG_AND_YNEG_EQUALS_ZNEG).setPosition(0, xPos,1);
            matrix[0][xPos][0] = temp.adjustOrientation(YNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_YP).setPosition(0, xPos,0);
        }
    }

    public void rotateBack(int numTurns) {
        final int zPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][zPos];
            matrix[0][0][zPos] = matrix[0][1][zPos].adjustOrientation(XNEG_EQUALS_YNEG_AND_YNEG_EQUALS_XP).setPosition(0,0, zPos);
            matrix[0][1][zPos] = matrix[1][1][zPos].adjustOrientation(YNEG_EQUALS_XP_AND_XP_EQUALS_YP).setPosition(0,1, zPos);
            matrix[1][1][zPos] = matrix[1][0][zPos].adjustOrientation(XP_EQUALS_YP_AND_YP_EQUALS_XNEG).setPosition(1,1, zPos);
            matrix[1][0][zPos] = temp.adjustOrientation(XNEG_EQUALS_YNEG_AND_YP_EQUALS_XNEG).setPosition(1,0, zPos);
        }
    }

    public void rotateBackCC(int numTurns) {
        final int zPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[1][1][zPos];
            matrix[1][1][zPos] = matrix[0][1][zPos].adjustOrientation(XP_EQUALS_YNEG_AND_YP_EQUALS_XP).setPosition(1,1, zPos);
            matrix[0][1][zPos] = matrix[0][0][zPos].adjustOrientation(XP_EQUALS_YNEG_AND_YNEG_EQUALS_XNEG).setPosition(0,1, zPos);
            matrix[0][0][zPos] = matrix[1][0][zPos].adjustOrientation(YNEG_EQUALS_XNEG_AND_XNEG_EQUALS_YP).setPosition(0,0, zPos);
            matrix[1][0][zPos] = temp.adjustOrientation(XNEG_EQUALS_YP_AND_YP_EQUALS_XP).setPosition(1,0, zPos);
        }
    }

    public void rotateBottom(int numTurns) {
        final int yPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[yPos][0][1];
            matrix[yPos][0][1] = matrix[yPos][1][1].adjustOrientation(ZP_EQUALS_XP_AND_XNEG_EQUALS_ZP).setPosition(yPos,0,1);
            matrix[yPos][1][1] = matrix[yPos][1][0].adjustOrientation(ZP_EQUALS_XP_AND_XP_EQUALS_ZNEG).setPosition(yPos,1,1);
            matrix[yPos][1][0] = matrix[yPos][0][0].adjustOrientation(ZNEG_EQUALS_XNEG_AND_XP_EQUALS_ZNEG).setPosition(yPos,1,0);
            matrix[yPos][0][0] = temp.adjustOrientation(ZNEG_EQUALS_XNEG_AND_XNEG_EQUALS_ZP).setPosition(yPos,0,0);
        }
    }

    public void rotateBottomCC(int numTurns) {
        final int yPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[yPos][1][1];
            matrix[yPos][1][1] = matrix[yPos][0][1].adjustOrientation(ZP_EQUALS_XNEG_AND_XP_EQUALS_ZP).setPosition(yPos,1,1);
            matrix[yPos][0][1] = matrix[yPos][0][0].adjustOrientation(XNEG_EQUALS_ZNEG_AND_ZP_EQUALS_XNEG).setPosition(yPos,0,1);
            matrix[yPos][0][0] = matrix[yPos][1][0].adjustOrientation(XNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_XP).setPosition(yPos,0,0);
            matrix[yPos][1][0] = temp.adjustOrientation(XP_EQUALS_ZP_AND_ZNEG_EQUALS_XP).setPosition(yPos,1,0);
        }
    }

    public String getFacesAsString() {
        StringBuilder faces = new StringBuilder();

        // print the TOP face
        faces.append("TOP:\n");
        for (int z = 1; z >= 0; z--) {
            for (int x = 0; x < 2; x++) {
                faces.append(getClrName(matrix[0][x][z].yNeg));
                if (x < 1) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the FRONT face
        faces.append("FRONT:\n");
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                faces.append(getClrName(matrix[y][x][0].zNeg));
                if (x < 1) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the BACK face
        faces.append("BACK:\n");
        for (int y = 0; y < 2; y++) {
            for (int x = 1; x >= 0; x--) {
                faces.append(getClrName(matrix[y][x][1].zP));
                if (x > 0) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the LEFT face
        faces.append("LEFT:\n");
        for (int y = 0; y < 2; y++) {
            for (int z = 1; z >= 0; z--) {
                faces.append(getClrName(matrix[y][0][z].xNeg));
                if (z > 0) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the RIGHT face
        faces.append("RIGHT:\n");
        for (int y = 0; y < 2; y++) {
            for (int z = 0; z < 2; z++) {
                faces.append(getClrName(matrix[y][1][z].xP));
                if (z < 1) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the BOTTOM face
        faces.append("BOTTOM:\n");
        for (int z = 0; z < 2; z++) {
            for (int x = 0; x < 2; x++) {
                faces.append(getClrName(matrix[1][x][z].yP));
                if (x < 1) faces.append(",");
            }
            faces.append("\n");
        }

        return faces.toString();
    }

    public static void main(String[] args) {
        Cube cube2x2 = CubeBuilder.makeCube(CubeType.TWO_BY_TWO);
        cube2x2.dumpFaces();
        System.out.println("---------------------------");
        cube2x2.rotateFront(2);
        cube2x2.rotateTop(3);
        cube2x2.rotateBack(1);
        cube2x2.rotateBackCC(1);
        cube2x2.rotateTopCC(3);
        cube2x2.rotateFrontCC(2);
        // should be same as before...:
        cube2x2.dumpFaces();
    }
}