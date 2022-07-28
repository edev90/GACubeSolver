package Core;

public final class CubeBuilder {

    public static Cube makeCube(Cube.CubeType cubeType) {
        Cube cube = null;
        int height = 0; // y
        int width = 0; // x
        int depth = 0; // z
        if(cubeType.equals(Cube.CubeType.TWO_BY_TWO)) {
            cube = new TwoByTwoCube();
            height = width = depth = 2;
        }
        else if(cubeType.equals(Cube.CubeType.THREE_BY_THREE)) {
            cube = new ThreeByThreeCube();
            height = width = depth = 3;
        }

        cube.setDimensions(height, width, depth);
        cube.setType(cubeType);
        cube.init();

        return cube;
    }

    public static Cube makeNewCubeOfSameType(Cube model) {
        return makeCube(model.getType());
    }

}

