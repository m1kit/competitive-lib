package dev.mikit.atcoder.lib.math.euclid;

import dev.mikit.atcoder.lib.math.geo.Vec3l;

public class LongEuclidSolver {
    private LongEuclidSolver() {
    }

    /**
     * Solves ax+by=gcd(a, b).
     *
     * @param a
     * @param b
     * @return (a, b, gcd (a, b))
     */
    public static Vec3l solve(long a, long b) {
        ReferenceLong p = new ReferenceLong(), q = new ReferenceLong();
        long d = solve(a, b, p, q);
        return new Vec3l(p.val, q.val, d);
    }

    private static long solve(long a, long b, ReferenceLong p, ReferenceLong q) {
        if (b == 0) {
            p.val = 1;
            q.val = 0;
            return a;
        } else {
            long d = solve(b, a % b, q, p);
            q.val -= (a / b) * p.val;
            return d;
        }
    }

    private static class ReferenceLong {
        private long val;
    }
}
