package Tests;

import Core.Cube;
import Core.CubeBuilder;

public class CubeCopyTest {

    private static void doTests() {
        Cube original = CubeBuilder.makeCube(Cube.CubeType.THREE_BY_THREE);
        Cube cubeCopy = original.clone();

        cubeCopy.rotateFront(2);
        cubeCopy.rotateTop(5);
        cubeCopy.rotateLeft(4);
        cubeCopy.rotateRight(2);
        cubeCopy.rotateLeft(1);
        cubeCopy.rotateBottom(3);
        cubeCopy.rotateBack(2);
        cubeCopy.rotateTopCC(1);

        // now reverse
        cubeCopy.rotateTop(1);
        cubeCopy.rotateBackCC(2);
        cubeCopy.rotateBottomCC(3);
        cubeCopy.rotateLeftCC(1);
        cubeCopy.rotateRightCC(2);
        cubeCopy.rotateLeftCC(4);
        cubeCopy.rotateTopCC(5);
        cubeCopy.rotateFrontCC(2);

        System.out.println("cubes in the same state?: " + (original.isInSameState(cubeCopy)));
        System.out.println();
        System.out.println("Cube: " + cubeCopy.getFacesAsString());
    }

    public static void main(String[] args) {
        doTests();
    }
}
