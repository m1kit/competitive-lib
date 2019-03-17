package dev.mikit.atcoder.lib.math.counting;

public class Combination {

    private long next;
    private final long invalidRange;

    public Combination(int n, int k) {
        if (k <= 0 || n < 0 || 62 < n || n < k) {
            throw new IllegalArgumentException();
        }
        next = (1L << k) - 1;
        invalidRange = -(1L << n);
    }

    public boolean hasNext() {
        return (next & invalidRange) == 0;
    }

    public long next() {
        long r = next;
        long lsb = next & -next;
        long part = next + lsb;
        next = part | ((((part & -part) / lsb) >>> 1) - 1);
        return r;
    }

}
