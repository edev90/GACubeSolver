package Core;

public final class CubeBuilder {

    public static Cube makeCube(Cube.CubeType cubeType) {
        Cube cube = null;
        Cube baseCube = null;

        int height = 0; // y
        int width = 0; // x
        int depth = 0; // z

        if(cubeType.equals(Cube.CubeType.TWO_BY_TWO)) {
            cube = new TwoByTwoCube();
            baseCube = new TwoByTwoCube();
            cube.fitness = baseCube.fitness = 8;
            height = width = depth = 2;
        }
        else if(cubeType.equals(Cube.CubeType.THREE_BY_THREE)) {
            cube = new ThreeByThreeCube();
            baseCube = new ThreeByThreeCube();
            cube.fitness = baseCube.fitness = 27;
            height = width = depth = 3;
        }

        baseCube.setDimensions(height, width, depth);
        baseCube.setType(cubeType);
        baseCube.init();

        cube.setDimensions(height, width, depth);
        cube.setType(cubeType);
        cube.init();

        cube.baseCube = baseCube;

        return cube;
    }

    public static Cube makeNewCubeOfSameType(Cube model) {
        return makeCube(model.getType());
    }

}

