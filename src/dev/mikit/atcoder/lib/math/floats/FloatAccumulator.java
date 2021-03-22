package dev.mikit.atcoder.lib.math.floats;

/**
 * An implementation of Kahan summation algorithm.
 */
public class FloatAccumulator {

    private double s, c;

    public FloatAccumulator() {
    }

    public FloatAccumulator(double s) {
        this.s = s;
    }

    public void add(double x) {
        double y1 = x - c, t1 = s + y1;
        c = (t1 - s) - y1;
        s = t1;
    }

    public double get() {
        return s;
    }
}
