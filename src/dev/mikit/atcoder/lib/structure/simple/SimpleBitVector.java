package dev.mikit.atcoder.lib.structure.simple;

import dev.mikit.atcoder.lib.math.BitMath;

import java.util.Arrays;
import java.util.BitSet;
import java.util.NoSuchElementException;

public class SimpleBitVector {
    private static final int BLOCK_SIZE = 64;
    private static final int CHUNK_SIZE = 512;
    private static final int BLOCKS = CHUNK_SIZE / BLOCK_SIZE;

    private final int n, chunks;
    private final long[] data;
    private final int[] chunk;
    private final short[][] block = new short[BLOCKS][];

    public SimpleBitVector(int n, long[] data) {
        this.n = n;
        this.chunks = (n + CHUNK_SIZE - 1) / CHUNK_SIZE;
        this.data = Arrays.copyOf(data, chunks * BLOCKS);
        this.chunk = new int[chunks + 1];
        for (int i = 0; i < BLOCKS; i++) block[i] = new short[chunks];

        for (int i = 0; i < chunks - 1; i++) {
            for (int j = 0; j < BLOCKS - 1; j++) {
                block[j + 1][i] = (short) (block[j][i] + BitMath.count(data[i * BLOCKS + j]));
            }
            chunk[i + 1] = chunk[i] + block[BLOCKS - 1][i] + BitMath.count(data[i * BLOCKS + BLOCKS - 1]);
        }
    }

    public SimpleBitVector(BitSet data) {
        this(data.size(), data.toLongArray());
    }

    public boolean get(int i) {
        return ((data[i / BLOCK_SIZE] >> (i % BLOCK_SIZE)) & 1) == 1;
    }

    public int rank(int r) {
        int c = r / CHUNK_SIZE, b = r % CHUNK_SIZE / BLOCK_SIZE, s = r % BLOCK_SIZE;
        long mask = (1L << s) - 1;
        return chunk[c] + block[b][c] + BitMath.count(data[r / BLOCK_SIZE] & mask);
    }

    public int rank(int l, int r) {
        return l == 0 ? rank(r) : rank(r) - rank(l - 1);
    }

    public int select(int offset, int num) {
        if (num == 0) return offset;
        if (rank(offset, n) < num) throw new NoSuchElementException();
        int max = n, min = offset;
        while (max - min > 1) {
            int mid = (min + max) / 2;
            if (rank(offset, mid) >= num) max = mid;
            else min = mid;
        }
        return min;
    }

    public int size() {
        return n;
    }

}
