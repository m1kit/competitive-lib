package jp.llv.atcoder.lib.sort;

import java.util.Comparator;

public class InsertionSort {
    private InsertionSort() {
    }

    static <T> void sort(T[] a, int low, int high, Comparator<? super T> comparator) {
        for (int i = low; i < high; i++) {
            for (int j = i; j > low && comparator.compare(a[j - 1], a[j]) > 0; j--) {
                T t = a[j];
                a[j] = a[j - 1];
                a[j - 1] = t;
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
                int t = a[j];
                a[j] = a[j - 1];
                a[j - 1] = t;
            }
        }
    }

    public static void sort(int[] a) {
        sort(a, 0, a.length);
    }

    static void sort(long[] a, int low, int high) {
        for (int i = low; i < high; i++) {
            for (int j = i; j > low && a[j - 1] > a[j]; j--) {
                long t = a[j];
                a[j] = a[j - 1];
                a[j - 1] = t;
            }
        }
    }

    public static void sort(long[] a) {
        sort(a, 0, a.length);
    }

    static void sort(double[] a, int low, int high) {
        for (int i = low; i < high; i++) {
            for (int j = i; j > low && a[j - 1] > a[j]; j--) {
                double t = a[j];
                a[j] = a[j - 1];
                a[j - 1] = t;
            }
        }
    }

    public static void sort(double[] a) {
        sort(a, 0, a.length);
    }

}
