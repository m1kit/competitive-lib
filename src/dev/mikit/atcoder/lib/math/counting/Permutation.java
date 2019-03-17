package dev.mikit.atcoder.lib.math.counting;

import dev.mikit.atcoder.lib.util.ArrayUtil;

import java.util.stream.IntStream;

public final class Permutation {

    private int[] next;

    public Permutation(int n) {
        next = IntStream.range(0, n).toArray();
    }

    public boolean hasNext() {
        return next != null;
    }

    public int[] next() {
        int[] r = next;
        next = nextPermutation(next);
        return r;
    }

    public static char[] nextPermutation(char[] a) {
        if (a == null || a.length < 2) {
            return null;
        }
        int p = 0;
        for (int i = a.length - 2; i >= 0; i--) {
            if (a[i] < a[i + 1]) {
                p = i;
                break;
            }
        }
        int q = 0;
        for (int i = a.length - 1; i > p; i--) {
            if (a[i] > a[p]) {
                q = i;
                break;
            }
        }
        if (p == 0 && q == 0) {
            return null;
        }
        char temp = a[p];
        a[p] = a[q];
        a[q] = temp;
        ArrayUtil.reverse(a, p + 1, a.length);
        return a;
    }

    public static int[] nextPermutation(int[] a) {
        if (a == null || a.length < 2) {
            return null;
        }
        int p = 0;
        for (int i = a.length - 2; i >= 0; i--) {
            if (a[i] < a[i + 1]) {
                p = i;
                break;
            }
        }
        int q = 0;
        for (int i = a.length - 1; i > p; i--) {
            if (a[i] > a[p]) {
                q = i;
                break;
            }
        }
        if (p == 0 && q == 0) {
            return null;
        }
        int temp = a[p];
        a[p] = a[q];
        a[q] = temp;
        ArrayUtil.reverse(a, p + 1, a.length);
        return a;
    }

    public static long[] nextPermutation(long[] a) {
        if (a == null || a.length < 2) {
            return null;
        }
        int p = 0;
        for (int i = a.length - 2; i >= 0; i--) {
            if (a[i] < a[i + 1]) {
                p = i;
                break;
            }
        }
        int q = 0;
        for (int i = a.length - 1; i > p; i--) {
            if (a[i] > a[p]) {
                q = i;
                break;
            }
        }
        if (p == 0 && q == 0) {
            return null;
        }
        long temp = a[p];
        a[p] = a[q];
        a[q] = temp;
        ArrayUtil.reverse(a, p + 1, a.length);
        return a;
    }

    public static double[] nextPermutation(double[] a) {
        if (a == null || a.length < 2) {
            return null;
        }
        int p = 0;
        for (int i = a.length - 2; i >= 0; i--) {
            if (a[i] < a[i + 1]) {
                p = i;
                break;
            }
        }
        int q = 0;
        for (int i = a.length - 1; i > p; i--) {
            if (a[i] > a[p]) {
                q = i;
                break;
            }
        }
        if (p == 0 && q == 0) {
            return null;
        }
        double temp = a[p];
        a[p] = a[q];
        a[q] = temp;
        ArrayUtil.reverse(a, p + 1, a.length);
        return a;
    }

    public static <T extends Comparable<T>> T[] nextPermutation(T[] a) {
        if (a == null || a.length < 2) {
            return null;
        }
        int p = 0;
        for (int i = a.length - 2; i >= 0; i--) {
            if (a[i].compareTo(a[i + 1]) < 0) {
                p = i;
                break;
            }
        }
        int q = 0;
        for (int i = a.length - 1; i > p; i--) {
            if (a[i].compareTo(a[p]) > 0) {
                q = i;
                break;
            }
        }
        if (p == 0 && q == 0) {
            return null;
        }
        T temp = a[p];
        a[p] = a[q];
        a[q] = temp;
        ArrayUtil.reverse(a, p + 1, a.length);
        return a;
    }
}