package dev.mikit.atcoder.lib.sort;

import java.util.Comparator;

public class MergeSort {

    private static final int INSERTIONSORT_THRESHOLD = 7;

    private MergeSort() {
    }

    static <T> void sort(T[] src, T[] dest, int low, int high, int off, Comparator<? super T> comparator) {
        int length = high - low;
        if (length < INSERTIONSORT_THRESHOLD) {
            InsertionSort.sort(dest, low, high, comparator);
            return;
        }
        int destLow = low;
        int destHigh = high;
        low += off;
        high += off;
        int mid = low + (high - low) / 2;
        sort(dest, src, low, mid, -off, comparator);
        sort(dest, src, mid, high, -off, comparator);
        if (comparator.compare(src[mid - 1], src[mid]) <= 0) {
            System.arraycopy(src, low, dest, destLow, length);
            return;
        }
        for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
            if (q >= high || p < mid && comparator.compare(src[p], src[q]) <= 0) {
                dest[i] = src[p++];
            } else {
                dest[i] = src[q++];
            }
        }
    }

    public static <T> void sort(T[] a, Comparator<? super T> comparator) {
        sort(a.clone(), a,0, a.length, 0, comparator);
    }

    public static <T extends Comparable<T>> void sort(T[] a) {
        sort(a, Comparator.naturalOrder());
    }

    static void sort(int[] src, int[] dest, int low, int high, int off) {
        int length = high - low;
        if (length < INSERTIONSORT_THRESHOLD) {
            InsertionSort.sort(dest, low, high);
            return;
        }
        int destLow = low;
        int destHigh = high;
        low += off;
        high += off;
        int mid = low + (high - low) / 2;
        sort(dest, src, low, mid, -off);
        sort(dest, src, mid, high, -off);
        if (src[mid - 1] <= src[mid]) {
            System.arraycopy(src, low, dest, destLow, length);
            return;
        }
        for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
            if (q >= high || p < mid && src[p] <= src[q]) {
                dest[i] = src[p++];
            } else {
                dest[i] = src[q++];
            }
        }
    }

    public static void sort(int[] a) {
        sort(a.clone(), a, 0, a.length, 0);
    }

    static void sort(long[] src, long[] dest, int low, int high, int off) {
        int length = high - low;
        if (length < INSERTIONSORT_THRESHOLD) {
            InsertionSort.sort(dest, low, high);
            return;
        }
        int destLow = low;
        int destHigh = high;
        low += off;
        high += off;
        int mid = low + (high - low) / 2;
        sort(dest, src, low, mid, -off);
        sort(dest, src, mid, high, -off);
        if (src[mid - 1] <= src[mid]) {
            System.arraycopy(src, low, dest, destLow, length);
            return;
        }
        for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
            if (q >= high || p < mid && src[p] <= src[q]) {
                dest[i] = src[p++];
            } else {
                dest[i] = src[q++];
            }
        }
    }

    public static void sort(long[] a) {
        sort(a.clone(), a, 0, a.length, 0);
    }

    static void sort(double[] src, double[] dest, int low, int high, int off) {
        int length = high - low;
        if (length < INSERTIONSORT_THRESHOLD) {
            InsertionSort.sort(dest, low, high);
            return;
        }
        int destLow = low;
        int destHigh = high;
        low += off;
        high += off;
        int mid = low + (high - low) / 2;
        sort(dest, src, low, mid, -off);
        sort(dest, src, mid, high, -off);
        if (src[mid - 1] <= src[mid]) {
            System.arraycopy(src, low, dest, destLow, length);
            return;
        }
        for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
            if (q >= high || p < mid && src[p] <= src[q]) {
                dest[i] = src[p++];
            } else {
                dest[i] = src[q++];
            }
        }
    }

    public static void sort(double[] a) {
        sort(a.clone(), a, 0, a.length, 0);
    }

}
