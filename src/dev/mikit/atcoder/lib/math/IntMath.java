package dev.mikit.atcoder.lib.math;

import dev.mikit.atcoder.lib.math.euclid.LongEuclidSolver;
import dev.mikit.atcoder.lib.math.geo.Vec3l;
import dev.mikit.atcoder.lib.math.mod.ModMath;
import dev.mikit.atcoder.lib.meta.Verified;

import java.math.BigInteger;
import java.util.*;

public final class IntMath {

    private IntMath() {
    }

    public static BigInteger lcm(BigInteger a, BigInteger b) {
        BigInteger ans = a.multiply(b);
        ans = ans.divide(gcd(a, b));
        return ans;
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger amodb;
        while ((amodb = a.mod(b)).compareTo(BigInteger.ZERO) > 0) {
            a = b;
            b = amodb;
        }
        return b;
    }

    @Verified("http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3489896")
    public static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    @Verified("http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3489896")
    public static long lcm(long... a) {
        long t = a[0];
        for (int i = 1; i < a.length; i++) {
            t = lcm(t, a[i]);
        }
        return t;
    }

    public static long gcd(long a, long b) {
        long t;
        if (a == 0) return b;
        if (b == 0) return a;
        while (a % b > 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return b;
    }

    public static long gcd(long... a) {
        long t = a[0];
        for (int i = 1; i < a.length; i++) {
            t = gcd(t, a[i]);
        }
        return t;
    }

    public static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    public static int lcm(int... a) {
        int t = a[0];
        for (int i = 1; i < a.length; i++) {
            t = lcm(t, a[i]);
        }
        return t;
    }

    public static int gcd(int a, int b) {
        int t;
        if (a == 0) return b;
        if (b == 0) return a;
        while (a % b > 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return b;
    }

    public static int gcd(int... a) {
        int t = a[0];
        for (int i = 1; i < a.length; i++) {
            t = gcd(t, a[i]);
        }
        return t;
    }

    public static Integer getPrimeFactor(int a) {
        if (a <= 1) {
            return null;
        } else if ((a & 1) == 0) {
            return 2;
        }
        for (int i = 3; i * i <= a; i += 2) {
            if (a % i == 0) {
                return i;
            }
        }
        return a;
    }

    @Verified("https://yukicoder.me/submissions/397993")
    public static boolean isPrime(int n) {
        if (n == 2) {
            return true;
        } else if (n <= 1 || (n & 1) == 0) {
            return false;
        }
        int r = BitMath.lsb(n - 1);
        int d = (n - 1) >> r;
        ModMath m = new ModMath(n, true);
        outer:
        for (int a : new int[]{2, 7, 61}) {
            int x = (int) m.powExact(a, d);
            if (x == 1 || x == n - 1 || n - 2 < a) {
                continue;
            }
            for (int j = 1; j < r; j++) {
                x = (int) m.powExact(x, 2);
                if (x == n - 1) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    @Verified("https://yukicoder.me/submissions/397993")
    public static boolean isPrime(long n) {
        if (n < Integer.MAX_VALUE) {
            return isPrime((int) n);
        }
        int s = BitMath.lsb(n - 1);
        long d = (n - 1) >> s;
        ModMath m = new ModMath(n);
        outer:
        for (long a : new long[]{2L, 325L, 9375L, 28178L, 450775L, 9780504L, 1795265022L}) {
            long r = m.powExact(a, d);
            if (r == 1 || r == n - 1) {
                continue;
            }
            for (int j = 0; j < s; j++) {
                r = m.powExact(r, 2);
                if (r == n - 1) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    @Verified("http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3489888")
    public static Map<Long, Integer> primeFactorize(long p) {
        Map<Long, Integer> factor = new HashMap<>();
        if ((p & 1) == 0) {
            int c = 0;
            do {
                c++;
                p >>= 1;
            } while ((p & 1) == 0);
            factor.put(2L, c);
        }
        for (long i = 3; i * i <= p; i += 2) {
            if (p % i == 0) {
                int c = 0;
                do {
                    c++;
                    p /= i;
                } while ((p % i) == 0);
                factor.put(i, c);
            }
        }
        if (p > 1) {
            factor.put(p, 1);
        }
        return factor;
    }

    public static int[] getAllDivisors(int p) {
        Set<Integer> res = new TreeSet<>();
        for (int i = 1; i * i <= p; i++) {
            if (p % i != 0) continue;
            res.add(i);
            if (i * i != p) res.add(p / i);
        }
        return res.stream().mapToInt(x -> x).toArray();
    }

    public static long[] getAllDivisors(long p) {
        Set<Long> res = new TreeSet<>();
        for (long i = 1; i * i <= p; i++) {
            if (p % i != 0) continue;
            res.add(i);
            if (i * i != p) res.add(p / i);
        }
        return res.stream().mapToLong(x -> x).toArray();
    }

    /**
     * Calculates totient functionn for p^c
     *
     * @param p prime base
     * @param c exponent
     * @return totient(p ^ c)
     */
    @Verified("http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3489904")
    public static long primeTotient(long p, int c) {
        long ans = p - 1;
        for (int i = 1; i < c; i++) {
            ans *= p;
        }
        return ans;
    }

    @Verified("http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3489904")
    public static long totient(long v) {
        Map<Long, Integer> factors = primeFactorize(v);
        long ans = 1;
        for (Map.Entry<Long, Integer> ent : factors.entrySet()) {
            ans *= primeTotient(ent.getKey(), ent.getValue());
        }
        return ans;
    }

    public static int min(int... v) {
        return Arrays.stream(v).min().orElseThrow(NoSuchElementException::new);
    }

    public static long min(long... v) {
        return Arrays.stream(v).min().orElseThrow(NoSuchElementException::new);
    }

    public static double min(double... v) {
        return Arrays.stream(v).min().orElseThrow(NoSuchElementException::new);
    }

    public static int max(int... v) {
        return Arrays.stream(v).max().orElseThrow(NoSuchElementException::new);
    }

    public static long max(long... v) {
        return Arrays.stream(v).max().orElseThrow(NoSuchElementException::new);
    }

    public static double max(double... v) {
        return Arrays.stream(v).max().orElseThrow(NoSuchElementException::new);
    }

    public static int[] sumArray(int[] a) {
        int[] sum = new int[a.length + 1];
        for (int i = 0; i < a.length; i++) {
            sum[i + 1] = sum[i] + a[i];
        }
        return sum;
    }

    public static long[] sumArray(long[] a) {
        long[] sum = new long[a.length + 1];
        for (int i = 0; i < a.length; i++) {
            sum[i + 1] = sum[i] + a[i];
        }
        return sum;
    }

    public static long sum(int... v) {
        return Arrays.stream(v).mapToLong(x -> x).sum();
    }

    public static long sum(long... v) {
        return Arrays.stream(v).sum();
    }

    @Verified("https://atcoder.jp/contests/nikkei2019-ex/submissions/4303502")
    public static int sqrt(int a) {
        int min = 0, max = 0x10000;
        while (max - min > 1) {
            int mid = (min + max) / 2;
            if (mid * mid <= a) {
                min = mid;
            } else {
                max = mid;
            }
        }
        return min;
    }

    public static int sqrt(long a) {
        long min = 0L, max = 0x100000000L;
        while (max - min > 1) {
            long mid = (min + max) / 2;
            if (mid * mid <= a) {
                min = mid;
            } else {
                max = mid;
            }
        }
        return (int) min;
    }

    public static long npr(int n, int r) {
        long res = 1;
        for (int i = 0; i < r; i++) res *= n - i;
        return res;
    }

    public static long ncr(int n, int r) {
        long res = npr(n, r);
        for (int i = 2; i <= r; i++) res /= i;
        return res;
    }

    public static long nhr(int n, int r) {
        return (n | r) == 0 ? 1 : ncr(n + r - 1, r);
    }

    /**
     * Calculate the value of sum_{0 <= i < n}(a * i + b)
     *
     * @param n number of terms
     * @param a diff of sequence
     * @param b initial term of sequence
     * @return the sum
     */
    public static long sumArithmeticSequence(long n, long a, long b) {
        return n * (n - 1L) / 2 * a + b * n;
    }

    /**
     * Calculate the value of sum_{0 <= i < n}(floor((a * i + b) / m))
     *
     * @param n number of terms
     * @param m divisor
     * @param a diff of sequence
     * @param b initial term of sequence
     * @return the sum
     */
    public static long sumFloorArithmeticSequence(long n, long m, long a, long b) {
        long ans = 0;
        if (a >= m) {
            ans += (n - 1) * n * (a / m) / 2;
            a %= m;
        }
        if (b >= m) {
            ans += n * (b / m);
            b %= m;
        }
        long yMax = (a * n + b) / m, xMax = (yMax * m - b);
        if (yMax == 0) return ans;
        ans += (n - (xMax + a - 1) / a) * yMax;
        ans += sumFloorArithmeticSequence(yMax, a, m, (a - xMax % a) % a);
        return ans;
    }

}
