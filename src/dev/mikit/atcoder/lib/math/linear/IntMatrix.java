package dev.mikit.atcoder.lib.math.linear;

import dev.mikit.atcoder.lib.util.ArrayUtil;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntMatrix {

    private final int n, m;
    final long[][] contents;

    public IntMatrix(int n, int m, long[][] contents) {
        this.n = n;
        this.m = m;
        this.contents = contents;
    }

    public IntMatrix(int n, int m) {
        this(n, m, new long[n][m]);
    }

    public long get(int i, int j) {
        return contents[i][j];
    }

    public void set(int i, int j, long v) {
        contents[i][j] = v;
    }

    public IntMatrix product(IntMatrix other) {
        if (this.m != other.n) {
            throw new IllegalArgumentException("size mismatch");
        }
        IntMatrix res = new IntMatrix(n, other.m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < other.m; k++) {
                    res.contents[i][k] += this.contents[i][j] * other.contents[j][k];
                }
            }
        }
        return res;
    }

    public IntMatrix product(IntMatrix other, long zero, LongBinaryOperator add, LongBinaryOperator prod) {
        if (this.m != other.n) {
            throw new IllegalArgumentException("size mismatch");
        }
        IntMatrix res = new IntMatrix(n, other.m);
        ArrayUtil.fill(res.contents, zero);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < other.m; k++) {
                    res.contents[i][k] = add.applyAsLong(
                            res.contents[i][k],
                            prod.applyAsLong(this.contents[i][j], other.contents[j][k])
                    );
                }
            }
        }
        return res;
    }

    public IntVector product(IntVector other) {
        if (this.m != other.getRows()) {
            throw new IllegalArgumentException("size mismatch");
        }
        IntVector res = new IntVector(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res.contents[i] += contents[i][j] * other.contents[j];
            }
        }
        return res;
    }

    public IntVector product(IntVector other, long zero, LongBinaryOperator add, LongBinaryOperator prod) {
        if (this.m != other.getRows()) {
            throw new IllegalArgumentException("size mismatch");
        }
        IntVector res = new IntVector(n);
        Arrays.fill(res.contents, zero);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res.contents[i] = add.applyAsLong(
                        res.contents[i],
                        prod.applyAsLong(contents[i][j], other.contents[j])
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
        IntMatrix matrix = (IntMatrix) o;
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

    public static IntMatrix getUnitMatrix(int n, long zero, long one) {
        IntMatrix unit = new IntMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                unit.set(i, j, i == j ? one : zero);
            }
        }
        return unit;
    }
}
