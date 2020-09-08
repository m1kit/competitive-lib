package dev.mikit.atcoder.lib.math.linear;

import java.util.Arrays;

public class IntVector {

    final long[] contents;

    public IntVector(long... contents) {
        this.contents = contents;
    }

    public IntVector(int n) {
        this(new long[n]);
    }

    public long get(int i) {
        return contents[i];
    }

    public void set(int i, long v) {
        contents[i] = v;
    }

    public int getRows() {
        return contents.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntVector vector = (IntVector) o;
        return Arrays.equals(contents, vector.contents);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(contents);
    }

    @Override
    public String toString() {
        return Arrays.toString(contents);
    }
}
