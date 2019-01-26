package jp.llv.atcoder.lib.math.euclid;

import jp.llv.atcoder.lib.math.geo.Vec3l;

public class LongEuclidSolver {
    private LongEuclidSolver() {
    }

    public static Vec3l solve(long p, long q) {
        if (q == 0) {
            return new Vec3l(p, 1, 0);
        }
        Vec3l vals = solve(q, p % q);
        long a = vals.y;
        long b = vals.x - (p / q) * a;
        return new Vec3l(a, b, vals.z);
    }
}
