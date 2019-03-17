package dev.mikit.atcoder.lib.sort;

import java.util.Comparator;

public class HeapSort {
    private HeapSort() {
    }

    // in heap [low, high), insert val into i
    private static <T> void heapfy(T[] a, int low, int high, int i, T val, Comparator<? super T> comparator) {
        int child = 2 * i - low + 1;
        while (child < high) {
            if (child + 1 < high && comparator.compare(a[child], a[child + 1]) < 0) {
                child++;
            }
            if (comparator.compare(val, a[child]) >= 0) {
                break;
            }
            a[i] = a[child];
            i = child;
            child = 2 * i - low + 1;
        }
        a[i] = val;
    }

    static <T> void sort(T[] a, int low, int high, Comparator<T> comparator) {
        for (int p = (high + low) / 2 - 1; p >= low; p--) {
            heapfy(a, low, high, p, a[p], comparator);
        }
        while (high > low) {
            high--;
            T pval = a[high];
            a[high] = a[low];
            heapfy(a, low, high, low, pval, comparator);
        }
    }

    public static <T> void sort(T[] a, Comparator<? super T> comparator) {
        sort(a, 0, a.length, comparator);
    }

    public static <T extends Comparable<T>> void sort(T[] a) {
        sort(a, Comparator.naturalOrder());
    }

    // in heap [low, high), insert val into i
    private static void heapfy(int[] a, int low, int high, int i, int val) {
        int child = 2 * i - low + 1;
        while (child < high) {
            if (child + 1 < high && a[child] < a[child + 1]) {
                child++;
            }
            if (val >= a[child]) {
                break;
            }
            a[i] = a[child];
            i = child;
            child = 2 * i - low + 1;
        }
        a[i] = val;
    }

    static void sort(int[] a, int low, int high) {
        for (int p = (high + low) / 2 - 1; p >= low; p--) {
            heapfy(a, low, high, p, a[p]);
        }
        while (high > low) {
            high--;
            int pval = a[high];
            a[high] = a[low];
            heapfy(a, low, high, low, pval);
        }
    }

    public static void sort(int[] a) {
        sort(a, 0, a.length);
    }

    // in heap [low, high), insert val into i
    private static void heapfy(long[] a, int low, int high, int i, long val) {
        int child = 2 * i - low + 1;
        while (child < high) {
            if (child + 1 < high && a[child] < a[child + 1]) {
                child++;
            }
            if (val >= a[child]) {
                break;
            }
            a[i] = a[child];
            i = child;
            child = 2 * i - low + 1;
        }
        a[i] = val;
    }

    static void sort(long[] a, int low, int high) {
        for (int p = (high + low) / 2 - 1; p >= low; p--) {
            heapfy(a, low, high, p, a[p]);
        }
        while (high > low) {
            high--;
            long pval = a[high];
            a[high] = a[low];
            heapfy(a, low, high, low, pval);
        }
    }

    public static void sort(long[] a) {
        sort(a, 0, a.length);
    }

    // in heap [low, high), insert val into i
    private static void heapfy(double[] a, int low, int high, int i, double val) {
        int child = 2 * i - low + 1;
        while (child < high) {
            if (child + 1 < high && a[child] < a[child + 1]) {
                child++;
            }
            if (val >= a[child]) {
                break;
            }
            a[i] = a[child];
            i = child;
            child = 2 * i - low + 1;
        }
        a[i] = val;
    }

    static void sort(double[] a, int low, int high) {
        for (int p = (high + low) / 2 - 1; p >= low; p--) {
            heapfy(a, low, high, p, a[p]);
        }
        while (high > low) {
            high--;
            double pval = a[high];
            a[high] = a[low];
            heapfy(a, low, high, low, pval);
        }
    }

    public static void sort(double[] a) {
        sort(a, 0, a.length);
    }

}
