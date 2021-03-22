package dev.mikit.atcoder.lib.math.euclid;

import dev.mikit.atcoder.lib.math.IntMath;
import dev.mikit.atcoder.lib.math.geo.Vec3l;

public class LongEuclidSolver {
    private LongEuclidSolver() {
    }

    /**
     * Solves ax+by=gcd(a, b).
     * Generic solution: a(x-x0) + b(y-y0) = 0
     * Thus, x = x0 + bm, y = y0 + am
     *
     * If you want to minimize x or y, please consider using Chinese Remainder Theorem instead.
     * Or, if you want to find minimum x s.t. ax = b mod m, see an editorial below
     * https://atcoder.jp/contests/abc186/editorial/401
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
