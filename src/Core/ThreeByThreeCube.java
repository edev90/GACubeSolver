package Core;

import static Core.Piece.*;

public class ThreeByThreeCube extends Cube {

    protected ThreeByThreeCube() {
        //init();
    }

    // Set up cube with initial configuration
    protected void init() {
        matrix = new Piece[][][]
                {
                        {{P(WHITE, NOTHING, RED, NOTHING, BLUE, NOTHING), P(WHITE, NOTHING, RED, NOTHING, NOTHING, NOTHING), P(WHITE, NOTHING, RED, NOTHING, NOTHING, GREEN)}, {P(WHITE, NOTHING, NOTHING, NOTHING, BLUE, NOTHING), P(WHITE, NOTHING, NOTHING, NOTHING, NOTHING, NOTHING), P(WHITE, NOTHING, NOTHING, NOTHING, NOTHING, GREEN)}, {P(WHITE, NOTHING, NOTHING, ORANGE, BLUE, NOTHING), P(WHITE, NOTHING, NOTHING, ORANGE, NOTHING, NOTHING), P(WHITE, NOTHING, NOTHING, ORANGE, NOTHING, GREEN)}}, // top layer
                        {{P(NOTHING, NOTHING, RED, NOTHING, BLUE, NOTHING), P(NOTHING, NOTHING, RED, NOTHING, NOTHING, NOTHING), P(NOTHING, NOTHING, RED, NOTHING, NOTHING, GREEN)}, {P(NOTHING, NOTHING, NOTHING, NOTHING, BLUE, NOTHING), P(NOTHING, NOTHING, NOTHING, NOTHING, NOTHING, NOTHING), P(NOTHING, NOTHING, NOTHING, NOTHING, NOTHING, GREEN)}, {P(NOTHING, NOTHING, NOTHING, ORANGE, BLUE, NOTHING), P(NOTHING, NOTHING, NOTHING, ORANGE, NOTHING, NOTHING), P(NOTHING, NOTHING, NOTHING, ORANGE, NOTHING, GREEN)}}, // middle layer
                        {{P(NOTHING, YELLOW, RED, NOTHING, BLUE, NOTHING), P(NOTHING, YELLOW, RED, NOTHING, NOTHING, NOTHING), P(NOTHING, YELLOW, RED, NOTHING, NOTHING, GREEN)}, {P(NOTHING, YELLOW, NOTHING, NOTHING, BLUE, NOTHING), P(NOTHING, YELLOW, NOTHING, NOTHING, NOTHING, NOTHING), P(NOTHING, YELLOW, NOTHING, NOTHING, NOTHING, GREEN)}, {P(NOTHING, YELLOW, NOTHING, ORANGE, BLUE, NOTHING), P(NOTHING, YELLOW, NOTHING, ORANGE, NOTHING, NOTHING), P(NOTHING, YELLOW, NOTHING, ORANGE, NOTHING, GREEN)}}, // bottom layer
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
            Piece temp = matrix[0][2][0];
            matrix[0][2][0] = matrix[0][0][0].adjustOrientation(XP_EQUALS_YNEG_AND_YNEG_EQUALS_XNEG).setPosition(0,2,0);
            matrix[0][0][0] = matrix[2][0][0].adjustOrientation(YNEG_EQUALS_XNEG_AND_XNEG_EQUALS_YP).setPosition(0,0,0);
            matrix[2][0][0] = matrix[2][2][0].adjustOrientation(XNEG_EQUALS_YP_AND_YP_EQUALS_XP).setPosition(2,0,0);
            matrix[2][2][0] = temp.adjustOrientation(XP_EQUALS_YNEG_AND_YP_EQUALS_XP).setPosition(2,2,0);

            // edges
            temp = matrix[2][1][0];
            matrix[2][1][0] = matrix[1][2][0].adjustOrientation(YP_EQUALS_XP).setPosition(2,1,0);
            matrix[1][2][0] = matrix[0][1][0].adjustOrientation(XP_EQUALS_YNEG).setPosition(1,2,0);
            matrix[0][1][0] = matrix[1][0][0].adjustOrientation(YNEG_EQUALS_XNEG).setPosition(0,1,0);
            matrix[1][0][0] = temp.adjustOrientation(XNEG_EQUALS_YP).setPosition(1,0,0);
        }
    }

    // rotate front counter clockwise
    public void rotateFrontCC(int numTurns) {
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][0];
            matrix[0][0][0] = matrix[0][2][0].adjustOrientation(XNEG_EQUALS_YNEG_AND_YNEG_EQUALS_XP).setPosition(0,0,0);
            matrix[0][2][0] = matrix[2][2][0].adjustOrientation(YNEG_EQUALS_XP_AND_XP_EQUALS_YP).setPosition(0,2,0);
            matrix[2][2][0] = matrix[2][0][0].adjustOrientation(XP_EQUALS_YP_AND_YP_EQUALS_XNEG).setPosition(2,2,0);
            matrix[2][0][0] = temp.adjustOrientation(YP_EQUALS_XNEG_AND_XNEG_EQUALS_YNEG).setPosition(2,0,0);

            // edges
            temp = matrix[0][1][0];
            matrix[0][1][0] = matrix[1][2][0].adjustOrientation(YNEG_EQUALS_XP).setPosition(0,1,0);
            matrix[1][2][0] = matrix[2][1][0].adjustOrientation(XP_EQUALS_YP).setPosition(1,2,0);
            matrix[2][1][0] = matrix[1][0][0].adjustOrientation(YP_EQUALS_XNEG).setPosition(2,1,0);
            matrix[1][0][0] = temp.adjustOrientation(XNEG_EQUALS_YNEG).setPosition(1,0,0);
        }
    }

    public void rotateTop(int numTurns) {
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][2];
            matrix[0][0][2] = matrix[0][0][0].adjustOrientation(XNEG_EQUALS_ZNEG_AND_ZP_EQUALS_XNEG).setPosition(0,0,2);
            matrix[0][0][0] = matrix[0][2][0].adjustOrientation(XNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_XP).setPosition(0,0,0);
            matrix[0][2][0] = matrix[0][2][2].adjustOrientation(XP_EQUALS_ZP_AND_ZNEG_EQUALS_XP).setPosition(0,2,0);
            matrix[0][2][2] = temp.adjustOrientation(ZP_EQUALS_XNEG_AND_XP_EQUALS_ZP).setPosition(0,2,2);

            // edges
            temp = matrix[0][1][2];
            matrix[0][1][2] = matrix[0][0][1].adjustOrientation(ZP_EQUALS_XNEG).setPosition(0,1,2);
            matrix[0][0][1] = matrix[0][1][0].adjustOrientation(XNEG_EQUALS_ZNEG).setPosition(0,0,1);
            matrix[0][1][0] = matrix[0][2][1].adjustOrientation(ZNEG_EQUALS_XP).setPosition(0,1,0);
            matrix[0][2][1] = temp.adjustOrientation(XP_EQUALS_ZP).setPosition(0,2,1);
        }
    }

    public void rotateTopCC(int numTurns) {
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][2];
            matrix[0][0][2] = matrix[0][2][2].adjustOrientation(ZP_EQUALS_XP_AND_XNEG_EQUALS_ZP).setPosition(0,0,2);
            matrix[0][2][2] = matrix[0][2][0].adjustOrientation(ZP_EQUALS_XP_AND_XP_EQUALS_ZNEG).setPosition(0,2,2);
            matrix[0][2][0] = matrix[0][0][0].adjustOrientation(ZNEG_EQUALS_XNEG_AND_XP_EQUALS_ZNEG).setPosition(0,2,0);
            matrix[0][0][0] = temp.adjustOrientation(ZNEG_EQUALS_XNEG_AND_XNEG_EQUALS_ZP).setPosition(0,0,0);

            // edges
            temp = matrix[0][1][2];
            matrix[0][1][2] = matrix[0][2][1].adjustOrientation(ZP_EQUALS_XP).setPosition(0,1,2);
            matrix[0][2][1] = matrix[0][1][0].adjustOrientation(XP_EQUALS_ZNEG).setPosition(0,2,1);
            matrix[0][1][0] = matrix[0][0][1].adjustOrientation(ZNEG_EQUALS_XNEG).setPosition(0,1,0);
            matrix[0][0][1] = temp.adjustOrientation(XNEG_EQUALS_ZP).setPosition(0,0,1);
        }
    }

    public void rotateRight(int numTurns) {
        final int xPos = 2;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][xPos][0];
            matrix[0][xPos][0] = matrix[2][xPos][0].adjustOrientation(YNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_YP).setPosition(0,xPos,0);
            matrix[2][xPos][0] = matrix[2][xPos][2].adjustOrientation(YP_EQUALS_ZP_AND_ZNEG_EQUALS_YP).setPosition(2,xPos,0);
            matrix[2][xPos][2] = matrix[0][xPos][2].adjustOrientation(ZP_EQUALS_YNEG_AND_YP_EQUALS_ZP).setPosition(2,xPos,2);
            matrix[0][xPos][2] = temp.adjustOrientation(ZP_EQUALS_YNEG_AND_YNEG_EQUALS_ZNEG).setPosition(0,xPos,2);

            // edges
            temp = matrix[1][xPos][2];
            matrix[1][xPos][2] = matrix[0][xPos][1].adjustOrientation(ZP_EQUALS_YNEG).setPosition(1,xPos,2);
            matrix[0][xPos][1] = matrix[1][xPos][0].adjustOrientation(YNEG_EQUALS_ZNEG).setPosition(0,xPos,1);
            matrix[1][xPos][0] = matrix[2][xPos][1].adjustOrientation(ZNEG_EQUALS_YP).setPosition(1,xPos,0);
            matrix[2][xPos][1] = temp.adjustOrientation(YP_EQUALS_ZP).setPosition(2,xPos,1);
        }
    }

    public void rotateRightCC(int numTurns) {
        final int xPos = 2;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][xPos][0];
            matrix[0][xPos][0] = matrix[0][xPos][2].adjustOrientation(ZNEG_EQUALS_YNEG_AND_YNEG_EQUALS_ZP).setPosition(0,xPos,0);
            matrix[0][xPos][2] = matrix[2][xPos][2].adjustOrientation(YNEG_EQUALS_ZP_AND_ZP_EQUALS_YP).setPosition(0,xPos,2);
            matrix[2][xPos][2] = matrix[2][xPos][0].adjustOrientation(YP_EQUALS_ZNEG_AND_ZP_EQUALS_YP).setPosition(2,xPos,2);
            matrix[2][xPos][0] = temp.adjustOrientation(ZNEG_EQUALS_YNEG_AND_YP_EQUALS_ZNEG).setPosition(2,xPos,0);

            // edges
            temp = matrix[0][xPos][1];
            matrix[0][xPos][1] = matrix[1][xPos][2].adjustOrientation(YNEG_EQUALS_ZP).setPosition(0,xPos,1);
            matrix[1][xPos][2] = matrix[2][xPos][1].adjustOrientation(ZP_EQUALS_YP).setPosition(1,xPos,2);
            matrix[2][xPos][1] = matrix[1][xPos][0].adjustOrientation(YP_EQUALS_ZNEG).setPosition(2,xPos,1);
            matrix[1][xPos][0] = temp.adjustOrientation(ZNEG_EQUALS_YNEG).setPosition(1,xPos,0);
        }
    }

    // Clockwise - looking at left side straight on, rotates toward zNeg
    public void rotateLeft(int numTurns) {
        final int xPos = 0;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][xPos][0];
            matrix[0][xPos][0] = matrix[0][xPos][2].adjustOrientation(ZNEG_EQUALS_YNEG_AND_YNEG_EQUALS_ZP).setPosition(0,xPos,0);
            matrix[0][xPos][2] = matrix[2][xPos][2].adjustOrientation(YNEG_EQUALS_ZP_AND_ZP_EQUALS_YP).setPosition(0,xPos,2);
            matrix[2][xPos][2] = matrix[2][xPos][0].adjustOrientation(YP_EQUALS_ZNEG_AND_ZP_EQUALS_YP).setPosition(2,xPos,2);
            matrix[2][xPos][0] = temp.adjustOrientation(YP_EQUALS_ZNEG_AND_ZNEG_EQUALS_YNEG).setPosition(2,xPos,0);

            // edges
            temp = matrix[0][xPos][1];
            matrix[0][xPos][1] = matrix[1][xPos][2].adjustOrientation(YNEG_EQUALS_ZP).setPosition(0,xPos,1);
            matrix[1][xPos][2] = matrix[2][xPos][1].adjustOrientation(ZP_EQUALS_YP).setPosition(1,xPos,2);
            matrix[2][xPos][1] = matrix[1][xPos][0].adjustOrientation(YP_EQUALS_ZNEG).setPosition(2,xPos,1);
            matrix[1][xPos][0] = temp.adjustOrientation(ZNEG_EQUALS_YNEG).setPosition(1,xPos,0);
        }
    }

    // rotate toward zP
    public void rotateLeftCC(int numTurns) {
        final int xPos = 0;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[2][xPos][0];
            matrix[2][xPos][0] = matrix[2][xPos][2].adjustOrientation(YP_EQUALS_ZP_AND_ZNEG_EQUALS_YP).setPosition(2,xPos,0);
            matrix[2][xPos][2] = matrix[0][xPos][2].adjustOrientation(ZP_EQUALS_YNEG_AND_YP_EQUALS_ZP).setPosition(2,xPos,2);
            matrix[0][xPos][2] = matrix[0][xPos][0].adjustOrientation(ZP_EQUALS_YNEG_AND_YNEG_EQUALS_ZNEG).setPosition(0,xPos,2);
            matrix[0][xPos][0] = temp.adjustOrientation(YNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_YP).setPosition(0,xPos,0);

            // edges
            temp = matrix[2][xPos][1];
            matrix[2][xPos][1] = matrix[1][xPos][2].adjustOrientation(YP_EQUALS_ZP).setPosition(2,xPos,1);
            matrix[1][xPos][2] = matrix[0][xPos][1].adjustOrientation(ZP_EQUALS_YNEG).setPosition(1,xPos,2);
            matrix[0][xPos][1] = matrix[1][xPos][0].adjustOrientation(YNEG_EQUALS_ZNEG).setPosition(0,xPos,1);
            matrix[1][xPos][0] = temp.adjustOrientation(ZNEG_EQUALS_YP).setPosition(1,xPos,0);
        }
    }

    public void rotateBack(int numTurns) {
        final int zPos = 2;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][zPos];
            matrix[0][0][zPos] = matrix[0][2][zPos].adjustOrientation(XNEG_EQUALS_YNEG_AND_YNEG_EQUALS_XP).setPosition(0,0,zPos);
            matrix[0][2][zPos] = matrix[2][2][zPos].adjustOrientation(YNEG_EQUALS_XP_AND_XP_EQUALS_YP).setPosition(0,2,zPos);
            matrix[2][2][zPos] = matrix[2][0][zPos].adjustOrientation(XP_EQUALS_YP_AND_YP_EQUALS_XNEG).setPosition(2,2,zPos);
            matrix[2][0][zPos] = temp.adjustOrientation(XNEG_EQUALS_YNEG_AND_YP_EQUALS_XNEG).setPosition(2,0,zPos);

            // edges
            temp = matrix[0][1][zPos];
            matrix[0][1][zPos] = matrix[1][2][zPos].adjustOrientation(YNEG_EQUALS_XP).setPosition(0,1,zPos);
            matrix[1][2][zPos] = matrix[2][1][zPos].adjustOrientation(XP_EQUALS_YP).setPosition(1,2,zPos);
            matrix[2][1][zPos] = matrix[1][0][zPos].adjustOrientation(YP_EQUALS_XNEG).setPosition(2,1,zPos);
            matrix[1][0][zPos] = temp.adjustOrientation(XNEG_EQUALS_YNEG).setPosition(1,0,zPos);
        }
    }

    public void rotateBackCC(int numTurns) {
        final int zPos = 2;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[2][2][zPos];
            matrix[2][2][zPos] = matrix[0][2][zPos].adjustOrientation(XP_EQUALS_YNEG_AND_YP_EQUALS_XP).setPosition(2,2,zPos);
            matrix[0][2][zPos] = matrix[0][0][zPos].adjustOrientation(XP_EQUALS_YNEG_AND_YNEG_EQUALS_XNEG).setPosition(0,2,zPos);
            matrix[0][0][zPos] = matrix[2][0][zPos].adjustOrientation(YNEG_EQUALS_XNEG_AND_XNEG_EQUALS_YP).setPosition(0,0,zPos);
            matrix[2][0][zPos] = temp.adjustOrientation(XNEG_EQUALS_YP_AND_YP_EQUALS_XP).setPosition(2,0,zPos);

            // edges
            temp = matrix[2][1][zPos];
            matrix[2][1][zPos] = matrix[1][2][zPos].adjustOrientation(YP_EQUALS_XP).setPosition(2,1,zPos);
            matrix[1][2][zPos] = matrix[0][1][zPos].adjustOrientation(XP_EQUALS_YNEG).setPosition(1,2,zPos);
            matrix[0][1][zPos] = matrix[1][0][zPos].adjustOrientation(YNEG_EQUALS_XNEG).setPosition(0,1,zPos);
            matrix[1][0][zPos] = temp.adjustOrientation(XNEG_EQUALS_YP).setPosition(1,0,zPos);
        }
    }

    public void rotateBottom(int numTurns) {
        final int yPos = 2;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[yPos][0][2];
            matrix[yPos][0][2] = matrix[yPos][2][2].adjustOrientation(ZP_EQUALS_XP_AND_XNEG_EQUALS_ZP).setPosition(yPos,0,2);
            matrix[yPos][2][2] = matrix[yPos][2][0].adjustOrientation(ZP_EQUALS_XP_AND_XP_EQUALS_ZNEG).setPosition(yPos,2,2);
            matrix[yPos][2][0] = matrix[yPos][0][0].adjustOrientation(ZNEG_EQUALS_XNEG_AND_XP_EQUALS_ZNEG).setPosition(yPos,2,0);
            matrix[yPos][0][0] = temp.adjustOrientation(ZNEG_EQUALS_XNEG_AND_XNEG_EQUALS_ZP).setPosition(yPos,0,0);

            // edges
            temp = matrix[yPos][1][2];
            matrix[yPos][1][2] = matrix[yPos][2][1].adjustOrientation(ZP_EQUALS_XP).setPosition(yPos,1,2);
            matrix[yPos][2][1] = matrix[yPos][1][0].adjustOrientation(XP_EQUALS_ZNEG).setPosition(yPos,2,1);
            matrix[yPos][1][0] = matrix[yPos][0][1].adjustOrientation(ZNEG_EQUALS_XNEG).setPosition(yPos,1,0);
            matrix[yPos][0][1] = temp.adjustOrientation(XNEG_EQUALS_ZP).setPosition(yPos,0,1);
        }
    }

    public void rotateBottomCC(int numTurns) {
        final int yPos = 2;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[yPos][2][2];
            matrix[yPos][2][2] = matrix[yPos][0][2].adjustOrientation(ZP_EQUALS_XNEG_AND_XP_EQUALS_ZP).setPosition(yPos,2,2);
            matrix[yPos][0][2] = matrix[yPos][0][0].adjustOrientation(XNEG_EQUALS_ZNEG_AND_ZP_EQUALS_XNEG).setPosition(yPos,0,2);
            matrix[yPos][0][0] = matrix[yPos][2][0].adjustOrientation(XNEG_EQUALS_ZNEG_AND_ZNEG_EQUALS_XP).setPosition(yPos,0,0);
            matrix[yPos][2][0] = temp.adjustOrientation(XP_EQUALS_ZP_AND_ZNEG_EQUALS_XP).setPosition(yPos,2,0);

            // edges
            temp = matrix[yPos][1][0];
            matrix[yPos][1][0] = matrix[yPos][2][1].adjustOrientation(ZNEG_EQUALS_XP).setPosition(yPos,1,0);
            matrix[yPos][2][1] = matrix[yPos][1][2].adjustOrientation(XP_EQUALS_ZP).setPosition(yPos,2,1);
            matrix[yPos][1][2] = matrix[yPos][0][1].adjustOrientation(ZP_EQUALS_XNEG).setPosition(yPos,1,2);
            matrix[yPos][0][1] = temp.adjustOrientation(XNEG_EQUALS_ZNEG).setPosition(yPos,0,1);
        }
    }

    public String getFacesAsString() {
        StringBuilder faces = new StringBuilder();

        // print the TOP face
        faces.append("TOP:\n");
        for (int z = 2; z >= 0; z--) {
            for (int x = 0; x < 3; x++) {
                faces.append(getClrName(matrix[0][x][z].yNeg));
                if (x < 2) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the FRONT face
        faces.append("FRONT:\n");
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                faces.append(getClrName(matrix[y][x][0].zNeg));
                if (x < 2) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the BACK face
        faces.append("BACK:\n");
        for (int y = 0; y < 3; y++) {
            for (int x = 2; x >= 0; x--) {
                faces.append(getClrName(matrix[y][x][2].zP));
                if (x > 0) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the LEFT face
        faces.append("LEFT:\n");
        for (int y = 0; y < 3; y++) {
            for (int z = 2; z >= 0; z--) {
                faces.append(getClrName(matrix[y][0][z].xNeg));
                if (z > 0) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the RIGHT face
        faces.append("RIGHT:\n");
        for (int y = 0; y < 3; y++) {
            for (int z = 0; z < 3; z++) {
                faces.append(getClrName(matrix[y][2][z].xP));
                if (z < 2) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the BOTTOM face
        faces.append("BOTTOM:\n");
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < 3; x++) {
                faces.append(getClrName(matrix[2][x][z].yP));
                if (x < 2) faces.append(",");
            }
            faces.append("\n");
        }

        return faces.toString();
    }

}