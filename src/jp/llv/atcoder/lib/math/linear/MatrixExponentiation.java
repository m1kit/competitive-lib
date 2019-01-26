package jp.llv.atcoder.lib.math.linear;

import java.util.function.LongBinaryOperator;

public class MatrixExponentiation {

    private static final int SIZE = 64;
    private final long zero;
    private final LongBinaryOperator add, prod;
    private final Matrix[] exps;

    public MatrixExponentiation(Matrix e0, long zero, LongBinaryOperator add, LongBinaryOperator prod) {
        this.zero = zero;
        this.add = add;
        this.prod = prod;
        exps = new Matrix[SIZE];
        exps[0] = e0;
        for (int i = 1; i < SIZE; i++) {
            exps[i] = exps[i - 1].product(exps[i - 1], zero, add, prod);
        }
    }

    public MatrixExponentiation(Matrix e0) {
        zero = 0;
        add = null;
        prod = null;
        exps = new Matrix[SIZE];
        exps[0] = e0;
        for (int i = 1; i < SIZE; i++) {
            exps[i] = exps[i - 1].product(exps[i - 1]);
        }
    }

    public Matrix power(long p) {
        Matrix res = Matrix.getUnitMatrix(exps[0].getRows());
        for (int i = 0; i < SIZE; i++) {
            if (((1 << i) & p) == 0) {
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
