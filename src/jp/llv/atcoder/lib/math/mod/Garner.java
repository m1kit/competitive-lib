package jp.llv.atcoder.lib.math.mod;

import jp.llv.atcoder.lib.math.IntMath;

import java.math.BigInteger;
import java.util.Arrays;

public class Garner {
    /**
     * Garnerのアルゴリズムが適用可能なように互いに素な配列に変形します
     * @param b 剰余
     * @param m 法
     * @return 法の総和
     */
    private static BigInteger transform(long[] b, long[] m) {
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < i; j++) {
                long g = IntMath.gcd(m[i], m[j]);
                if ((b[i] - b[j]) % g != 0) {
                    throw new IllegalArgumentException();
                } else if (g == 1) {
                    continue;
                }
                m[i] /= g;
                m[j] /= g;
                long gi = IntMath.gcd(m[i], g);
                long gj = g / gi;
                do {
                    g = IntMath.gcd(gi, gj);
                    gi *= g;
                    gj /= g;
                } while (g != 1);
                m[i] *= gi;
                m[j] *= gj;
                b[i] %= m[i];
                b[j] %= m[j];
            }
        }
        BigInteger res = BigInteger.ONE;
        for (int i = 0; i < b.length; ++i) {
            res = res.multiply(BigInteger.valueOf(m[i]));
        }
        return res;
    }

    public static BigInteger garner(long[] a, long[] m) {
        transform(a, m);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(m));
        long[] coffs = new long[a.length];
        long[] constants = new long[a.length];
        long[] digs = new long[a.length];
        Arrays.fill(coffs, 1);
        for (int i = 0; i < a.length; ++i) {
            ModMath mod = new ModMath(m[i]);
            digs[i] = mod.mod((a[i] - constants[i]) * mod.inv(coffs[i]));
            if (digs[i] < 0) {
                digs[i] += m[i];
            }
            for (int j = i + 1; j < a.length; j++) {
                constants[j] += coffs[j] * digs[i];
                constants[j] %= m[j];
                coffs[j] *= m[i];
                coffs[j] %= m[j];
            }
        }
        BigInteger ans = BigInteger.ZERO, c = BigInteger.ONE;
        for (int i = a.length - 1; i >= 0; i--) {
            c = c.multiply(BigInteger.valueOf(m[i]));
            ans = ans.multiply(BigInteger.valueOf(m[i]));
            ans = ans.add(BigInteger.valueOf(digs[i]));
        }
        if (ans.compareTo(c.divide(BigInteger.valueOf(2))) > 0) {
            ans = ans.subtract(c);
        }
        return ans;
    }
}
