package jp.llv.atcoder.lib.sort;

import jp.llv.atcoder.lib.meta.Verified;

import java.util.Arrays;
import java.util.function.ToIntFunction;

public class BucketSort {

    static <T> void sort(T[] a, int low, int high, int lb, int ub, ToIntFunction<? super T> index) {
        int[] c = new int[ub - lb];
        for (int i = low; i < high; i++) {
            c[index.applyAsInt(a[i]) - lb]++;
        }
        int s = 0;
        for (int i = 0; i < c.length; i++) {
            int t = c[i];
            c[i] = s;
            s += t;
        }
        T[] copy = Arrays.copyOfRange(a, low, high);
        for (int i = 0; i < high - low; i++) {
            a[low + c[index.applyAsInt(copy[i]) - lb]++] = copy[i];
        }
    }

    public static <T> void sort(T[] a, int lb, int ub, ToIntFunction<? super T> index) {
        sort(a, 0, a.length, lb, ub, index);
    }

    static void sort(int[] a, int low, int high, int lb, int ub) {
        int[] c = new int[ub - lb];
        for (int i = low; i < high; i++) {
            c[a[i] - lb]++;
        }
        int s = 0;
        for (int i = 0; i < c.length; i++) {
            int t = c[i];
            c[i] = s;
            s += t;
        }
        int[] copy = new int[high - low];
        System.arraycopy(a, low, copy, 0, high - low);
        for (int i = 0; i < high - low; i++) {
            a[low + c[copy[i] - lb]++] = copy[i];
        }
    }

    @Verified("https://atcoder.jp/contests/chokudai_S001/submissions/4256535")
    public static void sort(int[] a, int lb, int ub) {
        sort(a, 0, a.length, lb, ub);
    }

}
