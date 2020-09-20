package dev.mikit.atcoder.lib.structure.segmenttree;

import dev.mikit.atcoder.lib.meta.Verified;
import dev.mikit.atcoder.lib.util.Reflection;
import dev.mikit.atcoder.lib.util.function.ObjIntFunction;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

@Verified("https://atcoder.jp/contests/practice2/submissions/16923269")
public class LazySegmentTree<T, U> {
    private final int n;
    private final int m;
    private final int log;
    private final BinaryOperator<T> op;
    private final T zero;
    private final BiFunction<T, U, T> up;
    private final BinaryOperator<U> merge;
    private final U nop;
    private final ObjIntFunction<U, U> mul; // 更新の一括併合

    private final T[] tree;
    private final U[] lazy;

    public LazySegmentTree(T[] array, BinaryOperator<T> op, T zero, BiFunction<T, U, T> up, BinaryOperator<U> merge, U nop, ObjIntFunction<U, U> mul) {
        this.n = array.length;
        this.log = n == 1 ? 0 : 32 - Integer.numberOfLeadingZeros(n - 1);
        this.m = 1 << this.log;
        this.op = op;
        this.zero = zero;
        this.up = up;
        this.merge = merge;
        this.nop = nop;
        this.mul = mul;
        this.tree = Reflection.newInstance(Reflection.getComponentClass(array), 2 * m);
        this.lazy = Reflection.newInstance(Reflection.getClass(nop), m);
        Arrays.fill(tree, this.zero);
        Arrays.fill(lazy, this.nop);
        System.arraycopy(array, 0, tree, m, n);
        for (int i = m - 1; i > 0; i--) {
            tree[i] = op.apply(tree[(i << 1)], tree[(i << 1) | 1]);
        }
    }

    public LazySegmentTree(T[] array, BinaryOperator<T> op, T zero, BiFunction<T, U, T> up, BinaryOperator<U> merge, U nop) {
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
        U widen = mul.apply(lazy[k], len >> 1);
        tree[lk] = up.apply(tree[lk], widen);
        tree[rk] = up.apply(tree[rk], widen);
        if (lk < m) lazy[lk] = merge.apply(lazy[lk], lazy[k]);
        if (rk < m) lazy[rk] = merge.apply(lazy[rk], lazy[k]);
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

    public T query(int l, int r) {
        if (l >= r) return zero;
        l += m;
        r += m;
        pushTo(l, r);
        T sumL = zero, sumR = zero;
        while (l < r) {
            if ((l & 1) == 1) sumL = op.apply(sumL, tree[l++]);
            if ((r & 1) == 1) sumR = op.apply(tree[--r], sumR);
            l >>= 1;
            r >>= 1;
        }
        return op.apply(sumL, sumR);
    }

    public T get(int i) {
        i += m;
        pushTo(i);
        return tree[i];
    }

    public T total() {
        return tree[1];
    }

    public void update(int i, U v) {
        i += m;
        pushTo(i);
        tree[i] = up.apply(tree[i], v);
        i >>= 1;
        while (i > 0) {
            tree[i] = op.apply(tree[(i << 1)], tree[(i << 1) | 1]);
            i >>= 1;
        }
    }

    public void update(int l, int r, U v) {
        if (l >= r) return;
        l += m;
        r += m;
        pushTo(l, r);
        for (int lo = l, hi = r; lo < hi; ) {
            if ((lo & 1) == 1) {
                tree[lo] = up.apply(tree[lo], mul.apply(v, width(lo)));
                if (lo < m) lazy[lo] = merge.apply(lazy[lo], v);
                lo++;
            }
            if ((hi & 1) == 1) {
                hi--;
                tree[hi] = up.apply(tree[hi], mul.apply(v, width(hi)));
                if (hi < m) lazy[hi] = merge.apply(lazy[hi], v);
            }
            lo >>= 1;
            hi >>= 1;
        }
        for (int i = 1; i <= log; i++) {
            if (((l >> i) << i) != l) {
                int lki = l >> i;
                tree[lki] = op.apply(tree[(lki << 1)], tree[(lki << 1) | 1]);
            }
            if (((r >> i) << i) != r) {
                int rki = (r - 1) >> i;
                tree[rki] = op.apply(tree[(rki << 1)], tree[(rki << 1) | 1]);
            }
        }
    }

    public int maxRight(int l, Predicate<T> g) {
        if (!g.test(zero)) throw new IllegalArgumentException("Identity element must satisfy the condition.");
        if (n <= l) return n;
        l += m;
        pushTo(l);
        T sum = zero;
        do {
            l >>= Integer.numberOfTrailingZeros(l);
            if (!g.test(op.apply(sum, tree[l]))) {
                while (l < m) {
                    push(l);
                    l = l << 1;
                    if (g.test(op.apply(sum, tree[l]))) {
                        sum = op.apply(sum, tree[l]);
                        l++;
                    }
                }
                return l - m;
            }
            sum = op.apply(sum, tree[l]);
            l++;
        } while ((l & -l) != l);
        return n;
    }

    public int minLeft(int r, Predicate<T> g) {
        if (!g.test(zero)) throw new IllegalArgumentException("Identity element must satisfy the condition.");
        if (r <= 0) return 0;
        r += m;
        pushTo(r - 1);
        T sum = zero;
        do {
            r--;
            while (r > 1 && (r & 1) == 1) r >>= 1;
            if (!g.test(op.apply(tree[r], sum))) {
                while (r < m) {
                    push(r);
                    r = r << 1 | 1;
                    if (g.test(op.apply(tree[r], sum))) {
                        sum = op.apply(tree[r], sum);
                        r--;
                    }
                }
                return r + 1 - m;
            }
            sum = op.apply(tree[r], sum);
        } while ((r & -r) != r);
        return 0;
    }

    public int size() {
        return n;
    }
}