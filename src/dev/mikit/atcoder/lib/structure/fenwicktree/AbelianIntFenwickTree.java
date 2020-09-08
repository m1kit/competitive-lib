package dev.mikit.atcoder.lib.structure.fenwicktree;

import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

public class AbelianIntFenwickTree extends IntFenwickTree {

    private final LongUnaryOperator rev;

    public AbelianIntFenwickTree(int n, LongBinaryOperator op, long zero, LongUnaryOperator rev) {
        super(n, op, zero);
        this.rev = rev;
    }

    public AbelianIntFenwickTree(long[] init, LongBinaryOperator op, long zero, LongUnaryOperator rev) {
        super(init, op, zero);
        this.rev = rev;
    }

    public long query(int first, int last) {
        if (first == 0) {
            return query(last);
        } else {
            return op.applyAsLong(query(last), rev.applyAsLong(query(first)));
        }
    }
}
