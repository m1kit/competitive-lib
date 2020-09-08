package dev.mikit.atcoder.lib.structure.fenwicktree;

import dev.mikit.atcoder.lib.math.BitMath;
import dev.mikit.atcoder.lib.meta.Verified;
import dev.mikit.atcoder.lib.util.Reflection;

import java.util.Arrays;
import java.util.function.BinaryOperator;

@Verified({
        "http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3449394",
})
public class FenwickTree<T> {

    protected final int n;
    private final T[] tree;
    protected final BinaryOperator<T> op;
    protected final T zero;

    public FenwickTree(int n, BinaryOperator<T> op, T zero) {
        this.n = n;
        tree = Reflection.newInstance(Reflection.getClass(zero), n + 1);
        Arrays.fill(tree, zero);
        this.op = op;
        this.zero = zero;
    }

    public int size() {
        return n;
    }

    public void add(int index, T value) {
        for (index++; index <= n; index += BitMath.extractLsb(index)) {
            tree[index] = op.apply(tree[index], value);
        }
    }

    public T query(int last) {
        T res = zero;
        for (; last > 0; last -= BitMath.extractLsb(last)) {
            res = op.apply(res, tree[last]);
        }
        return res;
    }
}
