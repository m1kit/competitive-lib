package dev.mikit.atcoder.lib.structure.segmenttree;

import dev.mikit.atcoder.lib.util.function.LongIntToLongFunction;

import java.util.Arrays;
import java.util.function.LongBinaryOperator;
import java.util.function.LongPredicate;

public class IntLazySegmentTree {
    private final int n, m, log;
    private final long zero, nop;
    private final LongBinaryOperator op, up, merge;
    private final LongIntToLongFunction mul; // 更新の一括併合
    private final long[] tree;
    private final long[] lazy;

    public IntLazySegmentTree(long[] array, LongBinaryOperator op, long zero, LongBinaryOperator up, LongBinaryOperator merge, long nop, LongIntToLongFunction mul) {
        this.n = array.length;
        this.log = n == 1 ? 0 : 32 - Integer.numberOfLeadingZeros(n - 1);
        this.m = 1 << this.log;
        this.op = op;
        this.zero = zero;
        this.up = up;
        this.merge = merge;
        this.nop = nop;
        this.mul = mul;
        this.tree = new long[m << 1];
        this.lazy = new long[m];
        Arrays.fill(tree, this.zero);
        Arrays.fill(lazy, this.nop);
        System.arraycopy(array, 0, tree, m, n);
        for (int i = m - 1; i > 0; i--) {
            tree[i] = op.applyAsLong(tree[(i << 1)], tree[(i << 1) | 1]);
        }
    }

    public IntLazySegmentTree(long[] array, LongBinaryOperator op, long zero, LongBinaryOperator up, LongBinaryOperator merge, long nop) {
        this(array, op, zero, up, merge, nop, (u, w) -> u);
    }

    private int width(int k) {
        return m >> (31 - Integer.numberOfLeadingZeros(k));
    }

    private void push(int k) {
        push(k, width(k));
    }

    private void push(int k, int len) {
        if (lazy[k] == nop) return;
        int lk = (k << 1), rk = (k << 1) | 1;
        long widen = mul.applyAsLong(lazy[k], len >> 1);
        tree[lk] = up.applyAsLong(tree[lk], widen);
        tree[rk] = up.applyAsLong(tree[rk], widen);
        if (lk < m) lazy[lk] = merge.applyAsLong(lazy[lk], lazy[k]);
        if (rk < m) lazy[rk] = merge.applyAsLong(lazy[rk], lazy[k]);
        lazy[k] = nop;
    }

    private void pushTo(int i) {
        for (int j = log; j > 0; j--) push(i >> j, 1 << j);
    }

    private void pushTo(int l, int r) {
        for (int i = log; i > 0; i--) {
            int len = 1 << i;
            if (((l >> i) << i) != l) push(l >> i, len);
            if (((r >> i) << i) != r) push(r >> i, len);
        }
    }

    public long query(int l, int r) {
        if (l >= r) return zero;
        l += m;
        r += m;
        pushTo(l, r);
        long sumL = zero, sumR = zero;
        while (l < r) {
            if ((l & 1) == 1) sumL = op.applyAsLong(sumL, tree[l++]);
            if ((r & 1) == 1) sumR = op.applyAsLong(tree[--r], sumR);
            l >>= 1;
            r >>= 1;
        }
        return op.applyAsLong(sumL, sumR);
    }

    public long get(int i) {
        i += m;
        pushTo(i);
        return tree[i];
    }

    public long total() {
        return tree[1];
    }

    public void update(int i, long v) {
        i += m;
        pushTo(i);
        tree[i] = up.applyAsLong(tree[i], v);
        i >>= 1;
        while (i > 0) {
            tree[i] = op.applyAsLong(tree[(i << 1)], tree[(i << 1) | 1]);
            i >>= 1;
        }
    }

    public void update(int l, int r, long v) {
        if (l >= r) return;
        l += m;
        r += m;
        pushTo(l, r);
        for (int lo = l, hi = r; lo < hi; ) {
            if ((lo & 1) == 1) {
                tree[lo] = up.applyAsLong(tree[lo], mul.applyAsLong(v, width(lo)));
                if (lo < m) lazy[lo] = merge.applyAsLong(v, lazy[lo]);
                lo++;
            }
            if ((hi & 1) == 1) {
                hi--;
                tree[hi] = up.applyAsLong(tree[hi], mul.applyAsLong(v, width(hi)));
                if (hi < m) lazy[hi] = merge.applyAsLong(v, lazy[hi]);
            }
            lo >>= 1;
            hi >>= 1;
        }
        for (int i = 1; i <= log; i++) {
            if (((l >> i) << i) != l) {
                int lki = l >> i;
                tree[lki] = op.applyAsLong(tree[(lki << 1)], tree[(lki << 1) | 1]);
            }
            if (((r >> i) << i) != r) {
                int rki = (r - 1) >> i;
                tree[rki] = op.applyAsLong(tree[(rki << 1)], tree[(rki << 1) | 1]);
            }
        }
    }

    public int maxRight(int l, LongPredicate g) {
        if (!g.test(zero)) throw new IllegalArgumentException("Identity element must satisfy the condition.");
        if (n <= l) return n;
        l += m;
        pushTo(l);
        long sum = zero;
        do {
            l >>= Integer.numberOfTrailingZeros(l);
            if (!g.test(op.applyAsLong(sum, tree[l]))) {
                while (l < m) {
                    push(l);
                    l = l << 1;
                    if (g.test(op.applyAsLong(sum, tree[l]))) {
                        sum = op.applyAsLong(sum, tree[l]);
                        l++;
                    }
                }
                return l - m;
            }
            sum = op.applyAsLong(sum, tree[l]);
            l++;
        } while ((l & -l) != l);
        return n;
    }

    public int minLeft(int r, LongPredicate g) {
        if (!g.test(zero)) throw new IllegalArgumentException("Identity element must satisfy the condition.");
        if (r <= 0) return 0;
        r += m;
        pushTo(r - 1);
        long sum = zero;
        do {
            r--;
            while (r > 1 && (r & 1) == 1) r >>= 1;
            if (!g.test(op.applyAsLong(tree[r], sum))) {
                while (r < m) {
                    push(r);
                    r = r << 1 | 1;
                    if (g.test(op.applyAsLong(tree[r], sum))) {
                        sum = op.applyAsLong(tree[r], sum);
                        r--;
                    }
                }
                return r + 1 - m;
            }
            sum = op.applyAsLong(tree[r], sum);
        } while ((r & -r) != r);
        return 0;
    }

    public int size() {
        return n;
    }
}