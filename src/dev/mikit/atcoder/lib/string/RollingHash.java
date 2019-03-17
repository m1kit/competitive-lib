package dev.mikit.atcoder.lib.string;

import dev.mikit.atcoder.lib.math.mod.Exponentiation;
import dev.mikit.atcoder.lib.math.mod.ModMath;

public class RollingHash {

    private static final int[] DEFAULT_MOD = {999999937, 1000000007, 1000000009};
    private static final int DEFAULT_BASE = 9973;

    private final int[] mod;
    private final Exponentiation[] power;
    private final long[][] hash;

    public RollingHash(String str, int[] mod, int base) {
        this.mod = mod;
        int n = str.length(), m = mod.length;
        hash = new long[m][n + 1];
        power = new Exponentiation[m];
        for (int i = 0; i < m; i++) {
            power[i] = new ModMath(mod[i]).getExponentiation(base, n + 1);
            hash[i][0] = 0;
            for (int j = 0; j < n; j++) {
                hash[i][j + 1] = ((hash[i][j] * base + str.charAt(j)) % mod[i]);
            }
        }
    }


    public RollingHash(int[] str, int[] mod, int base) {
        this.mod = mod;
        int n = str.length, m = mod.length;
        hash = new long[m][n + 1];
        power = new Exponentiation[m];
        for (int i = 0; i < m; i++) {
            power[i] = new ModMath(mod[i]).getExponentiation(base, n + 1);
            hash[i][0] = 0;
            for (int j = 0; j < n; j++) {
                hash[i][j + 1] = ((hash[i][j] * base + str[j]) % mod[i]);
            }
        }
    }

    public RollingHash(String str) {
        this(str, DEFAULT_MOD, DEFAULT_BASE);
    }

    public RollingHash(int[] str) {
        this(str, DEFAULT_MOD, DEFAULT_BASE);
    }

    public long hash(int l, int r, int i) {
        return ((hash[i][r] - hash[i][l] * power[i].pow(r - l)) % mod[i] + mod[i]) % mod[i];
    }

    public boolean match(int l1, int r1, int l2, int r2) {
        for (int i = 0; i < mod.length; i++) {
            if (hash(l1, r1, i) != hash(l2, r2, i)) {
                return false;
            }
        }
        return true;
    }
}
