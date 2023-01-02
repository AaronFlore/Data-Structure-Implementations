import java.math.BigInteger;
import java.lang.Math;

/**
 * This interface contains the following variety of methods: 
 * A recursive mathematical function a(x, y), a decimal to binary function, returning the largest 
 * integer less than log3(n), and x^n power function.
 * These methods would not ordinarily be written recursively, but I am writing them recursively for practice.
 */
public interface NeedlesslyRecursive {

    static BigInteger a(BigInteger x, BigInteger y) {
        if (x == null || y == null || x.compareTo(BigInteger.ZERO) == -1 || y.compareTo(BigInteger.ZERO) == -1) {
            throw new IllegalArgumentException("Numbers cannot be less than 0 or null");
        }
        if (x.compareTo(BigInteger.ZERO) == 0
                && (y.compareTo(BigInteger.ZERO) == 0 || y.compareTo(BigInteger.ZERO) == 1)) {
            return y.add(BigInteger.ONE);
        }
        if (x.compareTo(BigInteger.ZERO) == 1 && y.compareTo(BigInteger.ZERO) == 0) {
            return a(x.subtract(BigInteger.ONE), BigInteger.ONE);
        }
        if (x.compareTo(BigInteger.ZERO) == 1 && y.compareTo(BigInteger.ZERO) == 1) {
            return a(x.subtract(BigInteger.ONE), a(x, y.subtract(BigInteger.ONE)));
        } else {
            throw new IllegalArgumentException();
        }
    }

    static String binaryString(int num) {
        if (num == -1 || num == 1 || num == 0) {
            return Integer.toString(num);
        }
        return binaryString(num / 2) + Integer.toString(Math.abs(num) % 2);
    }

    static int log3(int num) {
        if (3 > num) {
            return 0;
        } else {
            return 1 + log3(num / 3);
        }
    }

    static BigInteger power(BigInteger base, int power) {
        if (power == 0) {
            return BigInteger.ONE;
        } else {
            return base.multiply(power(base, (power - 1)));
        }
    }

}
