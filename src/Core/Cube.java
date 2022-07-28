package Core;

public abstract class Cube {

    public static enum CubeType {
        TWO_BY_TWO,
        THREE_BY_THREE
    }

    protected void init() {

    }

    private CubeType type;

    public CubeType getType() {
        return this.type;
    }

    public void setType(CubeType type) {
        this.type = type;
    }

    protected int height = 0;
    protected int width = 0;
    protected int depth = 0;

    public void setDimensions(int height, int width, int depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    // structure: y, x, z
    public Piece[][][] matrix = null;

    // Rotations

    public void rotateFront(int numTurns) {}
    public void rotateFrontCC(int numTurns) {}
    public void rotateTop(int numTurns) {}
    public void rotateTopCC(int numTurns) {}
    public void rotateRight(int numTurns) {}
    public void rotateRightCC(int numTurns) {}
    public void rotateLeft(int numTurns) {}
    public void rotateLeftCC(int numTurns) {}
    public void rotateBack(int numTurns) {}
    public void rotateBackCC(int numTurns) {}
    public void rotateBottom(int numTurns) {}
    public void rotateBottomCC(int numTurns) {}


    public String getFacesAsString() {
        /*** todo: implement */
        return "";
    }

    // Build a cube that is an exact copy of this one, with all the pieces configured in the same exact position and orientation
    public Cube clone() {
        Cube copy = CubeBuilder.makeCube(this.type);
        this.copyTo(copy);
        return copy;
    }

    // Copies the exact state of this cube into copy
    public void copyTo(Cube copy) {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                for(int z = 0; z < depth; z++) {
                    copy.matrix[y][x][z] = this.matrix[y][x][z].clone();
                }
            }
        }
    }

    public boolean isInSameState(Cube other) {
        boolean isSame = true;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                for(int z = 0; z < depth; z++) {
                    isSame &= this.matrix[y][x][z].isInSameState(other.matrix[y][x][z]);
                }
            }
        }
        return isSame;
    }

    public void scramble(int... moves) {
        int numOfMoves = moves.length;
        if(numOfMoves % 3 != 0) {
            System.err.println("Moves length must be >= 3 and divisible by 3.");
            return;
        }
        // Each move has 3 parts:
        //  move+0 defines the actual face getting rotated (top, front, back.., etc..)
        //  move+1 # of turns
        //  move+2 defines the direction of the rotation (clockwise vs. counter-clockwise)
        for(int move = 0; move < numOfMoves; move += 3) {
            int faceToRotate = moves[move];
            int numTurns = moves[move+1];
            int direction = moves[move+2];
            switch(faceToRotate) {
                case 0:
                    if(direction == 1) this.rotateTop(numTurns);
                    else this.rotateTopCC(numTurns);
                    break;
                case 1:
                    if(direction == 1) this.rotateBottom(numTurns);
                    else this.rotateBottomCC(numTurns);
                    break;
                case 2:
                    if(direction == 1) this.rotateLeft(numTurns);
                    else this.rotateLeftCC(numTurns);
                    break;
                case 3:
                    if(direction == 1) this.rotateRight(numTurns);
                    else this.rotateRightCC(numTurns);
                    break;
                case 4:
                    if(direction == 1) this.rotateFront(numTurns);
                    else this.rotateFrontCC(numTurns);
                    break;
                case 5:
                    if(direction == 1) this.rotateBack(numTurns);
                    else this.rotateBackCC(numTurns);
                    break;
                default: System.err.println("Invalid move.");
                    break;
            }
        }
    }

    public Piece getPiece(int y, int x, int z) {
        return this.matrix[y][x][z];
    }

    public void dumpFaces() {
        System.out.println(getFacesAsString());
    }

}
