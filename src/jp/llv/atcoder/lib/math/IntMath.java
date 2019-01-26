package jp.llv.atcoder.lib.math;

import jp.llv.atcoder.lib.math.mod.ModMath;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public final class IntMath {

    private IntMath() {
    }

    public static BigInteger lcm(BigInteger a, BigInteger b) {
        BigInteger ans = a.multiply(b);
        ans = ans.divide(gcd(a, b));
        return ans;
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger t;
        while (a.mod(b).compareTo(BigInteger.ZERO) > 0) {
            t = b;
            b = a.mod(b);
            a = t;
        }
        return b;
    }

    public static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    public static long gcd(long a, long b) {
        long t;
        while (a % b > 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return b;
    }

    public static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    public static int gcd(int a, int b) {
        int t;
        while (a % b > 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return b;
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

    public static Long getPrimeFactor(long a) {
        if (a <= 1) {
            return null;
        } else if ((a & 1) == 0) {
            return 2L;
        }
        for (long i = 3; i * i <= a; i += 2) {
            if (a % i == 0) {
                return i;
            }
        }
        return a;
    }

    public static boolean isPrime(int n) {
        if (n == 2) {
            return true;
        } else if (n <= 1 || (n & 1) == 0) {
            return false;
        }
        int s = BitMath.lsb(n - 1);
        int d = (n - 1) >> s;
        ModMath m = new ModMath(n);
        outer: for (int a : new int[]{2, 7, 61}) {
            int r = (int) m.pow(a, d);
            if (r == 1 || r == n - 1) {
                continue;
            }
            for (int j = 0; j < s; j++) {
                r = (int) m.pow(r, 2);
                if (r == n - 1) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    public static boolean isPrime(long n) {
        if (n < Integer.MAX_VALUE) {
            return isPrime((int) n);
        }
        int s = BitMath.lsb(n - 1);
        long d = (n - 1) >> s;
        ModMath m = new ModMath(n);
        outer: for (int a : new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}) {
            long r = m.pow(a, d);
            if (r == 1 || r == n - 1) {
                continue;
            }
            for (int j = 0; j < s; j++) {
                r = m.pow(r, 2);
                if (r == n - 1) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    public static Map<Long, Integer> primeFactorize(long p) {Long f = null;
        Map<Long, Integer> factor = new HashMap<>();
        while ((f = IntMath.getPrimeFactor(p)) != null) {
            factor.merge(f, 1, (x, y) -> x + y);
            p /= f;
        }
        if (p > 1) {
            factor.merge(p, 1, (x, y) -> x + y);
        }
        return factor;
    }


    public static int min(int ... v) {
        return Arrays.stream(v).min().orElseThrow(NoSuchElementException::new);
    }

    public static long min(long ... v) {
        return Arrays.stream(v).min().orElseThrow(NoSuchElementException::new);
    }

    public static double min(double ... v) {
        return Arrays.stream(v).min().orElseThrow(NoSuchElementException::new);
    }

    public static int max(int ... v) {
        return Arrays.stream(v).max().orElseThrow(NoSuchElementException::new);
    }

    public static long max(long ... v) {
        return Arrays.stream(v).max().orElseThrow(NoSuchElementException::new);
    }

    public static double max(double ... v) {
        return Arrays.stream(v).max().orElseThrow(NoSuchElementException::new);
    }

    public static int[] sum(int[] a) {
        int[] sum = new int[a.length + 1];
        for (int i = 0; i < a.length; i++) {
            sum[i + 1] = sum[i] + a[i];
        }
        return sum;
    }

    public static long[] sum(long[] a) {
        long[] sum = new long[a.length + 1];
        for (int i = 0; i < a.length; i++) {
            sum[i + 1] = sum[i] + a[i];
        }
        return sum;
    }

}
