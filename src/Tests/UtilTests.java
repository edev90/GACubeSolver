package Tests;

import static Utils.Random.randomInRange;

public class UtilTests {

    public static void inRange_Test() {
        System.out.println("randomInRange(0, 0): " + randomInRange(0, 0));
        System.out.println("randomInRange(0, 1): " + randomInRange(0, 1));
        System.out.println("randomInRange(1, 1): " + randomInRange(1, 1));
        System.out.println("randomInRange(2, 5): " + randomInRange(2, 5));
    }

    public static void doTests() {
        inRange_Test();
    }

    public static void main(String[] args) {
        doTests();
    }
}
