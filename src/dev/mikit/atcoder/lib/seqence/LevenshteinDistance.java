package dev.mikit.atcoder.lib.seqence;

public class LevenshteinDistance {

    public static int getDistanceBetween(String s, String t) {
        return getDistanceBetween(s.chars().toArray(), t.chars().toArray());
    }

    public static int getDistanceBetween(int[] s, int[] t) {
        int n = s.length, m = t.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i + 1][j + 1] = dp[i][j] + (s[i] == t[j] ? 0 : 1);
                dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j + 1] + 1);
                dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i + 1][j] + 1);
            }
        }
        return dp[n][m];
    }

}
