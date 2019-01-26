package jp.llv.atcoder.lib.math.mod;

import jp.llv.atcoder.lib.math.euclid.LongEuclidSolver;

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

    public long pow(long x, long y) {
        if (y == 0) {
            return 1;
        } else if (y % 2 == 0) {
            long z = pow(x, y / 2);
            return (z * z) % mod;
        } else {
            return (x % mod) * pow(x, y - 1) % mod;
        }
    }

    public long mod(long x) {
        x %= mod;
        return x < 0 ? x + mod : x;
    }

    public long inv(long x) {
        return mod(LongEuclidSolver.solve(x, -mod).x);
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
