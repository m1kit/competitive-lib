package dev.mikit.atcoder.lib.seqence;

import dev.mikit.atcoder.lib.meta.Verified;

public class StringUtil {
    private static final char[] SUPERSCRIPTS = "⁰¹²³⁴⁵⁶⁷⁸⁹".toCharArray();
    private static final char[] SUBSCRIPTS = "₀₁₂₃₄₅₆₇₈₉".toCharArray();

    private StringUtil() {
    }

    public static String tr(String str, String from, String to) {
        StringBuilder res = new StringBuilder();
        int n = str.length(), m = from.length();
        outer:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (str.charAt(i) == from.charAt(j)) {
                    res.append(to.charAt(j));
                    continue outer;
                }
            }
            res.append(str.charAt(i));
        }
        return res.toString();
    }

    public static int[] manacher(int[] s) {
        int n = s.length;
        int[] r = new int[n];
        for (int i = 0, j = 0; i < n; ) {
            while (i - j >= 0 && i + j < n && s[i - j] == s[i + j]) ++j;
            r[i] = j;
            int k = 1;
            while (i - k >= 0 && i + k < n && k + r[i - k] < j) {
                r[i + k] = r[i - k];
                k++;
            }
            i += k;
            j -= k;
        }
        return r;
    }

    public static int[] manacher(String s) {
        return manacher(s.chars().toArray());
    }

    public static int[] manacherEven(int[] s) {
        int n = s.length;
        int[] a = new int[2 * n + 1];
        a[0] = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            a[2 * i + 1] = s[i];
            a[2 * i + 2] = Integer.MIN_VALUE;
        }
        int[] r = manacher(a);
        for (int i = 0; i <= n; i++) r[i * 2] /= 2;
        for (int i = 0; i < n; i++) r[i * 2 + 1] /= 2;
        return r;
    }

    public static int[] manacherEven(String s) {
        return manacherEven(s.chars().toArray());
    }

    @Verified("https://judge.yosupo.jp/submission/2533")
    public static int[] zalgorithm(int[] s) {
        int n = s.length;
        int[] a = new int[n + 1];
        a[0] = n;
        for (int i = 1, j = 0; i < n; ) {
            while (i + j < n && s[j] == s[i + j]) j++;
            a[i] = j;
            if (j == 0) {
                i++;
                continue;
            }
            int k = 1;
            while (i + k < n && k + a[k] < j) {
                a[i + k] = a[k];
                k++;
            }
            i += k;
            j -= k;
        }
        return a;
    }

    public static int[] zalgorithm(String s) {
        return zalgorithm(s.chars().toArray());
    }

    public static String toSuperscript(String s) {
        char[] t = s.toCharArray();
        for (int i = 0; i < t.length; i++) {
            if ('0' <= t[i] && t[i] <= '9') t[i] = SUPERSCRIPTS[t[i] - '0'];
        }
        return String.valueOf(t);
    }

    public static String toSubscript(String s) {
        char[] t = s.toCharArray();
        for (int i = 0; i < t.length; i++) {
            if ('0' <= t[i] && t[i] <= '9') t[i] = SUBSCRIPTS[t[i] - '0'];
        }
        return String.valueOf(t);
    }

}
