package dev.mikit.atcoder.lib.math.linear;

import java.util.function.LongBinaryOperator;

public class MatrixExponentiation {

    private static final int DEFAULT_SIZE = 64;
    private final int size;
    private final long zero;
    private final LongBinaryOperator add, prod;
    private final Matrix[] exps;

    public MatrixExponentiation(Matrix e0, int size, long zero, LongBinaryOperator add, LongBinaryOperator prod) {
        this.size = size;
        this.zero = zero;
        this.add = add;
        this.prod = prod;
        exps = new Matrix[size];
        exps[0] = e0;
        for (int i = 1; i < size; i++) {
            exps[i] = exps[i - 1].product(exps[i - 1], zero, add, prod);
        }
    }

    public MatrixExponentiation(Matrix e0, long zero, LongBinaryOperator add, LongBinaryOperator prod) {
        this(e0, DEFAULT_SIZE, zero, add, prod);
    }

    public MatrixExponentiation(Matrix e0, int size) {
        this.size = size;
        zero = 0;
        add = null;
        prod = null;
        exps = new Matrix[size];
        exps[0] = e0;
        for (int i = 1; i < size; i++) {
            exps[i] = exps[i - 1].product(exps[i - 1]);
        }
    }

    public MatrixExponentiation(Matrix e0) {
        this(e0, DEFAULT_SIZE);
    }

    public Matrix power(long p) {
        Matrix res = Matrix.getUnitMatrix(exps[0].getRows());
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
