package dev.mikit.atcoder.lib.io;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.IntStream;

public abstract class LightScannerAdapter implements AutoCloseable {

    public abstract String string();

    /**
     * This method is required by CHelper.
     *
     * @return just one string token
     */
    public final String next() {
        return string();
    }

    public final String[] string(int length) {
        String[] res = new String[length];
        Arrays.setAll(res, ignored -> string());
        return res;
    }

    public final String[][] string(int height, int width) {
        String[][] res = new String[height][];
        Arrays.setAll(res, ignored -> string(width));
        return res;
    }

    public char chr() {
        return string().charAt(0);
    }

    public char[] chars() {
        return string().toCharArray();
    }

    public final char[][] chars(int h) {
        char[][] res = new char[h][];
        Arrays.setAll(res, ignored -> chars());
        return res;
    }

    public abstract String nextLine();

    public final void voids() {
        string();
    }

    public int ints() {
        return Integer.parseInt(string());
    }

    public final int[] ints(int length) {
        int[] res = new int[length];
        Arrays.setAll(res, ignored -> ints());
        return res;
    }

    public final int[][] ints(int height, int width) {
        int[][] res = new int[height][];
        Arrays.setAll(res, ignored -> ints(width));
        return res;
    }

    public final void ints(int[]... arrays) {
        int l = Arrays.stream(arrays).mapToInt(a -> a.length).min().orElse(0);
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < arrays.length; j++) {
                arrays[j][i] = ints();
            }
        }
    }

    public long multiplyAndLongs(long multiplier) {
        String[] tokens = string().split("\\.");
        if (tokens.length == 1) return Long.parseLong(tokens[0]) * multiplier;
        if (tokens.length != 2) throw new NumberFormatException();
        long x = Long.parseLong(tokens[0]) * multiplier;
        long y = Long.parseLong(tokens[1]) * multiplier;
        int len = tokens[1].length();
        while (len-- > 0) y /= 10;
        return x + y;
    }

    public final long[] multiplyAndLongs(int length, long multiplier) {
        long[] res = new long[length];
        Arrays.setAll(res, ignored -> multiplyAndLongs(multiplier));
        return res;
    }

    public long longs() {
        return Long.parseLong(string());
    }

    public final long[] longs(int length) {
        long[] res = new long[length];
        Arrays.setAll(res, ignored -> longs());
        return res;
    }

    public final long[][] longs(int height, int width) {
        long[][] res = new long[height][];
        Arrays.setAll(res, ignored -> longs(width));
        return res;
    }

    public final void longs(long[]... arrays) {
        int l = Arrays.stream(arrays).mapToInt(a -> a.length).min().orElse(0);
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < arrays.length; j++) {
                arrays[j][i] = longs();
            }
        }
    }

    public BigInteger bigints() {
        return new BigInteger(string());
    }

    public final BigInteger[] bigints(int length) {
        BigInteger[] res = new BigInteger[length];
        Arrays.setAll(res, ignored -> bigints());
        return res;
    }

    public final BigInteger[][] bigints(int height, int width) {
        BigInteger[][] res = new BigInteger[height][];
        Arrays.setAll(res, ignored -> bigints(width));
        return res;
    }

    public final void bigints(BigInteger[]... arrays) {
        int l = Arrays.stream(arrays).mapToInt(a -> a.length).min().orElse(0);
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < arrays.length; j++) {
                arrays[j][i] = bigints();
            }
        }
    }

    public double doubles() {
        return Double.parseDouble(string());
    }

    public final double[] doubles(int length) {
        double[] res = new double[length];
        Arrays.setAll(res, ignored -> doubles());
        return IntStream.range(0, length).mapToDouble(x -> doubles()).toArray();
    }

    public final double[][] doubles(int height, int width) {
        double[][] res = new double[height][];
        Arrays.setAll(res, ignored -> doubles(width));
        return res;
    }

    public final void doubles(double[]... arrays) {
        int l = Arrays.stream(arrays).mapToInt(a -> a.length).min().orElse(0);
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < arrays.length; j++) {
                arrays[j][i] = doubles();
            }
        }
    }

    @Override
    public abstract void close();
}
