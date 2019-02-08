package jp.llv.atcoder.lib.math.mod;

import jp.llv.atcoder.lib.math.IntMath;
import jp.llv.atcoder.lib.math.euclid.LongEuclidSolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * IntMath for long
 * intの範囲を超える大きい入力に対してはオーバーフローする!!!
 */
public class ModMath {

    private static final int DEFAULT_MOD = 1_000_000_007;

    private final long mod;

    public ModMath(long mod) {
        this.mod = mod;
    }

    public ModMath() {
        this(DEFAULT_MOD);
    }

    public long getPrimitiveElement() {
        Set<Long> factors = IntMath.getFactors(mod - 1);
        outer: for (int i = 2; i < mod; i++) {
            for (long f : factors) {
                if (pow(i, (mod - 1) / f) == 1) {
                    continue outer;
                }
            }
            return i;
        }
        throw new RuntimeException("No primitive element found");
    }

    public boolean isPrimitiveElement(long r) {
        Set<Long> factors = IntMath.getFactors(mod - 1);
        for (long f : factors) {
            if (pow(r, (mod - 1) / f) == 1) {
                return false;
            }
        }
        return true;
    }

    public long mod(long x) {
        x %= mod;
        return x < 0 ? x + mod : x;
    }

    public long inv(long x) {
        return mod(LongEuclidSolver.solve(x, -mod).x);
    }

    public long add(long x, long y) {
        return (x + y) % mod;
    }

    public long sub(long x, long y) {
        return (x - y + mod) % mod;
    }

    public long prod(long x, long y) {
        return (mod(x) * mod(y)) % mod;
    }

    public long div(long x, long y) {
        return prod(x, inv(y));
    }

    public long pow(long x, long y) {
        if (y < 0) {
            return pow(inv(x), -y);
        } else if (y == 0) {
            return 1;
        } else if (y % 2 == 0) {
            long z = pow(x, y / 2);
            return (z * z) % mod;
        } else {
            return (x % mod) * pow(x, y - 1) % mod;
        }
    }

    // Baby-step Giant-step
    public long log(long b, long x) {
        long m = (long) Math.ceil(Math.sqrt(mod));
        long pow = 1;
        Map<Long, Long> table = new HashMap<>();
        for (long j = 0; j < m; j++) {
            table.put(pow, j);
            pow *= b;
            pow %= mod;
        }
        long inv = pow(b, -m);
        long gamma = x % mod;
        for (int i = 0; i < m; i++) {
            if (table.containsKey(gamma)) {
                return i * m + table.get(gamma);
            }
            gamma = gamma * inv % mod;
        }
        throw new RuntimeException("No solution found");
    }

    public long npr(int n, int r) {
        if (n < r) {
            return 0;
        }
        long ans = 1;
        for (int i = 0; i < r; i++) {
            ans *= (n - i);
            ans %= mod;
        }
        return ans;
    }

    public long ncr(int n, int r) {
        long div = 1;
        for (int i = 2; i <= r; i++) {
            div *= i;
            div %= mod;
        }
        return mod(npr(n, r) * inv(div));
    }

    public long nhr(int n, int r) {
        if ((n | r) == 0) {
            return 1;
        }
        return ncr(n + r - 1, r);
    }

    public Factorial getFactorial(int n) {
        return new Factorial(this, n);
    }

}
