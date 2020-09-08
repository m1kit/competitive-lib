package dev.mikit.atcoder.lib.seqence;

import dev.mikit.atcoder.lib.util.ArrayUtil;

import java.util.Arrays;

public class LIS {
    private LIS() {}

    public static int getLIS(long[] a) {
        int n = a.length;
        long[] dp = new long[n];
        Arrays.fill(dp, Long.MAX_VALUE);
        for (long l : a) dp[ArrayUtil.lowerBound(dp, l)] = l;
        return ArrayUtil.lowerBound(dp, Long.MAX_VALUE);
    }

    public static int getLIS(int[] a) {
        int n = a.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int l : a) dp[ArrayUtil.lowerBound(dp, l)] = l;
        return ArrayUtil.lowerBound(dp, Integer.MAX_VALUE);
    }
}
