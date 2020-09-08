package dev.mikit.atcoder.lib.structure.sparsetable;

import dev.mikit.atcoder.lib.math.BitMath;
import dev.mikit.atcoder.lib.meta.Verified;
import dev.mikit.atcoder.lib.util.Reflection;

import java.util.function.BinaryOperator;

@Verified({
        "https://judge.yosupo.jp/submission/2595",
})
public class SparseTable<T> {

    private final int n;
    private final BinaryOperator<T> f;
    private final T[][] table;

    public SparseTable(T[] a, BinaryOperator<T> f) {
        this.n = a.length;
        this.f = f;
        this.table = Reflection.newInstance(Reflection.getClass(a), 30);
        table[0] = a.clone();
        for (int i = 1; (1 << i) < n; i++) {
            table[i] = Reflection.newInstance(Reflection.getComponentClass(a), n);
            int r = 1 << i, d = r + r;
            for (int j = r - 1; j < n; j += d) {
                table[i][j] = a[j];
                for (int k = 1; k < r; k++)
                    table[i][j - k] = f.apply(a[j - k], table[i][j - k + 1]);
            }
            for (int j = r; j < n; j += d) {
                table[i][j] = a[j];
                for (int k = j + 1; k < j + r && k < n; k++)
                    table[i][k] = f.apply(table[i][k - 1], a[k]);
            }
        }
    }

    public T query(int l, int r) {
        if (r <= l || l < 0 || n < r) throw new RuntimeException();
        if (l == --r) return table[0][l];
        int k = BitMath.msb(l ^ r);
        return f.apply(table[k][l], table[k][r]);
    }

    public T except(int i) {
        if (i == 0) return query(0, n);
        if (i == n - 1) return query(0, n - 1);
        return f.apply(query(0, i), query(i + 1, n));
    }

}
