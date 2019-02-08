package jp.llv.atcoder.lib.sort;

import jp.llv.atcoder.lib.util.ArrayUtil;

import java.util.Comparator;

public class QuickSort {

    private static int INSERTIONSORT_THRESHOLD = 16;

    private QuickSort() {
    }

    // median of x, y and z. precondition: x < y
    private static <T> void med(T[] a, int low, int x, int y, int z, Comparator<? super T> comparator) {
        if (comparator.compare(a[z], a[x]) < 0) {
            ArrayUtil.swap(a, low, x);
        } else if (comparator.compare(a[y], a[z]) < 0) {
            ArrayUtil.swap(a, low, y);
        } else {
            ArrayUtil.swap(a, low, z);
        }
    }

    static <T> int step(T[] a, int low, int high, Comparator<? super T> comparator) {
        int x = low + 1, y = low + (high - low) / 2, z = high - 1;
        if (comparator.compare(a[x], a[y]) < 0) {
            med(a, low, x, y, z, comparator);
        } else {
            med(a, low, y, x, z, comparator);
        }

        int lb = low + 1, ub = high;
        while (true) {
            while (comparator.compare(a[lb], a[low]) < 0) {
                lb++;
            }
            ub--;
            while (comparator.compare(a[low], a[ub]) < 0) {
                ub--;
            }
            if (lb >= ub) {
                return lb;
            }
            ArrayUtil.swap(a, lb, ub);
            lb++;
        }
    }

    static <T> void sort(T[] a, int low, int high, Comparator<? super T> comparator) {
        while (high - low > INSERTIONSORT_THRESHOLD) {
            int cut = step(a, low, high, comparator);
            sort(a, cut, high, comparator);
            high = cut;
        }
        InsertionSort.sort(a, low, high, comparator);
    }

    public <T> void sort(T[] a, Comparator<? super T> comparator) {
        sort(a, 0, a.length, comparator);
    }

    public <T extends Comparable<T>> void sort(T[] a) {
        sort(a, Comparator.naturalOrder());
    }

    // median of x, y and z. precondition: x < y
    private static void med(int[] a, int low, int x, int y, int z) {
        if (a[z] < a[x]) {
            ArrayUtil.swap(a, low, x);
        } else if (a[y] < a[z]) {
            ArrayUtil.swap(a, low, y);
        } else {
            ArrayUtil.swap(a, low, z);
        }
    }

    static int step(int[] a, int low, int high) {
        int x = low + 1, y = low + (high - low) / 2, z = high - 1;
        if (a[x] < a[y]) {
            med(a, low, x, y, z);
        } else {
            med(a, low, y, x, z);
        }

        int lb = low + 1, ub = high;
        while (true) {
            while (a[lb] < a[low]) {
                lb++;
            }
            ub--;
            while (a[low] < a[ub]) {
                ub--;
            }
            if (lb >= ub) {
                return lb;
            }
            ArrayUtil.swap(a, lb, ub);
            lb++;
        }
    }

    static void sort(int[] a, int low, int high) {
        while (high - low > INSERTIONSORT_THRESHOLD) {
            int cut = step(a, low, high);
            sort(a, cut, high);
            high = cut;
        }
        InsertionSort.sort(a, low, high);
    }

    public static void sort(int[] a) {
        sort(a, 0, a.length);
    }

    // median of x, y and z. precondition: x < y
    private static void med(long[] a, int low, int x, int y, int z) {
        if (a[z] < a[x]) {
            ArrayUtil.swap(a, low, x);
        } else if (a[y] < a[z]) {
            ArrayUtil.swap(a, low, y);
        } else {
            ArrayUtil.swap(a, low, z);
        }
    }

    static int step(long[] a, int low, int high) {
        int x = low + 1, y = low + (high - low) / 2, z = high - 1;
        if (a[x] < a[y]) {
            med(a, low, x, y, z);
        } else {
            med(a, low, y, x, z);
        }

        int lb = low + 1, ub = high;
        while (true) {
            while (a[lb] < a[low]) {
                lb++;
            }
            ub--;
            while (a[low] < a[ub]) {
                ub--;
            }
            if (lb >= ub) {
                return lb;
            }
            ArrayUtil.swap(a, lb, ub);
            lb++;
        }
    }

    public static void sort(long[] a, int low, int high) {
        while (high - low > INSERTIONSORT_THRESHOLD) {
            int cut = step(a, low, high);
            sort(a, cut, high);
            high = cut;
        }
        InsertionSort.sort(a, low, high);
    }

    public static void sort(long[] a) {
        sort(a, 0, a.length);
    }

    // median of x, y and z. precondition: x < y
    private static void med(double[] a, int low, int x, int y, int z) {
        if (a[z] < a[x]) {
            ArrayUtil.swap(a, low, x);
        } else if (a[y] < a[z]) {
            ArrayUtil.swap(a, low, y);
        } else {
            ArrayUtil.swap(a, low, z);
        }
    }

    static int step(double[] a, int low, int high) {
        int x = low + 1, y = low + (high - low) / 2, z = high - 1;
        if (a[x] < a[y]) {
            med(a, low, x, y, z);
        } else {
            med(a, low, y, x, z);
        }

        int lb = low + 1, ub = high;
        while (true) {
            while (a[lb] < a[low]) {
                lb++;
            }
            ub--;
            while (a[low] < a[ub]) {
                ub--;
            }
            if (lb >= ub) {
                return lb;
            }
            ArrayUtil.swap(a, lb, ub);
            lb++;
        }
    }

    public static void sort(double[] a, int low, int high) {
        while (high - low > INSERTIONSORT_THRESHOLD) {
            int cut = step(a, low, high);
            sort(a, cut, high);
            high = cut;
        }
        InsertionSort.sort(a, low, high);
    }

    public static void sort(double[] a) {
        sort(a, 0, a.length);
    }
}
