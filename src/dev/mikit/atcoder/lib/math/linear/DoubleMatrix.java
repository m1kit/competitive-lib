package dev.mikit.atcoder.lib.math.linear;

import dev.mikit.atcoder.lib.util.ArrayUtil;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DoubleMatrix {

    private final int n, m;
    final double[][] contents;

    public DoubleMatrix(int n, int m, double[][] contents) {
        this.n = n;
        this.m = m;
        this.contents = contents;
    }

    public DoubleMatrix(int n, int m) {
        this(n, m, new double[n][m]);
    }

    public double get(int i, int j) {
        return contents[i][j];
    }

    public void set(int i, int j, double v) {
        contents[i][j] = v;
    }

    public DoubleMatrix product(DoubleMatrix other) {
        if (this.m != other.n) {
            throw new IllegalArgumentException("size mismatch");
        }
        DoubleMatrix res = new DoubleMatrix(n, other.m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < other.m; k++) {
                    res.contents[i][k] += this.contents[i][j] * other.contents[j][k];
                }
            }
        }
        return res;
    }

    public DoubleMatrix product(DoubleMatrix other, long zero, DoubleBinaryOperator add, DoubleBinaryOperator prod) {
        if (this.m != other.n) {
            throw new IllegalArgumentException("size mismatch");
        }
        DoubleMatrix res = new DoubleMatrix(n, other.m);
        ArrayUtil.fill(res.contents, zero);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < other.m; k++) {
                    res.contents[i][k] = add.applyAsDouble(
                            res.contents[i][k],
                            prod.applyAsDouble(this.contents[i][j], other.contents[j][k])
                    );
                }
            }
        }
        return res;
    }

    public DoubleVector product(DoubleVector other) {
        if (this.m != other.getRows()) {
            throw new IllegalArgumentException("size mismatch");
        }
        DoubleVector res = new DoubleVector(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res.contents[i] += contents[i][j] * other.contents[j];
            }
        }
        return res;
    }

    public DoubleVector product(DoubleVector other, long zero, DoubleBinaryOperator add, DoubleBinaryOperator prod) {
        if (this.m != other.getRows()) {
            throw new IllegalArgumentException("size mismatch");
        }
        DoubleVector res = new DoubleVector(n);
        Arrays.fill(res.contents, zero);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res.contents[i] = add.applyAsDouble(
                        res.contents[i],
                        prod.applyAsDouble(contents[i][j], other.contents[j])
                );
            }
        }
        return res;
    }

    public int getRows() {
        return n;
    }

    public int getColumns() {
        return m;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleMatrix matrix = (DoubleMatrix) o;
        return n == matrix.n &&
                m == matrix.m &&
                Arrays.equals(contents, matrix.contents);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(n, m);
        result = 31 * result + Arrays.hashCode(contents);
        return result;
    }

    @Override
    public String toString() {
        return Stream.of(contents).map(Arrays::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    public static DoubleMatrix getUnitMatrix(int n) {
        DoubleMatrix unit = new DoubleMatrix(n, n);
        for (int i = 0; i < n; i++) {
            unit.contents[i][i] = 1L;
        }
        return unit;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
