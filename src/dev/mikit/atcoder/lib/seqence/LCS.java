package dev.mikit.atcoder.lib.seqence;

import dev.mikit.atcoder.lib.util.ArrayUtil;

import java.util.stream.IntStream;

public class LCS {
    private final int[] s, t;
    private final int[][] dp;

    public LCS(int[] s, int[] t) {
        this.s = s;
        this.t = t;
        int n = s.length, m = t.length;
        dp = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (s[i] == t[j]) dp[i + 1][j + 1] = dp[i][j] + 1;
                else dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
            }
        }
    }

    public LCS(String s, String t) {
        this(s.chars().toArray(), t.chars().toArray());
    }

    public int getLength() {
        return dp[s.length][t.length];
    }

    public int[] build() {
        int[] res = new int[getLength()];
        int i = s.length, j = t.length, cur = res.length;
        while (cur > 0) {
            if (s[i - 1] != t[j - 1]) {
                if (dp[i - 1][j] > dp[i][j - 1]) i--;
                else j--;
            } else {
                res[--cur] = s[i - 1];
                i--;
                j--;
            }
        }
        return res;
    }

    public String buildAsString() {
        return IntStream.of(build()).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
