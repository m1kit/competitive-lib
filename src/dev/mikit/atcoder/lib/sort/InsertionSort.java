package dev.mikit.atcoder.lib.sort;

import dev.mikit.atcoder.lib.util.ArrayUtil;

import java.util.Comparator;

public class InsertionSort {
    private InsertionSort() {
    }

    static <T> void sort(T[] a, int low, int high, Comparator<? super T> comparator) {
        for (int i = low; i < high; i++) {
            for (int j = i; j > low && comparator.compare(a[j - 1], a[j]) > 0; j--) {
                ArrayUtil.swap(a, j - 1, j);
            }
        }
    }

    public static <T> void sort(T[] a, Comparator<? super T> comparator) {
        sort(a, 0, a.length, comparator);
    }

    public static <T extends Comparable<T>> void sort(T[] a) {
        sort(a, 0, a.length, Comparator.naturalOrder());
    }

    static void sort(int[] a, int low, int high) {
        for (int i = low; i < high; i++) {
            for (int j = i; j > low && a[j - 1] > a[j]; j--) {
                ArrayUtil.swap(a, j - 1, j);
            }
        }
    }

    public static void sort(int[] a) {
        sort(a, 0, a.length);
    }

    static void sort(long[] a, int low, int high) {
        for (int i = low; i < high; i++) {
            for (int j = i; j > low && a[j - 1] > a[j]; j--) {
                ArrayUtil.swap(a, j - 1, j);
            }
        }
    }

    public static void sort(long[] a) {
        sort(a, 0, a.length);
    }

    static void sort(double[] a, int low, int high) {
        for (int i = low; i < high; i++) {
            for (int j = i; j > low && a[j - 1] > a[j]; j--) {
                ArrayUtil.swap(a, j - 1, j);
            }
        }
    }

    public static void sort(double[] a) {
        sort(a, 0, a.length);
    }

}
