package dev.mikit.atcoder.lib.structure.segmenttree;

import dev.mikit.atcoder.lib.util.Reflection;
import dev.mikit.atcoder.lib.math.BitMath;
import dev.mikit.atcoder.lib.meta.Verified;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

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
        this.tree = Reflection.newInstance(Reflection.getComponentClass(array), 2 * m - 1);
        this.op = op;
        this.up = up;
        this.zero = zero;
        Arrays.fill(tree, zero);
        System.arraycopy(array, 0, this.tree, m - 1, array.length);
        for (int i = m - 2; i >= 0; i--) {
            tree[i] = op.apply(tree[2 * i + 1], tree[2 * i + 2]);
        }
    }

    public T get(int i) {
        return tree[m + i - 1];
    }

    public void update(int i, U v) {
        i += m - 1;
        tree[i] = up.apply(tree[i], v);
        while (i > 0) {
            i = (i - 1) / 2;
            tree[i] = op.apply(tree[2 * i + 1], tree[2 * i + 2]);
        }
    }

    public T query(int l, int r) {
        T left = zero, right = zero;
        l += m - 1;
        r += m - 1;
        while (l < r) {
            if ((l & 1) == 0) {
                left = op.apply(left, tree[l]);
            }
            if ((r & 1) == 0) {
                right = op.apply(tree[r - 1], right);
            }
            l = l / 2;
            r = (r - 1) / 2;
        }
        return op.apply(left, right);
    }
}
