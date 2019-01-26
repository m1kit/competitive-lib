package jp.llv.atcoder.lib.math.euclid;

import jp.llv.atcoder.lib.math.geo.Vec2b;

import java.math.BigInteger;

public class BigIntEuclidSolver {
    private static final BigIntEuclidSolver instance = new BigIntEuclidSolver();
    private BigInteger x, y;

    private BigInteger solve(BigInteger a, BigInteger b, boolean r) {
        if (b.equals(BigInteger.ZERO)) {
            if (r) {
                y = BigInteger.ONE;
                x = BigInteger.ZERO;
            } else {
                x = BigInteger.ONE;
                y = BigInteger.ZERO;
            }
            return a;
        }
        BigInteger d = solve(b, a.mod(b), !r);
        if (r) {
            x = x.subtract(a.divide(b).multiply(y));
        } else {
            y = y.subtract(a.divide(b).multiply(x));
        }
        return d;
    }

    public static Vec2b solve(BigInteger a, BigInteger b) {
        if (a.compareTo(b) >= 0) {
            instance.solve(a, b, false);
        } else {
            instance.solve(b, a, true);
        }
        return new Vec2b(instance.x, instance.y);
    }
}
