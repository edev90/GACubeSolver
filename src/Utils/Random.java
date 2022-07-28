package Utils;

public final class Random {

    public static int randomInRange(int startInclusive, int endNotInclusive) {
        return startInclusive + (int)(Math.random()*(endNotInclusive - startInclusive));
    }

    public static int randomInRange(int endNotInclusive) {
        return randomInRange(0, endNotInclusive);
    }
}
