package Utils;

// Helper to encode the positional & CubeCluster hash info into RGB colors

import Core.Piece;

public final class RGBEncoder {

    public static Object[] decode(int red, int green, int blue) {
        String orientationName = Piece.getOrientationNameByIndex(green & 0b111);

        return new Object[] {(blue >>> 4) & 0b11, (blue >>> 2) & 0b11, blue & 0b11, orientationName};
    }

    public static Object[] decode(int rgb) {
        int red = (rgb >>> 16) & 0xFF;
        int green = (rgb >>> 8) & 0xFF;
        int blue = rgb & 0xFF;

        return decode(red, green, blue);
    }

    public static Object[] decode(String rgbIntHash) {
        return decode(Integer.parseInt(rgbIntHash));
    }

    /***
     *
     * mode: 2 bits
     * orientation: 3 bits
     * y: 2 bits
     * x: 2 bits
     * z: 2 bits
     *
     *
     *
     *
     * */
    public static int[] encode(int mode, String orientationName, int y, int x, int z) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int rgb = 0;

        // 8 bits
        red = (int)(Math.random()*256);

        // mode: 2 bits
        // orientation: 3 bits
        int orientationIndex = Piece.getOrientationIndexByName(orientationName);
        green = (mode << 3) | orientationIndex;

        // blue - 6 bits in total
        blue = y;
        blue = (blue << 2) | x;
        blue = (blue << 2) | z;

        rgb |= red << 16;
        rgb |= green << 8;
        rgb |= blue;

        System.out.printf("y: %d, x: %d, z: %d, RGB: %s, (r: %d, g: %d, b: %d)\n", y, x, z, formatForHTML(rgb), red, green, blue);

        return new int[] {red, green, blue};
    }

    private static String formatForHTML(int rgbValue) {
        String rgbHexStr = Integer.toHexString(rgbValue);
        int padding = 6 - rgbHexStr.length();
        while(padding-- > 0) {
            rgbHexStr = "0" + rgbHexStr;
        }
        return rgbHexStr;
    }

    public static void main(String[] args) {
        final int MODE = 0;
        // zNeg
        System.out.println("xNeg:");
        {
            final int y = 0;
            final int x = 0;
            final int z = 1;
            int[] rgb = encode(MODE, "xNeg", y, x, z);
        }
        {
            final int y = 1;
            final int x = 0;
            final int z = 1;
            int[] rgb = encode(MODE, "xNeg", y, x, z);
        }

    }
}
