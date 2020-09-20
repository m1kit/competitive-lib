package dev.mikit.atcoder.lib.structure.segmenttree;

import dev.mikit.atcoder.lib.util.Reflection;
import dev.mikit.atcoder.lib.math.BitMath;
import dev.mikit.atcoder.lib.meta.Verified;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

@Verified({
        "https://atcoder.jp/contests/arc008/submissions/4094461",
})
public class SegmentTree<T, U> {

    private final int n;
    private final int m;
    private final T[] tree;
    private final BinaryOperator<T> op; // 区間の併合
    private final T zero; // opの単位元
    private final BiFunction<T, U, T> up; // 要素の更新


    public SegmentTree(T[] array, BinaryOperator<T> op, T zero, BiFunction<T, U, T> up) {
        this.n = array.length;
        this.m = n == 1 ? 1 : Integer.highestOneBit(n - 1) << 1;
        this.tree = Reflection.newInstance(Reflection.getComponentClass(array), 2 * m);
        this.op = op;
        this.up = up;
        this.zero = zero;
        Arrays.fill(tree, zero);
        System.arraycopy(array, 0, this.tree, m, array.length);
        for (int i = m - 1; i > 0; i--) {
            tree[i] = op.apply(tree[(i << 1)], tree[(i << 1) | 1]);
        }
    }

    public T get(int i) {
        return tree[m + i];
    }

    public void update(int i, U v) {
        i += m;
        tree[i] = up.apply(tree[i], v);
        while (i > 0) {
            i >>= 1;
            tree[i] = op.apply(tree[(i << 1)], tree[(i << 1) | 1]);
        }
    }

    public T query(int l, int r) {
        T left = zero, right = zero;
        l += m;
        r += m;
        while (l < r) {
            if ((l & 1) == 1) {
                left = op.apply(left, tree[l++]);
            }
            if ((r & 1) == 1) {
                right = op.apply(tree[--r], right);
            }
            l >>= 1;
            r >>= 1;
        }
        return op.apply(left, right);
    }

    public T total() {
        return tree[1];
    }

    public int maxRight(int l, Predicate<T> f) {
        if (!f.test(zero)) {
            throw new IllegalArgumentException("Identity element must satisfy the condition.");
        }
        if (n <= l) return n;
        l += m;
        T sum = zero;
        do {
            l >>= Integer.numberOfTrailingZeros(l );
            if (!f.test(op.apply(sum, tree[l]))) {
                while (l < m) {
                    l = l << 1;
                    if (f.test(op.apply(sum, tree[l]))) {
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

    public int minLeft(int r, Predicate<T> f) {
        if (!f.test(zero)) {
            throw new IllegalArgumentException("Identity element must satisfy the condition.");
        }
        if (r <= 0) return 0;
        r += m;
        T sum = zero;
        do {
            r--;
            while (r > 1 && (r & 1) == 1) r >>= 1;
            if (!f.test(op.apply(tree[r], sum))) {
                while (r < m) {
                    r = r << 1 | 1;
                    if (f.test(op.apply(tree[r], sum))) {
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
