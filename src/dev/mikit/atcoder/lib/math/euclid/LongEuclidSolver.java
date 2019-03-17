package dev.mikit.atcoder.lib.math.euclid;

import dev.mikit.atcoder.lib.geo.Vec3l;

public class LongEuclidSolver {
    private LongEuclidSolver() {
    }

    /**
     * Solves px+qy=0.
     * @param p
     * @param q
     * @return (x, y, gcd(p, q))
     */
    public static Vec3l solve(long p, long q) {
        if (q == 0) {
            return new Vec3l(p, 1, p);
        }
        Vec3l vals = solve(q, p % q);
        long a = vals.y;
        long b = vals.x - (p / q) * a;
        return new Vec3l(a, b, vals.z);
    }
}
