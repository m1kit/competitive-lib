package dev.mikit.atcoder.lib.math.linear;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoolMatrix {

    private final int n, m;
    final BitSet[] mat;

    public BoolMatrix(int n, int m, BitSet[] contents) {
        this.n = n;
        this.m = m;
        this.mat = contents;
    }

    public BoolMatrix(int n, int m) {
        this(n, m, new BitSet[n]);
        for (int i = 0; i < n; i++) this.mat[i] = new BitSet(m);
    }

    public boolean get(int i, int j) {
        return mat[i].get(j);
    }

    public BitSet get(int i) {
        return mat[i];
    }

    public void set(int i, int j, boolean v) {
        mat[i].set(j, v);
    }

    public BoolMatrix product(BoolMatrix other) {
        if (this.m != other.n) {
            throw new IllegalArgumentException("size mismatch");
        }
        BoolMatrix res = new BoolMatrix(n, other.m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < other.m; k++) {
                    if (this.mat[i].get(j) && other.mat[j].get(k)) {
                        res.mat[i].flip(k);
                    }
                }
            }
        }
        return res;
    }

    public BoolVector product(BoolVector other) {
        if (this.m != other.getRows()) {
            throw new IllegalArgumentException("size mismatch");
        }
        BoolVector res = new BoolVector(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (mat[i].get(j) && other.contents.get(j)) {
                    res.contents.flip(i);
                }
            }
        }
        return res;
    }

    public int normalize() {
        int rank = 0;
        for (int col = 0; col < m; col++) {
            int pivot = -1;
            for (int row = rank; row < n; row++) {
                if (mat[row].get(col)) {
                    pivot = row;
                }
            }
            if (pivot == -1) continue;
            swap(pivot, rank);

            for (int row = 0; row < n; row++) {
                if (row != rank && mat[row].get(col)) {
                    mat[row].xor(mat[rank]);
                }
            }
            rank++;
        }
        return rank;
    }

    public BoolMatrix inv() {
        if (n != m) throw new RuntimeException();
        BoolMatrix res = getUnitMatrix(n);
        for (int i = 0; i < n; i++) {
            int pivot = -1;
            for (int j = i; j < n; j++) {
                if (get(j, i)) {
                    pivot = j;
                    break;
                }
            }
            if (pivot == -1) return null;
            if (pivot != i) {
                swap(i, pivot);
                res.swap(i, pivot);
            }
            for (int j = 0; j < n; j++) {
                if (i == j || !get(j, i)) continue;
                mat[j].xor(mat[i]);
                res.mat[j].xor(res.mat[i]);
            }
        }
        return res;
    }

    private void swap(int i, int j) {
        BitSet t = mat[i];
        mat[i] = mat[j];
        mat[j] = t;
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
        BoolMatrix matrix = (BoolMatrix) o;
        return n == matrix.n &&
                m == matrix.m &&
                Arrays.equals(mat, matrix.mat);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(n, m);
        result = 31 * result + Arrays.hashCode(mat);
        return result;
    }

    @Override
    public String toString() {
        return Stream.of(mat).map(Object::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    public static BoolMatrix getUnitMatrix(int n) {
        BoolMatrix unit = new BoolMatrix(n, n);
        for (int i = 0; i < n; i++) {
            unit.mat[i].set(i);
        }
        return unit;
    }
}
