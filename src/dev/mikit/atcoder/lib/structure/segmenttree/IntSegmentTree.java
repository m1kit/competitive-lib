package dev.mikit.atcoder.lib.structure.segmenttree;

import dev.mikit.atcoder.lib.meta.Verified;

import java.util.Arrays;
import java.util.function.LongBinaryOperator;
import java.util.function.LongPredicate;

@Verified({
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3356247",
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3356253",
        "https://atcoder.jp/contests/practice2/submissions/16910553",
})
public class IntSegmentTree {

    private final int n;
    private final int m;
    private final long[] tree;
    private final LongBinaryOperator op, up; // op: 区間併合, up: 要素更新
    private final long zero; // 演算opの単位元

    public IntSegmentTree(long[] array, LongBinaryOperator op, long zero, LongBinaryOperator up) {
        this.n = array.length;
        this.m = n == 1 ? 1 : Integer.highestOneBit(n - 1) << 1;
        this.tree = new long[2 * m];
        this.op = op;
        this.up = up;
        this.zero = zero;
        Arrays.fill(tree, zero);
        System.arraycopy(array, 0, this.tree, m, n);
        for (int i = m - 1; i > 0; i--) {
            tree[i] = op.applyAsLong(tree[(i << 1)], tree[(i << 1) | 1]);
        }
    }

    public long get(int i) {
        return tree[m + i];
    }

    public void update(int i, long v) {
        i += m;
        tree[i] = up.applyAsLong(tree[i], v);
        while (i > 0) {
            i >>= 1;
            tree[i] = op.applyAsLong(tree[(i << 1)], tree[(i << 1) | 1]);
        }
    }

    public long query(int l, int r) {
        long left = zero, right = zero;
        l += m;
        r += m;
        while (l < r) {
            if ((l & 1) == 1) {
                left = op.applyAsLong(left, tree[l++]);
            }
            if ((r & 1) == 1) {
                right = op.applyAsLong(tree[--r], right);
            }
            l >>= 1;
            r >>= 1;
        }
        return op.applyAsLong(left, right);
    }

    public long total() {
        return tree[1];
    }

    public int maxRight(int l, LongPredicate f) {
        if (!f.test(zero)) {
            throw new IllegalArgumentException("Identity element must satisfy the condition.");
        }
        if (n <= l) return n;
        l += m;
        long sum = zero;
        do {
            l >>= Integer.numberOfTrailingZeros(l );
            if (!f.test(op.applyAsLong(sum, tree[l]))) {
                while (l < m) {
                    l = l << 1;
                    if (f.test(op.applyAsLong(sum, tree[l]))) {
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

    public int minLeft(int r, LongPredicate f) {
        if (!f.test(zero)) {
            throw new IllegalArgumentException("Identity element must satisfy the condition.");
        }
        if (r <= 0) return 0;
        r += m;
        long sum = zero;
        do {
            r--;
            while (r > 1 && (r & 1) == 1) r >>= 1;
            if (!f.test(op.applyAsLong(tree[r], sum))) {
                while (r < m) {
                    r = r << 1 | 1;
                    if (f.test(op.applyAsLong(tree[r], sum))) {
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
