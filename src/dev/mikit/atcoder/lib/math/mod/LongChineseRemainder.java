package dev.mikit.atcoder.lib.math.mod;

import dev.mikit.atcoder.lib.math.euclid.LongEuclidSolver;
import dev.mikit.atcoder.lib.math.geo.Vec3l;
import dev.mikit.atcoder.lib.meta.Verified;

@Verified({
        "https://yukicoder.me/submissions/338860",
})
public class LongChineseRemainder {

    private long remainder = 0, modulo = 1;

    public void grow(long b2, long m2) {
        Vec3l sol = LongEuclidSolver.solve(modulo, m2);
        long p = sol.x, d = sol.z;
        if ((b2 - remainder) % d != 0) {
            throw new IllegalStateException("Given x % "+ m2 + " = " + b2 +" and x % " + modulo + " = " + remainder + ", gcd was " + sol.z);
        }
        m2 /= d;
        long m = modulo * m2;
        long tmp = (b2 - remainder) / d * p % m2;
        remainder += modulo * tmp;
        remainder %= m;
        if (remainder < 0) {
            remainder += m;
            remainder %= m;
        }
        modulo = m;
     }

    public long getRemainder() {
        return remainder;
    }

    public long getModulo() {
        return modulo;
    }

    public void clear() {
        this.remainder = 0;
        this.modulo = 1;
    }
}
