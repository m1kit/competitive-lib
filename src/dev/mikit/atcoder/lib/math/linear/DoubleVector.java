package dev.mikit.atcoder.lib.math.linear;

import java.util.Arrays;

public class DoubleVector {

    final double[] contents;

    public DoubleVector(double... contents) {
        this.contents = contents;
    }

    public DoubleVector(int n) {
        this(new double[n]);
    }

    public double get(int i) {
        return contents[i];
    }

    public void set(int i, double v) {
        contents[i] = v;
    }

    public int getRows() {
        return contents.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleVector vector = (DoubleVector) o;
        return Arrays.equals(contents, vector.contents);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(contents);
    }
}
