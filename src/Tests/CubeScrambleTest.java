package Tests;

/*
* Test to verify that we can scramble a cube and reverse the scrambling to revert the cube successfully
* back to its original state
* */

import Core.Cube;
import Core.CubeBuilder;

public class CubeScrambleTest {

    public static void doTests() {
        Cube beforeScramble = CubeBuilder.makeCube(Cube.CubeType.THREE_BY_THREE);
        Cube cubeCopy = beforeScramble.clone();

        System.out.println("Cube before scramble:\n" + cubeCopy.getFacesAsString());

        int[] scrambleMoves = {0,4,0, 4,2,1, 5,1,1, 2,2,0, 5,3,0, 5,1,1, 1,1,0, 0,3,0, 4,3,1, 2,2,0, 1,2,0, 5,3,1,
                                5,4,0, 4,2,1, 2,1,1, 1,2,0, 4,4,1, 2,4,1, 0,3,1, 1,3,1, 4,1,1, 0,3,0, 0,3,0, 2,4,0,
                                0,4,1, 2,1,1, 5,4,1, 1,2,1, 1,4,1, 4,3,0, 5,1,0, 0,2,0, 1,1,1, 5,3,1, 4,1,1, 2,4,0,
                                2,1,0, 5,3,1, 2,2,1, 2,4,1, 0,4,1, 5,4,0, 2,4,1, 0,2,0, 3,3,0, 5,1,0, 3,4,0, 1,1,1,
                                5,1,0, 0,1,0};

        cubeCopy.scramble(scrambleMoves);

        System.out.println("Original cube after cubeCopy scramble: " + beforeScramble.getFacesAsString());
        System.out.println();

        System.out.println("Cube after scramble:\n" + cubeCopy.getFacesAsString());
        System.out.println();

        int[] reverseScrambleMoves = new int[scrambleMoves.length];

        for(int i = 0, r = reverseScrambleMoves.length-1; i < scrambleMoves.length; i += 3, r -= 3) {
            reverseScrambleMoves[r-2] = scrambleMoves[i];
            reverseScrambleMoves[r-1] = scrambleMoves[i+1];
            reverseScrambleMoves[r] = scrambleMoves[i+2] == 1 ? 0 : 1;  // reverse direction
        }

        cubeCopy.scramble(reverseScrambleMoves);

        System.out.println("Cube same as it was pre-scramble?: " + cubeCopy.isInSameState(beforeScramble));
        System.out.println("");
        System.out.println(cubeCopy.getFacesAsString());
    }

    public static void main(String[] args) {
        doTests();
        System.exit(0);
    }
}
