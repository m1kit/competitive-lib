package dev.mikit.atcoder.lib.sort;

import dev.mikit.atcoder.lib.math.BitMath;
import dev.mikit.atcoder.lib.meta.Verified;

import java.util.Comparator;

public class IntroSort {

    private static int INSERTIONSORT_THRESHOLD = 16;

    private IntroSort() {
    }

    static <T> void sort(T[] a, int low, int high, int maxDepth, Comparator<T> comparator) {
        while (high - low > INSERTIONSORT_THRESHOLD) {
            if (maxDepth-- == 0) {
                HeapSort.sort(a, low, high, comparator);
                return;
            }
            int cut = QuickSort.step(a, low, high, comparator);
            sort(a, cut, high, maxDepth, comparator);
            high = cut;
        }
        InsertionSort.sort(a, low, high, comparator);
    }

    @Verified({
            "https://atcoder.jp/contests/abc116/submissions/4199065",
            "https://codeforces.com/contest/1114/submission/49740827"
    })
    public static <T> void sort(T[] a, Comparator<T> comparator) {
        if (a.length <= INSERTIONSORT_THRESHOLD) {
            InsertionSort.sort(a, 0, a.length, comparator);
        } else {
            sort(a, 0, a.length, 2 * BitMath.msb(a.length), comparator);
        }
    }

    public static <T extends Comparable<T>> void sort(T[] a) {
        sort(a, Comparator.naturalOrder());
    }

    static void sort(int[] a, int low, int high, int maxDepth) {
        while (high - low > INSERTIONSORT_THRESHOLD) {
            if (maxDepth-- == 0) {
                HeapSort.sort(a, low, high);
                return;
            }
            int cut = QuickSort.step(a, low, high);
            sort(a, cut, high, maxDepth);
            high = cut;
        }
        InsertionSort.sort(a, low, high);
    }

    public static void sort(int[] a) {
        if (a.length <= INSERTIONSORT_THRESHOLD) {
            InsertionSort.sort(a, 0, a.length);
        } else {
            sort(a, 0, a.length, 2 * BitMath.msb(a.length));
        }
    }

    static void sort(long[] a, int low, int high, int maxDepth) {
        while (high - low > INSERTIONSORT_THRESHOLD) {
            if (maxDepth-- == 0) {
                HeapSort.sort(a, low, high);
                return;
            }
            int cut = QuickSort.step(a, low, high);
            sort(a, cut, high, maxDepth);
            high = cut;
        }
        InsertionSort.sort(a, low, high);
    }

    @Verified("https://codeforces.com/contest/1110/submission/49640634")
    public static void sort(long[] a) {
        if (a.length <= INSERTIONSORT_THRESHOLD) {
            InsertionSort.sort(a, 0, a.length);
        } else {
            sort(a, 0, a.length, 2 * BitMath.msb(a.length));
        }
    }

    static void sort(double[] a, int low, int high, int maxDepth) {
        while (high - low > INSERTIONSORT_THRESHOLD) {
            if (maxDepth-- == 0) {
                HeapSort.sort(a, low, high);
                return;
            }
            int cut = QuickSort.step(a, low, high);
            sort(a, cut, high, maxDepth);
            high = cut;
        }
        InsertionSort.sort(a, low, high);
    }

    public static void sort(double[] a) {
        if (a.length <= INSERTIONSORT_THRESHOLD) {
            InsertionSort.sort(a, 0, a.length);
        } else {
            sort(a, 0, a.length, 2 * BitMath.msb(a.length));
        }
    }

}
