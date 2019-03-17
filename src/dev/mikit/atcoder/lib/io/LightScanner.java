package dev.mikit.atcoder.lib.io;

import dev.mikit.atcoder.lib.util.function.BiIntFunction;
import dev.mikit.atcoder.lib.util.function.BiLongFunction;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.IntStream;

public class LightScanner {
    private BufferedReader reader = null;
    private StringTokenizer tokenizer = null;

    public LightScanner(InputStream in) {
        reader = new BufferedReader(new InputStreamReader(in));
    }

    public String string() {
        if (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return tokenizer.nextToken();
    }

    public String[] string(int length) {
        return IntStream.range(0, length).mapToObj(x -> string()).toArray(String[]::new);
    }

    public String[][] string(int height, int width) {
        return IntStream.range(0, height).mapToObj(x -> string(width)).toArray(String[][]::new);
    }

    public String nextLine() {
        if (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                return reader.readLine();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return tokenizer.nextToken("\n");
    }

    public void voids() {
        string();
    }

    public int ints() {
        return Integer.parseInt(string());
    }

    public int[] ints(int length) {
        return IntStream.range(0, length).map(x -> ints()).toArray();
    }

    public int[][] ints(int height, int width) {
        return IntStream.range(0, height).mapToObj(x -> ints(width)).toArray(int[][]::new);
    }

    public void ints(int[] ... arrays) {
        int l = Arrays.stream(arrays).mapToInt(a -> a.length).min().orElse(0);
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < arrays.length; j++) {
                arrays[j][i] = ints();
            }
        }
    }

    public long longs() {
        return Long.parseLong(string());
    }

    public long[] longs(int length) {
        return IntStream.range(0, length).mapToLong(x -> longs()).toArray();
    }

    public long[][] longs(int height, int width) {
        return IntStream.range(0, height).mapToObj(x -> longs(width)).toArray(long[][]::new);
    }

    public void longs(long[] ... arrays) {
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

    public BigInteger[] bigints(int length) {
        return IntStream.range(0, length).mapToObj(x -> ints()).toArray(BigInteger[]::new);
    }

    public BigInteger[][] bigints(int height, int width) {
        return IntStream.range(0, height).mapToObj(x -> bigints(width)).toArray(BigInteger[][]::new);
    }

    public void bigints(BigInteger[] ... arrays) {
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

    public double[] doubles(int length) {
        return IntStream.range(0, length).mapToDouble(x -> doubles()).toArray();
    }

    public double[][] doubles(int height, int width) {
        return IntStream.range(0, height).mapToObj(x -> doubles(width)).toArray(double[][]::new);
    }

    public void doubles(double[] ... arrays) {
        int l = Arrays.stream(arrays).mapToInt(a -> a.length).min().orElse(0);
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < arrays.length; j++) {
                arrays[j][i] = doubles();
            }
        }
    }

    public <T> void objs(T[] array, Function<String, T> constructor) {
        for (int i = 0; i < array.length; i++) {
            array[i] = constructor.apply(string());
        }
    }

    public <T> void objs(T[] array, BiIntFunction<T> constructor) {
        for (int i = 0; i < array.length; i++) {
            array[i] = constructor.apply(ints(), ints());
        }
    }

    public <T> void objs(T[] array, BiLongFunction<T> constructor) {
        for (int i = 0; i < array.length; i++) {
            array[i] = constructor.apply(longs(), longs());
        }
    }
}
