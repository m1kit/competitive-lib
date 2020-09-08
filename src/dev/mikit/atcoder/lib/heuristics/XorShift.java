package dev.mikit.atcoder.lib.heuristics;

import java.util.Random;

public class XorShift extends Random {
    private int x;

    public XorShift() {
        this.x = (int) System.nanoTime();
    }

    @Override
    protected int next(int bits) {
        x = x ^ (x << 13);
        x = x ^ (x >> 17);
        x = x ^ (x << 5);
        return x;
    }
}
