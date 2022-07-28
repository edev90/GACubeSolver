package Tests;

import static Utils.RGBEncoder.decode;
import static Utils.RGBEncoder.encode;

public class RGBEncodeAndDecodingTest {

    public static boolean test_decodeMatchesEncoded0() {
        final int MODE = 0;
        int[] rgb = encode(MODE, "yNeg", 0, 0, 0);
        Object[] decoded = decode(rgb[0], rgb[1], rgb[2]);
        boolean testPassed = (Integer) decoded[0] == 0 &&
                            ((Integer) decoded[1] == 0) &&
                            ((Integer) decoded[2] == 0) &&
                            (""+decoded[3]).equals("yNeg");
        return testPassed;
    }

    public static boolean test_decodeMatchesEncoded1() {
        final int MODE = 0;
        int[] rgb = encode(MODE, "xP", 2, 1, 0);
        Object[] decoded = decode(rgb[0], rgb[1], rgb[2]);
        boolean testPassed = (Integer) decoded[0] == 2 &&
                ((Integer) decoded[1] == 1) &&
                ((Integer) decoded[2] == 0) &&
                (""+decoded[3]).equals("xP");
        return testPassed;
    }

    public static boolean test_decodeDoesNotMatchEncoded() {
        final int MODE = 0;
        int[] rgb = encode(MODE, "xP", 2, 1, 0);
        Object[] decoded = decode(rgb[0], rgb[1], rgb[2]);
        boolean matches = (Integer) decoded[0] == 2 &&
                ((Integer) decoded[1] == 1) &&
                ((Integer) decoded[2] == 0) &&
                (""+decoded[3]).equals("yNeg");
        return matches == false;
    }

    public static void main(String[] args) {
        System.out.println("test_decodeMatchesEncoded0() passed? " + test_decodeMatchesEncoded0());
        System.out.println("test_decodeMatchesEncoded1() passed? " + test_decodeMatchesEncoded1());
        System.out.println("test_decodeDoesNotMatchEncoded() passed? " + test_decodeDoesNotMatchEncoded());
    }

}
