package dev.mikit.atcoder.lib.math.mod;

import dev.mikit.atcoder.lib.meta.Verified;
import dev.mikit.atcoder.lib.util.ArrayUtil;

import java.util.Arrays;

@Verified("https://judge.yosupo.jp/submission/21610")
public class NTT {

    private static final int[] DEFAULT_MOD = {998_244_353, 1_300_234_241, 1_484_783_617};
    private static final int[] DEFAULT_G = {3, 3, 5};

    private final ModMath mm;
    private final int mod;
    private final int g;

    private NTT(int mod, int g) {
        this.mm = new ModMath(mod);
        this.mod = mod;
        this.g = g;
    }

    public NTT() {
        this.mod = DEFAULT_MOD[0];
        this.g = DEFAULT_G[0];
        this.mm = new ModMath(DEFAULT_MOD[0]);
    }

    public NTT(int mod) {
        this.mm = new ModMath(mod);
        this.mod = mod;
        this.g = (int) mm.getPrimitiveRoot();
    }

    public void fft(long[] a, boolean invert) {
        int count = a.length;
        long base = invert ? mm.inv(g) : g;
        for (int i = 0, j = 1; j < count; j++) {
            int k = count >> 1;
            for (; i >= k; k >>= 1) i -= k;
            i += k;
            if (j < i) ArrayUtil.swap(a, j, i);
        }
        int exp = mod - 1;
        for (int len = 2; len <= count; len <<= 1) {
            int halfLen = len >> 1;
            exp >>= 1;
            long angle = mm.pow(base, exp);
            long w = 1;
            for (int i = 0; i < halfLen; i++) {
                for (int j1 = i, j2 = i + halfLen; j1 < count; j1 += len, j2 += len) {
                    long u = a[j1], v = w * a[j2] % mod;
                    a[j1] = u + v;
                    if (a[j1] >= mod) a[j1] -= mod;
                    a[j2] = u - v;
                    if (a[j2] < 0) a[j2] += mod;
                }
                w = w * angle % mod;
            }
        }
        if (invert) {
            long inv = mm.inv(count);
            for (int i = 0; i < count; i++) a[i] = a[i] * inv % mod;
        }
    }

    public long[] convolve(long[] aRaw, long[] bRaw) {
        int total = aRaw.length + bRaw.length - 1;
        int len = 1 << (32 - Integer.numberOfLeadingZeros(total - 1));
        long[] a = Arrays.copyOf(aRaw, len), b = Arrays.copyOf(bRaw, len);
        fft(a, false);
        fft(b, false);
        for (int i = 0; i < len; i++) a[i] = a[i] * b[i] % mod;
        fft(a, true);
        return Arrays.copyOf(a, total);
    }


}
