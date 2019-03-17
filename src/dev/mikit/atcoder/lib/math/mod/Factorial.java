package dev.mikit.atcoder.lib.math.mod;

import java.util.Arrays;

public class Factorial {
    private final ModMath mod;
    private final int max;
    private final long[] natural;
    private final long[] reverse;

    public Factorial(ModMath mod, int max) {
        this.mod = mod;
        this.max = max;
        this.natural = new long[max];
        this.reverse = new long[max];
        natural[0] = 1;
        for (int i = 1; i < max; i++) {
            natural[i] = mod.mod(natural[i - 1] * i);
        }
        reverse[max - 1] = mod.inv(natural[max - 1]);
        for (int i = max - 1; i > 0; i--) {
            reverse[i - 1] = mod.mod(reverse[i] * i);
        }
    }

    public long fact(int x) {
        return natural[x];
    }

    public long factinv(int x) {
        return reverse[x];
    }

    public long npr(int n, int r) {
        return n < r ? 0 : mod.mod(natural[n] * reverse[n - r]);
    }

    public long ncr(int n, int r) {
        return mod.mod(npr(n, r) * reverse[r]);
    }

    public long nhr(int n, int r) {
        return (n | r) == 0 ? 1 : ncr(n + r - 1, r);
    }

    public ModMath getMod() {
        return mod;
    }

    @Override
    public String toString() {
        return "Factorial{" +
                "natural=" + Arrays.toString(natural) +
                ", reverse=" + Arrays.toString(reverse) +
                '}';
    }
}
