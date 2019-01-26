package jp.llv.atcoder.lib.structure;

import jp.llv.atcoder.lib.math.BitMath;
import jp.llv.atcoder.lib.util.ArrayUtil;
import jp.llv.atcoder.lib.util.function.ObjIntFunction;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class LazySegmentTree<T, U> {

    private final int n;
    private final int m;
    private final T[] tree;
    private final U[] lazy;
    private final BinaryOperator<T> op;
    private final T zero; // 区間の併合に関するモノイド
    private final BiFunction<T, U, T> up; // 要素の更新
    private final BinaryOperator<U> merge;
    private final U nop; // 更新の併合に関するモノイド
    private final ObjIntFunction<U, U> mul; // 更新の一括併合

    public LazySegmentTree(T[] array, Class<T> elem, Class<U> query, BinaryOperator<T> op, T zero, BiFunction<T, U, T> up,
                           BinaryOperator<U> merge, U nop,
                           ObjIntFunction<U, U> mul) {
        this.n = array.length;
        this.op = op;
        this.up = up;
        this.zero = zero;
        this.merge = merge;
        this.nop = nop;
        this.mul = mul;
        int msb = BitMath.extractMsb(n);
        this.m = n == msb ? msb : (msb << 1);
        this.tree = ArrayUtil.newInstance(elem, 2 * m - 1);
        this.lazy = ArrayUtil.newInstance(query, 2 * m - 1);
        Arrays.fill(tree, zero);
        System.arraycopy(array, 0, this.tree, m - 1, array.length);
        Arrays.fill(lazy, nop);
        for (int i = m - 2; i >= 0; i--) {
            tree[i] = op.apply(tree[2 * i + 1], tree[2 * i + 2]);
        }
    }

    public LazySegmentTree(T[] array, Class<T> elem, Class<U> query, BinaryOperator<T> op, T zero, BiFunction<T, U, T> up,
                           BinaryOperator<U> merge, U nop) {
        this(array, elem, query, op, zero, up, merge, nop, (q, n) -> q);
    }

    private void eval(int len, int k) {
        if (lazy[k] == nop) {
            return;
        } else if (k * 2 + 1 < m * 2 - 1) {
            lazy[k * 2 + 1] = merge.apply(lazy[k * 2 + 1], lazy[k]);
            lazy[k * 2 + 2] = merge.apply(lazy[k * 2 + 2], lazy[k]);
        }
        tree[k] = up.apply(tree[k], mul.apply(lazy[k], len));
        lazy[k] = nop;
    }

    public void update(int i, U v) {
        i += m - 1;
        eval(1, i);
        tree[i] = up.apply(tree[i], v);
        while (i > 0) {
            i = (i - 1) / 2;
            tree[i] = op.apply(tree[2 * i + 1], tree[2 * i + 2]);
        }
    }

    private T update(int l, int r, U q, int k, int sl, int sr) {
        if (r <= sl || sr <= l) {
            eval(sr - sl, k);
            return tree[k];
        }
        if (l <= sl && sr <= r) {
            lazy[k] = merge.apply(lazy[k], q);
            eval(sr - sl, k);
            return tree[k];
        } else {
            eval(sr - sl, k);
            return tree[k] = op.apply(
                    update(l, r, q, k * 2 + 1, sl, (sl + sr) / 2),
                    update(l, r, q, k * 2 + 2, (sl + sr) / 2, sr)
            );
        }
    }

    public void update(int l, int r, U q) {
        update(l, r, q, 0, 0, m);
    }

    private T query(int l, int r, int k, int sl, int sr) {
        if (r <= sl || sr <= l) {
            return zero;
        }
        eval(r - l, k);
        if (l <= sl && sr <= r) {
            return tree[k];
        } else {
            T left = query(l, r, 2 * k + 1, sl, (sl + sr) / 2);
            T right = query(l, r, 2 * k + 2, (sl + sr) / 2, sr);
            return op.apply(left, right);
        }
    }

    public T query(int l, int r) {
        return query(l, r, 0, 0, m);
    }
}
