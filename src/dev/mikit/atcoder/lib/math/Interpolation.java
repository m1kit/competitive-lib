package dev.mikit.atcoder.lib.math;

import dev.mikit.atcoder.lib.math.mod.ModMath;

public class Interpolation {

    private final int n;
    private final int[] c;
    private final ModMath mod;

    public Interpolation(int[] x, int[] y, ModMath mod) {
        if (x.length != y.length) throw new RuntimeException();
        this.n = x.length - 1;
        this.c = new int[n + 1];
        this.mod = mod;
        final int m = (int) mod.getModulo();
        for (int i = 0; i <= n; i++) {
            long t = 1;
            for (int j = 0; j <= n; j++) {
                if (i != j) {
                    t *= x[i] - x[j];
                    t %= m;
                }
            }
            y[i] = (int) mod.div(y[i], t);
        }
    }
}
