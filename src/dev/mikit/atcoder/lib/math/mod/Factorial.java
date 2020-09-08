package dev.mikit.atcoder.lib.math.mod;

import java.util.Arrays;

public class Factorial {
    private final ModMath mod;
    private final long[] natural;
    private final long[] reverse;

    public Factorial(ModMath mod, int max) {
        this.mod = mod;
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

    public long fact(long x) {
        return natural[(int) x];
    }

    public long factinv(long x) {
        return reverse[(int) x];
    }

    public long npr(long n, long r) {
        return n < r ? 0 : mod.mod(fact(n) * factinv(n - r));
    }

    public long ncr(long n, long r) {
        return mod.mod(npr(n, r) * factinv(r));
    }

    public long nhr(long n, long r) {
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
