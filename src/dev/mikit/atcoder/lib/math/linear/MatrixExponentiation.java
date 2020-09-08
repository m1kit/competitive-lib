package dev.mikit.atcoder.lib.math.linear;

import java.util.function.LongBinaryOperator;

public class MatrixExponentiation {

    private static final int DEFAULT_SIZE = 64;
    private final int size;
    private final long zero, one;
    private final LongBinaryOperator add, prod;
    private final IntMatrix[] exps;

    public MatrixExponentiation(IntMatrix e0, int size, long zero, long one, LongBinaryOperator add, LongBinaryOperator prod) {
        this.size = size;
        this.zero = zero;
        this.one = one;
        this.add = add;
        this.prod = prod;
        exps = new IntMatrix[size];
        exps[0] = e0;
        for (int i = 1; i < size; i++) {
            exps[i] = exps[i - 1].product(exps[i - 1], zero, add, prod);
        }
    }

    public MatrixExponentiation(IntMatrix e0, long zero, long one, LongBinaryOperator add, LongBinaryOperator prod) {
        this(e0, DEFAULT_SIZE, zero, one, add, prod);
    }

    public MatrixExponentiation(IntMatrix e0, int size) {
        this.size = size;
        zero = 0;
        one = 1;
        add = null;
        prod = null;
        exps = new IntMatrix[size];
        exps[0] = e0;
        for (int i = 1; i < size; i++) {
            exps[i] = exps[i - 1].product(exps[i - 1]);
        }
    }

    public MatrixExponentiation(IntMatrix e0) {
        this(e0, DEFAULT_SIZE);
    }

    public IntMatrix power(long p) {
        IntMatrix res = IntMatrix.getUnitMatrix(exps[0].getRows(), zero, one);
        for (int i = 0; i < size; i++) {
            if (((1L << i) & p) == 0) {
                continue;
            }
            if (add == null) {
                res = res.product(exps[i]);
            } else {
                res = res.product(exps[i], zero, add, prod);
            }
        }
        return res;
    }
}
