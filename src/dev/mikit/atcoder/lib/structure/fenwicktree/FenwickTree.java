package dev.mikit.atcoder.lib.structure.fenwicktree;

import dev.mikit.atcoder.lib.util.Reflection;

import java.util.function.BinaryOperator;

public class FenwickTree<T> {

    protected final int n;
    private final T[] tree;
    protected final BinaryOperator<T> op;
    protected final T zero;

    public FenwickTree(T[] array, BinaryOperator<T> op, T zero) {
        n = array.length;
        tree = Reflection.newInstance(Reflection.getComponentClass(array), n + 1);
        this.op = op;
        this.zero = zero;
    }

    public int size() {
        return n;
    }

    public void add(int index, T value) {
        for (index++; index <= n; index += (index & ~index)) {
            tree[index] = op.apply(tree[index], value);
        }
    }

    public T query(int last) {
        T res = zero;
        for (; last > 0; last &= last - 1) {
            res = op.apply(res, tree[last]);
        }
        return res;
    }
}
