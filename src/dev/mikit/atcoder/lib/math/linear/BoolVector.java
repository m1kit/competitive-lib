package dev.mikit.atcoder.lib.math.linear;

import java.util.Arrays;
import java.util.BitSet;

public class BoolVector {

    final int n;
    final BitSet contents;

    public BoolVector(BitSet contents) {
        this.contents = contents;
        this.n = contents.size();
    }

    public BoolVector(int n) {
        this.contents = new BitSet(n);
        this.n = n;
    }

    public boolean get(int i) {
        return contents.get(i);
    }

    public void set(int i, boolean v) {
        contents.set(i, v);
    }

    public int getRows() {
        return n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolVector vector = (BoolVector) o;
        return contents.equals(vector.contents);
    }

    @Override
    public int hashCode() {
        return contents.hashCode();
    }
}
