package dev.mikit.atcoder.lib.structure.segmenttree;

import dev.mikit.atcoder.lib.meta.Verified;
import dev.mikit.atcoder.lib.util.Reflection;

import java.util.function.Supplier;

@Verified("https://atcoder.jp/contests/arc115/submissions/21158686")
public class InplaceLazySegmentTree<E extends InplaceLazySegmentTree.Element<E, U>, U extends InplaceLazySegmentTree.Update<E, U>> {
    private final int n;
    private final int m;
    private final int log;
    private final Supplier<E> zero;

    private final E[] tree;
    private final U[] lazy;

    public InplaceLazySegmentTree(E[] array, Supplier<E> zero, Supplier<U> nop) {
        this.n = array.length;
        this.log = n == 1 ? 0 : 32 - Integer.numberOfLeadingZeros(n - 1);
        this.m = 1 << this.log;
        this.zero = zero;
        this.tree = Reflection.newInstance(Reflection.getComponentClass(array), 2 * m);
        this.lazy = Reflection.newInstance(Reflection.getClass(nop.get()), m);
        for (int i = 0; i < m; i++) lazy[i] = nop.get();
        for (int i = 0; i < m; i++) tree[i] = zero.get();
        for (int i = m + n; i < 2 * m; i++) tree[i] = zero.get();
        System.arraycopy(array, 0, tree, m, n);
        for (int i = m - 1; i > 0; i--) {
            tree[i].mergeFrom(tree[(i << 1)], tree[(i << 1) | 1]);
        }
    }

    private int width(int k) {
        return m >> (31 - Integer.numberOfLeadingZeros(k));
    }

    private void push(int k) {
        push(k, width(k));
    }

    private void push(int k, int len) {
        if (lazy[k].isUnit()) return;
        int lk = (k << 1), rk = (k << 1) | 1;
        U widen = lazy[k].widen(len >> 1);
        tree[lk].apply(widen);
        tree[rk].apply(widen);
        if (lk < m) lazy[lk].merge(lazy[k]);
        if (rk < m) lazy[rk].merge(lazy[k]);
        lazy[k].clear();
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

    public E query(int l, int r) {
        if (l >= r) return zero.get();
        l += m;
        r += m;
        pushTo(l, r);
        E sumL = zero.get(), sumR = zero.get();
        while (l < r) {
            if ((l & 1) == 1) sumL.mergeFrom(sumL, tree[l++]);
            if ((r & 1) == 1) sumR.mergeFrom(tree[--r], sumR);
            l >>= 1;
            r >>= 1;
        }
        sumL.mergeFrom(sumL, sumR);
        return sumL;
    }

    public E get(int i) {
        i += m;
        pushTo(i);
        return tree[i];
    }

    public E total() {
        return tree[1];
    }

    public void update(U v) {
        lazy[1].merge(v);
    }

    public void update(int i, U v) {
        i += m;
        pushTo(i);
        tree[i].apply(v);
        i >>= 1;
        while (i > 0) {
            tree[i].mergeFrom(tree[(i << 1)], tree[(i << 1) | 1]);
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
                tree[lo].apply(v.widen(width(lo)));
                if (lo < m) lazy[lo].merge(v);
                lo++;
            }
            if ((hi & 1) == 1) {
                hi--;
                tree[hi].apply(v.widen(width(hi)));
                if (hi < m) lazy[hi].merge(v);
            }
            lo >>= 1;
            hi >>= 1;
        }
        for (int i = 1; i <= log; i++) {
            if (((l >> i) << i) != l) {
                int lki = l >> i;
                tree[lki].mergeFrom(tree[(lki << 1)], tree[(lki << 1) | 1]);
            }
            if (((r >> i) << i) != r) {
                int rki = (r - 1) >> i;
                tree[rki].mergeFrom(tree[(rki << 1)], tree[(rki << 1) | 1]);
            }
        }
    }

    public void updateRight(int l, U v) {
        if (l >= n) return;
        l += m;
        int r = 2 * m;
        for (int i = log; i > 0; i--) {
            if (((l >> i) << i) != l) push(l >> i, 1 << i);
        }
        for (int lo = l, hi = r; lo < hi; ) {
            if ((lo & 1) == 1) {
                tree[lo].apply(v.widen(width(lo)));
                if (lo < m) lazy[lo].merge(v);
                lo++;
            }
            lo >>= 1;
            hi >>= 1;
        }
        for (int i = 1; i <= log; i++) {
            if (((l >> i) << i) != l) {
                int lki = l >> i;
                tree[lki].mergeFrom(tree[(lki << 1)], tree[(lki << 1) | 1]);
            }
        }
    }

    public void updateLeft(int r, U v) {
        if (0 >= r) return;
        int l = m;
        r += m;
        for (int i = log; i > 0; i--) {
            if (((r >> i) << i) != r) push(r >> i, 1 << i);
        }
        for (int lo = l, hi = r; lo < hi; ) {
            if ((hi & 1) == 1) {
                hi--;
                tree[hi].apply(v.widen(width(hi)));
                if (hi < m) lazy[hi].merge(v);
            }
            lo >>= 1;
            hi >>= 1;
        }
        for (int i = 1; i <= log; i++) {
            if (((r >> i) << i) != r) {
                int rki = (r - 1) >> i;
                tree[rki].mergeFrom(tree[(rki << 1)], tree[(rki << 1) | 1]);
            }
        }
    }

    public int size() {
        return n;
    }

    public interface Element<E extends Element<E, U>, U extends Update<E, U>> {
        void apply(U update);

        void mergeFrom(E e1, E e2);

        void clear();

        boolean isUnit();
    }

    public interface Update<E extends Element<E, U>, U extends Update<E, U>> {
        void merge(U u2);

        void clear();

        boolean isUnit();

        /* 非破壊的にwidenしなければならない */
        U widen(int width);
    }


}
