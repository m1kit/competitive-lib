package dev.mikit.atcoder.lib.structure;

import dev.mikit.atcoder.lib.math.BitMath;
import dev.mikit.atcoder.lib.meta.Verified;
import dev.mikit.atcoder.lib.util.function.LongIntToLongFunction;
import java.util.Arrays;
import java.util.function.LongBinaryOperator;

@Verified({
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3354722",
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3354723",
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3354921",
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3354942",
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3355020",
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3355038",
})
public class IntLazySegmentTree {

    private final int n;
    private final int m;
    private final long[] tree, lazy;
    private final LongBinaryOperator op;
    private final long zero;
    private final LongBinaryOperator up; // 要素の更新
    private final LongBinaryOperator merge;
    private final long nop; // 更新の併合に関するモノイド
    private final LongIntToLongFunction mul; // 更新の一括併合

    public IntLazySegmentTree(long[] array, LongBinaryOperator op, long zero, LongBinaryOperator up,
                              LongBinaryOperator merge, long nop,
                              LongIntToLongFunction mul) {
        this.n = array.length;
        int msb = BitMath.extractMsb(n);
        this.m = n == msb ? msb : (msb << 1);
        this.op = op;
        this.zero = zero;
        this.up = up;
        this.merge = merge;
        this.nop = nop;
        this.mul = mul;
        this.tree = new long[m * 2 - 1];
        Arrays.fill(tree, zero);
        System.arraycopy(array, 0, this.tree, m - 1, array.length);
        this.lazy = new long[m * 2 - 1];
        Arrays.fill(lazy, nop);
        for (int i = m - 2; i >= 0; i--) {
            tree[i] = op.applyAsLong(tree[2 * i + 1], tree[2 * i + 2]);
        }
    }

    public IntLazySegmentTree(long[] array, LongBinaryOperator op, long zero, LongBinaryOperator up,
                              LongBinaryOperator merge, long nop) {
        this(array, op, zero, up, merge, nop, (q, n) -> q);
    }

    private void eval(int len, int k) {
        if (lazy[k] == nop) {
            return;
        } else if (k * 2 + 1 < m * 2 - 1) {
            lazy[k * 2 + 1] = merge.applyAsLong(lazy[k * 2 + 1], lazy[k]);
            lazy[k * 2 + 2] = merge.applyAsLong(lazy[k * 2 + 2], lazy[k]);
        }
        tree[k] = up.applyAsLong(tree[k], mul.applyAsLong(lazy[k], len));
        lazy[k] = nop;
    }

    public void update(int i, long v) {
        i += m - 1;
        eval(1, i);
        tree[i] = up.applyAsLong(tree[i], v);
        while (i > 0) {
            i = (i - 1) / 2;
            tree[i] = op.applyAsLong(tree[2 * i + 1], tree[2 * i + 2]);
        }
    }

    private long update(int l, int r, long q, int k, int sl, int sr) {
        if (r <= sl || sr <= l) {
            eval(sr - sl, k);
            return tree[k];
        }
        if (l <= sl && sr <= r) {
            lazy[k] = merge.applyAsLong(lazy[k], q);
            eval(sr - sl, k);
            return tree[k];
        } else {
            eval(sr - sl, k);
            return tree[k] = op.applyAsLong(
                    update(l, r, q, k * 2 + 1, sl, (sl + sr) / 2),
                    update(l, r, q, k * 2 + 2, (sl + sr) / 2, sr)
            );
        }
    }

    public void update(int l, int r, long q) {
        update(l, r, q, 0, 0, m);
    }

    private long query(int l, int r, int k, int sl, int sr) {
        if (r <= sl || sr <= l) {
            return zero;
        }
        eval(sr - sl, k);
        if (l <= sl && sr <= r) {
            return tree[k];
        } else {
            long left = query(l, r, 2 * k + 1, sl, (sl + sr) / 2);
            long right = query(l, r, 2 * k + 2, (sl + sr) / 2, sr);
            return op.applyAsLong(left, right);
        }
    }

    public long query(int l, int r) {
        return query(l, r, 0, 0, m);
    }

    public void print() {
        int br = 0;
        for (int i = 0; i < 2 * m - 1; i++) {
            System.out.print(tree[i] + "/" + lazy[i] + " ");
            if (i == br) {
                System.out.println();
                br = 2 * br + 2;
            }
        }
    }
}
