package dev.mikit.atcoder.lib.sort;

import dev.mikit.atcoder.lib.util.function.IntComparator;

import java.util.Comparator;

public class BinaryInsertionSort {

    private BinaryInsertionSort() {
    }

    static <T> void sort(T[] a, int low, int high, int start, Comparator<? super T> comparator) {
        if (start == low) {
            start++;
        }
        for (int p = start; p < high; p++) {
            T pivot = a[p];
            int min = low, max = p;
            while (min < max) {
                int mid = (min + max) / 2;
                if (comparator.compare(pivot, a[mid]) < 0) {
                    max = mid;
                } else {
                    min = mid + 1;
                }
            }
            int n = p - min;
            switch (n) {
                case 2:
                    a[min + 2] = a[min + 1];
                case 1:
                    a[min + 1] = a[min];
                    break;
                default:
                    System.arraycopy(a, min, a, min + 1, n);
            }
            a[min] = pivot;
        }
    }

    static <T extends Comparable<T>> void sort(T[] a, int low, int high, int start) {
        sort(a, low, high, start, Comparator.naturalOrder());
    }

    public static <T> void sort(T[] a, Comparator<? super T> comparator) {
        sort(a, 0, a.length, 1, comparator);
    }

    public static <T extends Comparable<T>> void sort(T[] a) {
        sort(a, Comparator.naturalOrder());
    }

    static void sort(int[] a, int low, int high, int start) {
        if (start == low) {
            start++;
        }
        for (int p = start; p < high; p++) {
            int pivot = a[p];
            int min = low, max = p;
            while (min < max) {
                int mid = (min + max) / 2;
                if (pivot < a[mid]) {
                    max = mid;
                } else {
                    min = mid + 1;
                }
            }
            int n = p - min;
            switch (n) {
                case 2:
                    a[min + 2] = a[min + 1];
                case 1:
                    a[min + 1] = a[min];
                    break;
                default:
                    System.arraycopy(a, min, a, min + 1, n);
            }
            a[min] = pivot;
        }
    }

    public static void sort(int[] a) {
        sort(a, 0, a.length, 1);
    }

    static void sort(long[] a, int low, int high, int start) {
        if (start == low) {
            start++;
        }
        for (int p = start; p < high; p++) {
            long pivot = a[p];
            int min = low, max = p;
            while (min < max) {
                int mid = (min + max) / 2;
                if (pivot < a[mid]) {
                    max = mid;
                } else {
                    min = mid + 1;
                }
            }
            int n = p - min;
            switch (n) {
                case 2:
                    a[min + 2] = a[min + 1];
                case 1:
                    a[min + 1] = a[min];
                    break;
                default:
                    System.arraycopy(a, min, a, min + 1, n);
            }
            a[min] = pivot;
        }
    }

    public static void sort(long[] a) {
        sort(a, 0, a.length, 1);
    }

    static void sort(double[] a, int low, int high, int start) {
        if (start == low) {
            start++;
        }
        for (int p = start; p < high; p++) {
            double pivot = a[p];
            int min = low, max = p;
            while (min < max) {
                int mid = (min + max) / 2;
                if (pivot < a[mid]) {
                    max = mid;
                } else {
                    min = mid + 1;
                }
            }
            int n = p - min;
            switch (n) {
                case 2:
                    a[min + 2] = a[min + 1];
                case 1:
                    a[min + 1] = a[min];
                    break;
                default:
                    System.arraycopy(a, min, a, min + 1, n);
            }
            a[min] = pivot;
        }
    }

    public static void sort(double[] a) {
        sort(a, 0, a.length, 1);
    }
}
