package dev.mikit.atcoder.lib.structure.sparsetable;

import dev.mikit.atcoder.lib.math.BitMath;
import dev.mikit.atcoder.lib.meta.Verified;

import java.util.function.LongBinaryOperator;

@Verified({
        "https://judge.yosupo.jp/submission/2596",
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=4093254#1",
        "https://atcoder.jp/contests/abc125/submissions/9338408",
})
public class IntSparseTable {

    private final int n;
    private final LongBinaryOperator f;
    private final long[][] table;

    public IntSparseTable(long[] a, LongBinaryOperator f) {
        this.n = a.length;
        this.f = f;
        this.table = new long[30][];
        table[0] = a.clone();
        for (int i = 1; (1 << i) < n; i++) {
            table[i] = new long[n];
            int r = 1 << i, d = r + r;
            for (int j = r - 1; j < n; j += d) {
                table[i][j] = a[j];
                for (int k = 1; k < r; k++)
                    table[i][j - k] = f.applyAsLong(a[j - k], table[i][j - k + 1]);
            }
            for (int j = r; j < n; j += d) {
                table[i][j] = a[j];
                for (int k = j + 1; k < j + r && k < n; k++)
                    table[i][k] = f.applyAsLong(table[i][k - 1], a[k]);
            }
        }
    }

    public long query(int l, int r) {
        if (r <= l || l < 0 || n < r) throw new RuntimeException("Illegal access (" + l + ", " + r + ")");
        if (l == --r) return table[0][l];
        int k = BitMath.msb(l ^ r);
        return f.applyAsLong(table[k][l], table[k][r]);
    }

    public long except(int i) {
        if (i == 0) return query(0, n);
        if (i == n - 1) return query(0, n - 1);
        return f.applyAsLong(query(0, i), query(i + 1, n));
    }
}
