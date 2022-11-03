package Core;

import static Core.Piece.*;
import static Core.Piece.P;

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
            matrix[0][1][0] = matrix[0][0][0].rotateAroundZToRight().setPosition(0,1,0);
            matrix[0][0][0] = matrix[1][0][0].rotateAroundZToRight().setPosition(0,0,0);
            matrix[1][0][0] = matrix[1][1][0].rotateAroundZToRight().setPosition(1,0,0);
            matrix[1][1][0] = temp.rotateAroundZToRight().setPosition(1,1,0);
        }
    }

    // rotate front counter clockwise
    public void rotateFrontCC(int numTurns) {
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][0];
            matrix[0][0][0] = matrix[0][1][0].rotateAroundZToLeft().setPosition(0,0,0);
            matrix[0][1][0] = matrix[1][1][0].rotateAroundZToLeft().setPosition(0,1,0);
            matrix[1][1][0] = matrix[1][0][0].rotateAroundZToLeft().setPosition(1,1,0);
            matrix[1][0][0] = temp.rotateAroundZToLeft().setPosition(1,0,0);
        }
    }

    public void rotateTop(int numTurns) {
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][1];
            matrix[0][0][1] = matrix[0][0][0].rotateAroundYToRight().setPosition(0,0,1);
            matrix[0][0][0] = matrix[0][1][0].rotateAroundYToRight().setPosition(0,0,0);
            matrix[0][1][0] = matrix[0][1][1].rotateAroundYToRight().setPosition(0,1,0);
            matrix[0][1][1] = temp.rotateAroundYToRight().setPosition(0,1,1);
        }
    }

    public void rotateTopCC(int numTurns) {
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][1];
            matrix[0][0][1] = matrix[0][1][1].rotateAroundYToLeft().setPosition(0,0,1);
            matrix[0][1][1] = matrix[0][1][0].rotateAroundYToLeft().setPosition(0,1,1);
            matrix[0][1][0] = matrix[0][0][0].rotateAroundYToLeft().setPosition(0,1,0);
            matrix[0][0][0] = temp.rotateAroundYToLeft().setPosition(0,0,0);
        }
    }

    public void rotateRight(int numTurns) {
        final int xPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][xPos][0];
            matrix[0][xPos][0] = matrix[1][xPos][0].rotateAroundXToBack().setPosition(0, xPos,0);
            matrix[1][xPos][0] = matrix[1][xPos][1].rotateAroundXToBack().setPosition(1, xPos,1);
            matrix[1][xPos][1] = matrix[0][xPos][1].rotateAroundXToBack().setPosition(1, xPos,1);
            matrix[0][xPos][1] = temp.rotateAroundXToBack().setPosition(0, xPos,1);
        }
    }

    public void rotateRightCC(int numTurns) {
        final int xPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][xPos][0];
            matrix[0][xPos][0] = matrix[0][xPos][1].rotateAroundXToFront().setPosition(0, xPos,0);
            matrix[0][xPos][1] = matrix[1][xPos][1].rotateAroundXToFront().setPosition(0, xPos,1);
            matrix[1][xPos][1] = matrix[1][xPos][0].rotateAroundXToFront().setPosition(1, xPos,1);
            matrix[1][xPos][0] = temp.rotateAroundXToFront().setPosition(1, xPos,0);
        }
    }

    // Clockwise - looking at left side straight on, rotates toward zNeg
    public void rotateLeft(int numTurns) {
        final int xPos = 0;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][xPos][0];
            matrix[0][xPos][0] = matrix[0][xPos][1].rotateAroundXToFront().setPosition(0,xPos,0);
            matrix[0][xPos][1] = matrix[1][xPos][1].rotateAroundXToFront().setPosition(0,xPos,1);
            matrix[1][xPos][1] = matrix[1][xPos][0].rotateAroundXToFront().setPosition(1,xPos,1);
            matrix[1][xPos][0] = temp.rotateAroundXToFront().setPosition(1, xPos,0);
        }
    }

    // rotate toward zP
    public void rotateLeftCC(int numTurns) {
        final int xPos = 0;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[1][xPos][0];
            matrix[1][xPos][0] = matrix[1][xPos][1].rotateAroundXToBack().setPosition(1, xPos,0);
            matrix[1][xPos][1] = matrix[0][xPos][1].rotateAroundXToBack().setPosition(1, xPos,1);
            matrix[0][xPos][1] = matrix[0][xPos][0].rotateAroundXToBack().setPosition(0, xPos,1);
            matrix[0][xPos][0] = temp.rotateAroundXToBack().setPosition(0, xPos,0);
        }
    }

    public void rotateBack(int numTurns) {
        final int zPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[0][0][zPos];
            matrix[0][0][zPos] = matrix[0][1][zPos].rotateAroundZToLeft().setPosition(0,0, zPos);
            matrix[0][1][zPos] = matrix[1][1][zPos].rotateAroundZToLeft().setPosition(0,1, zPos);
            matrix[1][1][zPos] = matrix[1][0][zPos].rotateAroundZToLeft().setPosition(1,1, zPos);
            matrix[1][0][zPos] = temp.rotateAroundZToLeft().setPosition(1,0, zPos);
        }
    }

    public void rotateBackCC(int numTurns) {
        final int zPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[1][1][zPos];
            matrix[1][1][zPos] = matrix[0][1][zPos].rotateAroundZToRight().setPosition(1,1, zPos);
            matrix[0][1][zPos] = matrix[0][0][zPos].rotateAroundZToRight().setPosition(0,1, zPos);
            matrix[0][0][zPos] = matrix[1][0][zPos].rotateAroundZToRight().setPosition(0,0, zPos);
            matrix[1][0][zPos] = temp.rotateAroundZToRight().setPosition(1,0, zPos);
        }
    }

    public void rotateBottom(int numTurns) {
        final int yPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[yPos][0][1];
            matrix[yPos][0][1] = matrix[yPos][1][1].rotateAroundYToLeft().setPosition(yPos,0,1);
            matrix[yPos][1][1] = matrix[yPos][1][0].rotateAroundYToLeft().setPosition(yPos,1,1);
            matrix[yPos][1][0] = matrix[yPos][0][0].rotateAroundYToLeft().setPosition(yPos,1,0);
            matrix[yPos][0][0] = temp.rotateAroundYToLeft().setPosition(yPos,0,0);
        }
    }

    public void rotateBottomCC(int numTurns) {
        final int yPos = 1;
        for (int turn = 0; turn < numTurns; turn++) {
            // corners
            Piece temp = matrix[yPos][1][1];
            matrix[yPos][1][1] = matrix[yPos][0][1].rotateAroundYToRight().setPosition(yPos,1,1);
            matrix[yPos][0][1] = matrix[yPos][0][0].rotateAroundYToRight().setPosition(yPos,0,1);
            matrix[yPos][0][0] = matrix[yPos][1][0].rotateAroundYToRight().setPosition(yPos,0,0);
            matrix[yPos][1][0] = temp.rotateAroundYToRight().setPosition(yPos,1,0);
        }
    }

    public String getFacesAsString() {
        StringBuilder faces = new StringBuilder();

        // print the TOP face
        faces.append("TOP:\n");
        for (int z = 1; z >= 0; z--) {
            for (int x = 0; x < 2; x++) {
                faces.append(getClrName(matrix[0][x][z].top));
                if (x < 1) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the FRONT face
        faces.append("FRONT:\n");
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                faces.append(getClrName(matrix[y][x][0].front));
                if (x < 1) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the BACK face
        faces.append("BACK:\n");
        for (int y = 0; y < 2; y++) {
            for (int x = 1; x >= 0; x--) {
                faces.append(getClrName(matrix[y][x][1].back));
                if (x > 0) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the LEFT face
        faces.append("LEFT:\n");
        for (int y = 0; y < 2; y++) {
            for (int z = 1; z >= 0; z--) {
                faces.append(getClrName(matrix[y][0][z].left));
                if (z > 0) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the RIGHT face
        faces.append("RIGHT:\n");
        for (int y = 0; y < 2; y++) {
            for (int z = 0; z < 2; z++) {
                faces.append(getClrName(matrix[y][1][z].right));
                if (z < 1) faces.append(",");
            }
            faces.append("\n");
        }
        faces.append("\n");

        // print the BOTTOM face
        faces.append("BOTTOM:\n");
        for (int z = 0; z < 2; z++) {
            for (int x = 0; x < 2; x++) {
                faces.append(getClrName(matrix[1][x][z].bottom));
                if (x < 1) faces.append(",");
            }
            faces.append("\n");
        }

        return faces.toString();
    }

//    public static void main(String[] args) {
//        Cube cube2x2 = CubeBuilder.makeCube(CubeType.TWO_BY_TWO);
//        cube2x2.dumpFaces();
//        System.out.println("---------------------------");
//        cube2x2.rotateFront(2);
//        cube2x2.rotateTop(3);
//        cube2x2.rotateBack(1);
//        cube2x2.rotateBackCC(1);
//        cube2x2.rotateTopCC(3);
//        cube2x2.rotateFrontCC(2);
//        // should be same as beginning state...:
//        cube2x2.dumpFaces();
//    }
}
