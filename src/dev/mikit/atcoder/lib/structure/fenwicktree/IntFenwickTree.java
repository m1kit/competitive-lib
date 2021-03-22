package dev.mikit.atcoder.lib.structure.fenwicktree;

import dev.mikit.atcoder.lib.math.BitMath;

import java.util.Arrays;
import java.util.function.LongBinaryOperator;

public class IntFenwickTree {

    protected final int n;
    private final long[] tree;
    protected final LongBinaryOperator op;
    protected final long zero;

    public IntFenwickTree(int n, LongBinaryOperator op, long zero) {
        this.n = n;
        tree = new long[n + 1];
        Arrays.fill(tree, zero);
        this.op = op;
        this.zero = zero;
    }

    public IntFenwickTree(long[] init, LongBinaryOperator op, long zero) {
        this.n = init.length;
        this.op = op;
        this.tree = new long[n + 1];
        this.zero = zero;
        System.arraycopy(init, 0, tree, 1, n);
        for (int i = 1; i <= n; ++i) {
            int j = i + (i & -i);
            if (j <= n) tree[j] = op.applyAsLong(tree[j], tree[i]);
        }
    }

    public int size() {
        return n;
    }

    public void add(int index, long value) {
        for (index++; index <= n; index += BitMath.extractLsb(index)) {
            tree[index] = op.applyAsLong(tree[index], value);
        }
    }

    public long query(int last) {
        long res = zero;
        for (; last > 0; last -= BitMath.extractLsb(last)) {
            res = op.applyAsLong(res, tree[last]);
        }
        return res;
    }
}
