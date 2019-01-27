package jp.llv.atcoder.lib.structure;

import jp.llv.atcoder.lib.math.BitMath;
import jp.llv.atcoder.lib.meta.Verified;

import java.util.Arrays;
import java.util.function.LongBinaryOperator;

@Verified({
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3356247",
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3356253",
})
public class IntSegmentTree {

    private final int n;
    private final int m;
    private final long[] tree;
    private final LongBinaryOperator op, up; // op: 区間併合, up: 要素更新
    private final long zero; // 演算opの単位元

    public IntSegmentTree(long[] array, LongBinaryOperator op, long zero, LongBinaryOperator up) {
        this.n = array.length;
        int msb = BitMath.extractMsb(n);
        this.m = n == msb ? msb : (msb << 1);
        this.tree = new long[m * 2 - 1];
        this.op = op;
        this.up = up;
        this.zero = zero;
        Arrays.fill(tree, zero);
        System.arraycopy(array, 0, this.tree, m - 1, array.length);
        for (int i = m - 2; i >= 0; i--) {
            tree[i] = op.applyAsLong(tree[2 * i + 1], tree[2 * i + 2]);
        }
    }

    public long get(int i) {
        return tree[m + i - 1];
    }

    public void update(int i, long v) {
        i += m - 1;
        tree[i] = up.applyAsLong(tree[i], v);
        while (i > 0) {
            i = (i - 1) / 2;
            tree[i] = op.applyAsLong(tree[2 * i + 1], tree[2 * i + 2]);
        }
    }

    public long query(int l, int r) {
        long left = zero, right = zero;
        l += m - 1;
        r += m - 1;
        while (l < r) {
            if ((l & 1) == 0) {
                left = op.applyAsLong(left, tree[l]);
            }
            if ((r & 1) == 0) {
                right = op.applyAsLong(tree[r - 1], right);
            }
            l = l / 2;
            r = (r - 1) / 2;
        }
        return op.applyAsLong(left, right);
    }

}
