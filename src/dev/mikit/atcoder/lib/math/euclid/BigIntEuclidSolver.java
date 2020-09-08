package dev.mikit.atcoder.lib.math.euclid;

import dev.mikit.atcoder.lib.math.geo.Vec3bi;

import java.math.BigInteger;

public class BigIntEuclidSolver {
    /**
     * Solves ax+by=gcd(a, b).
     *
     * @param a
     * @param b
     * @return (a, b, gcd (a, b))
     */
    public static Vec3bi solve(BigInteger a, BigInteger b) {
        ReferenceBigInt p = new ReferenceBigInt(), q = new ReferenceBigInt();
        BigInteger d = solve(a, b, p, q);
        return new Vec3bi(p.val, q.val, d);
    }

    private static BigInteger solve(BigInteger a, BigInteger b, ReferenceBigInt p, ReferenceBigInt q) {
        if (BigInteger.ZERO.equals(b)) {
            p.val = BigInteger.ONE;
            q.val = BigInteger.ZERO;
            return a;
        } else {
            BigInteger d = solve(b, a.mod(b), q, p);
            q.val = q.val.subtract(a.divide(b).multiply(p.val));
            return d;
        }
    }

    private static class ReferenceBigInt {
        private BigInteger val = BigInteger.ZERO;
    }
}
