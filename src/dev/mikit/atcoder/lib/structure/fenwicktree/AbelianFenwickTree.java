package dev.mikit.atcoder.lib.structure.fenwicktree;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class AbelianFenwickTree<T> extends FenwickTree<T> {

    private UnaryOperator<T> rev;

    public AbelianFenwickTree(T[] array, BinaryOperator<T> op, T zero, UnaryOperator<T> rev) {
        super(array, op, zero);
        this.rev = rev;
    }

    public T query(int first, int last) {
        if (first == 0) {
            return query(last);
        } else {
            return op.apply(query(last), rev.apply(query(first - 1)));
        }
    }
}
