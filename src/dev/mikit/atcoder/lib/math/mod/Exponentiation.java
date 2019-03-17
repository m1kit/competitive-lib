package dev.mikit.atcoder.lib.math.mod;

import java.util.Arrays;

public class Exponentiation {
    private final ModMath mod;
    private final long base;
    private final int max;
    private final long[] natural;
    private final long[] reverse;

    public Exponentiation(ModMath mod, long base, int max) {
        this.mod = mod;
        this.base = base;
        this.max = max;
        this.natural = new long[max];
        this.reverse = new long[max];
        natural[0] = 1;
        for (int i = 1; i < max; i++) {
            natural[i] = mod.mod(natural[i - 1] * base);
        }
        reverse[max - 1] = mod.inv(natural[max - 1]);
        for (int i = max - 1; i > 0; i--) {
            reverse[i - 1] = mod.mod(reverse[i] * base);
        }
    }

    public long pow(int x) {
        if (x >= 0) {
            return natural[x];
        } else {
            return reverse[-x];
        }
    }

    public ModMath getMod() {
        return mod;
    }

    @Override
    public String toString() {
        return "Exponentiation{" +
                "natural=" + Arrays.toString(natural) +
                ", reverse=" + Arrays.toString(reverse) +
                '}';
    }
}
