package dev.mikit.atcoder.lib.structure.waveletmatrix;

import java.util.Arrays;

public class WaveletMatrix {
    private final int n, bits;
    private final int[] count = new int[64];
    private final BitVector[] vectors;

    public WaveletMatrix(long[] a) {
        this.n = a.length;
        int bits = 0;
        for (int i = 0; i < n; i++) {
            bits = Math.max(bits, 64 - Long.numberOfLeadingZeros(a[i]));
        }
        this.bits = bits;
        this.vectors = new BitVector[bits];
        long[] b = new long[n];
        for (int i = bits - 1; i >= 0; i--) {
            vectors[i] = new BitVector(n);
            long bit = 1L << i;
            int aLen = 0, bLen = 0;
            for (int j = 0; j < n; j++) {
                if ((a[j] & bit) == 0) {
                    a[aLen++] = a[j];
                    count[i]++;
                } else {
                    b[bLen++] = a[j];
                    vectors[i].set(j);
                }
            }
            System.arraycopy(b, 0, a, aLen, bLen);
        }
    }

    public long access(int i) {
        if (i < 0 || n <= i) throw new IndexOutOfBoundsException();
        long res = 0;
        for (int j = bits - 1; j >= 0; j--) {
            if (vectors[j].get(i)) {
                res |= 1L << j;
                i = count[j] + vectors[j].rank(i);
            } else {
                i -= vectors[j].rank(i);
            }
        }
        return res;
    }

    public int rank(int l, int r, long v) {
        if (l < 0 || n < r || l > r) throw new IndexOutOfBoundsException();
        for (int i = bits - 1; i >= 0; i--) {
            if (((v >> i) & 1) == 1) {
                l = vectors[i].rank(l) + count[i];
                r = vectors[i].rank(r) + count[i];
            } else {
                l -= vectors[i].rank(l);
                r -= vectors[i].rank(r);
            }
        }
        return r - l;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = bits - 1; i >= 0; i--) {
            sb.append(vectors[i].toString());
            sb.append('\n');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
