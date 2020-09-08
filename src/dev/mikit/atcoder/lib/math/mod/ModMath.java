package dev.mikit.atcoder.lib.math.mod;

import dev.mikit.atcoder.lib.math.IntMath;
import dev.mikit.atcoder.lib.math.euclid.LongEuclidSolver;
import dev.mikit.atcoder.lib.math.geo.Vec3l;
import dev.mikit.atcoder.lib.meta.Verified;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.LongStream;

/**
 * IntMath for long
 * intの範囲を超える大きい入力に対してはオーバーフローする!!!
 */
public class ModMath {

    private static final int DEFAULT_MOD = 1_000_000_007;

    private final long mod;
    private long primitiveRoot;

    public ModMath(long mod, boolean unsafe) {
        /*if (!unsafe && !IntMath.isPrime(mod)) {
            throw new RuntimeException("This class is designed for primes!");
        }*/
        this.mod = mod;
    }

    public ModMath(long mod) {
        this(mod, false);
    }

    public ModMath() {
        this(DEFAULT_MOD, true);
    }

    public long getPrimitiveRoot() {
        if (primitiveRoot != 0) {
            return primitiveRoot;
        }
        Set<Long> factors = IntMath.primeFactorize(mod - 1).keySet();
        outer:
        for (int i = 2; i < mod; i++) {
            for (long f : factors) {
                if (pow(i, (mod - 1) / f) == 1) {
                    continue outer;
                }
            }
            return primitiveRoot = i;
        }
        throw new RuntimeException("No primitive element found");
    }

    public boolean isPrimitiveElement(long r) {
        Set<Long> factors = IntMath.primeFactorize(mod - 1).keySet();
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
        //return pow(x, mod - 2);
        return mod(LongEuclidSolver.solve(x, mod).x);
    }

    public long add(long... x) {
        return LongStream.of(x).sum() % mod;
    }

    public long sub(long x, long y) {
        return (x - y + mod) % mod;
    }

    public long prod(long... x) {
        long ans = x[0];
        for (int i = 1; i < x.length; i++) {
            ans *= x[i];
            ans %= mod;
        }
        return ans;
    }

    public long mul(long x, long y) {
        x %= mod;
        y %= mod;
        long res = 0;
        for (; y != 0; y >>= 1) {
            if ((y & 1) == 1) {
                res += x;
                if (Long.compareUnsigned(res, mod) >= 0) {
                    res -= mod;
                }
            }
            x <<= 1;
            if (Long.compareUnsigned(x, mod) >= 0) {
                x -= mod;
            }
        }
        return res;
    }

    public long div(long x, long y) {
        return x * inv(y) % mod;
    }

    @Verified("http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3489892")
    public long pow(long x, long y) {
        y %= (mod - 1);
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

    public long powExact(long x, long y) {
        if (y < 0) {
            return powExact(inv(x), -y);
        } else if (y == 0) {
            return 1;
        } else if ((y & 1) == 0) {
            return powExact(mul(x, x), y / 2);
        } else {
            return mul(x, powExact(x, y - 1));
        }
    }

    // Baby-step Giant-step
    @Verified({
            "https://atcoder.jp/contests/arc042/submissions/5247103",
    })
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

    /*
    long msqrtp_p(int a, int p, int e) {
        int q = mod - 1, s = 0;
        while (q % p == 0) {
            q /= p;
            s++;
        }
        int pe = 1;
        for (int i = 0; i < e; i++) pe *= p;
        int d = mod_inv(pe - q % pe, pe) * q;
        long r = pow(a, (d + 1) / pe);
        long t = pow(a, d);
        if (t == 1) return r;
        int ps = 1;
        for (int i = 1; i < s; i++) ps *= p;
        long c = -1;
        for (int z = 2; ; ++z) {
            c = pow(z, q);
            if (pow(c, ps) != 1) break;
        }
        long b = -1;
        while (t != 1) {
            int tmp = pow(t, p), s2 = 1;
            while (tmp != 1) tmp = pow(tmp, p), ++s2;
            if (s2 + e <= s) {
                b = c;
                for (int i = 0; i < s - s2 - e; i++) b = pow(b, p);
                c = pow(b, pe);
                s = s2;
            }
            r = r * b % mod;
            t = t * c % mod;
        }
        return r;
    }

    int msqrtn_p(int a, int n) {
        a %= mod;
        n %= mod - 1;
        if (a <= 1) return a;
        long g = IntMath.gcd(mod - 1, n);
        if (pow(a, (mod - 1) / g) != 1) return -1;
        a = pow_mod(a, mod_inv(n / g, (p - 1) / g), p);
        for (auto pp : factors(g)) {
            a = msqrtp_p(a, pp.first, pp.second, p);
        }
        return a;
    }
     */

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

    public Exponentiation getExponentiation(long base, int max) {
        return new Exponentiation(this, base, max);
    }

    public long getModulo() {
        return mod;
    }
}
