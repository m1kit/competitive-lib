package dev.mikit.atcoder.lib.util;

import dev.mikit.atcoder.lib.meta.Verified;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public final class ArrayUtil {

    private ArrayUtil() {
    }

    public static int[] concat(int[]...as) {
        int total = 0;
        for (int[] a : as) total += a.length;
        int[] res = new int[total];
        int head = 0;
        for (int[] a : as) {
            System.arraycopy(a, 0, res, head, a.length);
            head += a.length;
        }
        return res;
    }

    public static long[] concat(long[]...as) {
        int total = 0;
        for (long[] a : as) total += a.length;
        long[] res = new long[total];
        int head = 0;
        for (long[] a : as) {
            System.arraycopy(a, 0, res, head, a.length);
            head += a.length;
        }
        return res;
    }

    public static char[] concat(char[]...as) {
        int total = 0;
        for (char[] a : as) total += a.length;
        char[] res = new char[total];
        int head = 0;
        for (char[] a : as) {
            System.arraycopy(a, 0, res, head, a.length);
            head += a.length;
        }
        return res;
    }

    public static double[] concat(double[]...as) {
        int total = 0;
        for (double[] a : as) total += a.length;
        double[] res = new double[total];
        int head = 0;
        for (double[] a : as) {
            System.arraycopy(a, 0, res, head, a.length);
            head += a.length;
        }
        return res;
    }

    public static boolean[] concat(boolean[]...as) {
        int total = 0;
        for (boolean[] a : as) total += a.length;
        boolean[] res = new boolean[total];
        int head = 0;
        for (boolean[] a : as) {
            System.arraycopy(a, 0, res, head, a.length);
            head += a.length;
        }
        return res;
    }

    public static <T> T[] concat(T[]...as) {
        int total = 0;
        for (T[] a : as) total += a.length;
        T[] res = Reflection.newInstance(Reflection.getComponentClass(as[0]), total);
        int head = 0;
        for (T[] a : as) {
            System.arraycopy(a, 0, res, head, a.length);
            head += a.length;
        }
        return res;
    }

    public static void fill(int[] a, int v) {
        Arrays.fill(a, v);
    }

    public static void fill(int[][] a, int v) {
        for (int[] ints : a) {
            fill(ints, v);
        }
    }

    public static void fill(int[][][] a, int v) {
        for (int[][] ints : a) {
            fill(ints, v);
        }
    }

    public static void fill(int[][][][] a, int v) {
        for (int[][][] ints : a) {
            fill(ints, v);
        }
    }

    public static void fill(char[] a, char v) {
        Arrays.fill(a, v);
    }

    public static void fill(char[][] a, char v) {
        for (int i = 0; i < a.length; i++) {
            fill(a[i], v);
        }
    }

    public static void fill(char[][][] a, char v) {
        for (int i = 0; i < a.length; i++) {
            fill(a[i], v);
        }
    }

    public static void fill(long[] a, long v) {
        Arrays.fill(a, v);
    }

    public static void fill(long[][] a, long v) {
        for (int i = 0; i < a.length; i++) {
            fill(a[i], v);
        }
    }

    public static void fill(long[][][] a, long v) {
        for (long[][] longs : a) {
            fill(longs, v);
        }
    }

    public static void fill(long[][][][] a, int v) {
        for (long[][][] longs : a) {
            fill(longs, v);
        }
    }

    public static void fill(double[] a, double v) {
        Arrays.fill(a, v);
    }

    public static void fill(double[][] a, double v) {
        for (double[] doubles : a) {
            fill(doubles, v);
        }
    }

    public static void fill(double[][][] a, double v) {
        for (double[][] doubles : a) {
            fill(doubles, v);
        }
    }

    public static void fill(double[][][][] a, double v) {
        for (double[][][] doubles : a) {
            fill(doubles, v);
        }
    }

    public static void fill(boolean[] a, boolean v) {
        Arrays.fill(a, v);
    }

    public static void fill(boolean[][] a, boolean v) {
        for (boolean[] booleans : a) {
            fill(booleans, v);
        }
    }

    public static void fill(boolean[][][] a, boolean v) {
        for (boolean[][] booleans : a) {
            fill(booleans, v);
        }
    }

    public static void fill(boolean[][][][] a, boolean v) {
        for (boolean[][][] booleans : a) {
            fill(booleans, v);
        }
    }

    public static <T> void fill(T[] a, T v) {
        Arrays.fill(a, v);
    }

    public static <T> void fill(T[][] a, T v) {
        for (T[] ts : a) {
            fill(ts, v);
        }
    }

    public static <T> void fill(T[][][] a, T v) {
        for (T[][] ts : a) {
            fill(ts, v);
        }
    }

    public static <T> void fill(T[][][][] a, T v) {
        for (T[][][] ts : a) {
            fill(ts, v);
        }
    }

    public static void reverse(int[] a, int left, int right) {
        right--;
        while (left < right) {
            int temp = a[left];
            a[left++] = a[right];
            a[right--] = temp;
        }
    }

    public static void reverse(double[] a, int left, int right) {
        right--;
        while (left < right) {
            double temp = a[left];
            a[left++] = a[right];
            a[right--] = temp;
        }
    }

    public static void reverse(long[] a, int left, int right) {
        right--;
        while (left < right) {
            long temp = a[left];
            a[left++] = a[right];
            a[right--] = temp;
        }
    }

    public static void reverse(char[] a, int left, int right) {
        right--;
        while (left < right) {
            char temp = a[left];
            a[left++] = a[right];
            a[right--] = temp;
        }
    }

    public static <T> void reverse(T[] a, int left, int right) {
        right--;
        while (left < right) {
            T temp = a[left];
            a[left++] = a[right];
            a[right--] = temp;
        }
    }

    public static char[] toCharArray(int[] a) {
        char[] temp = new char[a.length];
        for (int i = 0; i < a.length; i++) {
            temp[i] = (char) a[i];
        }
        return temp;
    }

    @Verified("https://codeforces.com/contest/1111/submission/49436627")
    public static int lowerBound(int[] a, int t) {
        return lowerBound(a, t, 0);
    }

    public static int lowerBound(int[] a, int t, int min) {
        int max = a.length;
        while (min < max) {
            int mid = (min + max) / 2;
            if (t <= a[mid]) {
                max = mid;
            } else {
                min = mid + 1;
            }
        }
        return min;
    }

    public static int lowerBound(long[] a, long t) {
        return lowerBound(a, t, 0);
    }

    public static int lowerBound(long[] a, long t, int min) {
        int max = a.length;
        while (min < max) {
            int mid = (min + max) / 2;
            if (t <= a[mid]) {
                max = mid;
            } else {
                min = mid + 1;
            }
        }
        return min;
    }

    public static <T> int minTrue(T[] a, Predicate<? super T> t) {
        return minTrue(a, t, 0);
    }

    public static <T> int minTrue(T[] a, Predicate<? super T> t, int min) {
        int max = a.length;
        while (min < max) {
            int mid = (min + max) / 2;
            if (t.test(a[mid])) {
                max = mid;
            } else {
                min = mid + 1;
            }
        }
        return min;
    }

    public static int upperBound(int[] a, int t, int min) {
        int max = a.length;
        while (min < max) {
            final int mid = (min + max) / 2;
            if (t >= a[mid]) {
                min = mid + 1;
            } else {
                max = mid;
            }
        }
        return min;
    }

    public static int upperBound(int[] a, int t) {
        return upperBound(a, t, 0);
    }

    public static int upperBound(long[] a, long t, int min) {
        int max = a.length;
        while (min < max) {
            final int mid = (min + max) / 2;
            if (t >= a[mid]) {
                min = mid + 1;
            } else {
                max = mid;
            }
        }
        return min;
    }

    public static int upperBound(long[] a, long t) {
        return upperBound(a, t, 0);
    }

    public static <T> int indexOf(T[] a, T v) {
        for (int i = 0; i < a.length; i++) {
            if (Objects.equals(a[i], v)) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(int[] a, int v) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == v) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(long[] a, long v) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == v) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(double[] a, double v) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == v) {
                return i;
            }
        }
        return -1;
    }

    public static void shuffle(int[] array) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int tmp = array[index];
            array[index] = array[i];
            array[i] = tmp;
        }
    }

    public static void shuffle(long[] array) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            long tmp = array[index];
            array[index] = array[i];
            array[i] = tmp;
        }
    }

    public static void shuffle(double[] array) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            double tmp = array[index];
            array[index] = array[i];
            array[i] = tmp;
        }
    }

    public static void shuffle(Object[] array) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Object tmp = array[index];
            array[index] = array[i];
            array[i] = tmp;
        }
    }

    public static <T> void swap(T[] a, int x, int y) {
        T t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    public static void swap(char[] a, int x, int y) {
        char t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    public static void swap(int[] a, int x, int y) {
        int t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    public static void swap(long[] a, int x, int y) {
        long t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    public static void swap(double[] a, int x, int y) {
        double t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    public static int minTrue(IntPredicate p, int lo, int hi) {
        while (hi - lo > 1) {
            int mid = (lo >> 1) + (hi >> 1) + (lo & hi & 1);
            if (p.test(mid)) hi = mid;
            else lo = mid;
        }
        return hi;
    }

    public static int maxTrue(IntPredicate p, int lo, int hi) {
        while (hi - lo > 1) {
            int mid = (lo >> 1) + (hi >> 1) + (lo & hi & 1);
            if (p.test(mid)) lo = mid;
            else hi = mid;
        }
        return lo;
    }

}
