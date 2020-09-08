package dev.mikit.atcoder.lib.structure.waveletmatrix;

import java.util.Arrays;

public class BitVector {

    private static int LOG_WORD_SIZE = 6;
    public static int WORD_SIZE = 1 << LOG_WORD_SIZE;
    public static int WORD_SIZE_MASK = WORD_SIZE - 1;

    private boolean built = false;
    private final int n;
    private final long[] vector;
    private final int[] rank;

    public BitVector(int n) {
        this.n = n;
        int len = (n + WORD_SIZE - 1) >> LOG_WORD_SIZE;
        this.vector = new long[len];
        this.rank = new int[len];
    }

    public void set(int i) {
        built = false;
        vector[i >> LOG_WORD_SIZE] |= (1L << (i & WORD_SIZE_MASK));
    }

    public boolean get(int i) {
        return ((vector[i >> LOG_WORD_SIZE] >> (i & WORD_SIZE_MASK)) & 1) == 1;
    }

    public void clear(int i) {
        built = false;
        vector[i >> LOG_WORD_SIZE] &= ~(1L << (i & WORD_SIZE_MASK));
    }

    private void build() {
        if (built) return;
        for (int i = 1; i < vector.length; i++) rank[i] = rank[i - 1] + Long.bitCount(vector[i - 1]);
    }

    public int rank(int i) {
        build();
        int base = i >> LOG_WORD_SIZE, offset = i & WORD_SIZE_MASK;
        return rank[base] + Long.bitCount(vector[base] & ((1L << offset) - 1));
    }

    public int select(int i) {
        build();
        int min = 0, max = vector.length;
        while (max - min > 1) {
            int mid = (min + max) >> 1;
            if (i <= rank[mid]) max = mid;
            else min = mid;
        }
        i -= rank[min];
        int offset = 0;
        for (int w = WORD_SIZE >> 1; w > 0; w >>= 1) {
            long mask = ((1L << w) - 1) << offset;
            int cnt = Long.bitCount(vector[min] & mask);
            if (cnt < i) {
                i -= cnt;
                offset += w;
            }
        }
        return min * WORD_SIZE + offset + 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = vector.length - 1; i >= 0; i--) {
            sb.append(String.format("%64s", Long.toBinaryString(vector[i])).replace(' ', '0'));
        }
        sb.reverse();
        sb.setLength(n);
        return sb.toString();
    }
}
