package Utils;

public final class MathUtil {

    public static final long ONE_SECOND_IN_NANOS = 1000000000L;

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public static double nanosToSeconds(long nanoseconds) {
        return ((double)nanoseconds) / ONE_SECOND_IN_NANOS;
    }

    public static void main(String[] args) {
        System.out.println(distance(10, 10, 15, 15));
    }
}
