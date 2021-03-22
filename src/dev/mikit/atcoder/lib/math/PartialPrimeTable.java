package dev.mikit.atcoder.lib.math;

public class PartialPrimeTable {
    private final int l, r;
    private final int[] table;

    private PartialPrimeTable(int l, int r, int[] p) {
        this.l = l;
        this.r = r;
        this.table = new int[r - l];
        for (int i = l; i < r; i++) table[i - l] = i;
        for (int i = 0, j; p[i] * p[i] < r; ++i) {
            if (p[i] >= l) j = p[i] * p[i];
            else if (l % p[i] == 0) j = l;
            else j = l - (l % p[i]) + p[i];
            for (; j < r; j += p[i]) table[j - l] = 0;
        }
    }

    public PartialPrimeTable(int l, int r, PrimeTable table) {
        this(l, r, table.getPrimes().stream().mapToInt(x -> x).toArray());
        if (table.getSize() * table.getSize() < r) throw new RuntimeException("Insufficient small primes table");
    }

    public PartialPrimeTable(int l, int r) {
        this(l, r, new PrimeTable(IntMath.sqrtFloor(r) + 1));
    }

    public boolean isPrime(int x) {
        return table[x - l] != 0;
    }
}
