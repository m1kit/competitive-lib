package dev.mikit.atcoder.lib.seqence;

import dev.mikit.atcoder.lib.meta.Verified;
import dev.mikit.atcoder.lib.sort.IntroSort;
import dev.mikit.atcoder.lib.util.function.IntComparator;

import java.util.Arrays;
import java.util.function.Consumer;

public class StringUtil {

    private static final int SA_THRESHOLD_NAIVE = 10;
    private static final int SA_THRESHOLD_DOUBLING = 40;
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

    private static int[] saNaive(int[] s) {
        int n = s.length;
        int[] sa = new int[n];
        for (int i = 0; i < n; i++) sa[i] = i;
        IntroSort.sort(sa, (l, r) -> {
            while (l < n && r < n) {
                if (s[l] != s[r]) return s[l] - s[r];
                l++;
                r++;
            }
            return r - l;
        });
        return sa;
    }

    private static int[] saDoubling(int[] s) {
        int n = s.length;
        int[] sa = new int[n];
        for (int i = 0; i < n; i++) sa[i] = i;
        int[] rnk = s.clone();
        int[] tmp = new int[n];

        for (int k = 1; k < n; k *= 2) {
            final int _k = k;
            final int[] _rnk = rnk;
            IntComparator cmp = (x, y) -> {
                if (_rnk[x] != _rnk[y]) return _rnk[x] - _rnk[y];
                int rx = x + _k < n ? _rnk[x + _k] : -1;
                int ry = y + _k < n ? _rnk[y + _k] : -1;
                return rx - ry;
            };
            IntroSort.sort(sa, cmp);
            tmp[sa[0]] = 0;
            for (int i = 1; i < n; i++) {
                tmp[sa[i]] = tmp[sa[i - 1]] + (cmp.compare(sa[i - 1], sa[i]) < 0 ? 1 : 0);
            }
            int[] buf = tmp;
            tmp = rnk;
            rnk = buf;
        }
        return sa;
    }

    private static int[] sais(int[] s, int upper) {
        int n = s.length;
        if (n == 0) return new int[0];
        if (n == 1) return new int[]{0};
        if (n == 2) {
            return s[0] < s[1] ? new int[]{0, 1} : new int[]{1, 0};
        }
        if (n < SA_THRESHOLD_NAIVE) return saNaive(s);
        if (n < SA_THRESHOLD_DOUBLING) return saDoubling(s);

        int[] sa = new int[n];
        boolean[] ls = new boolean[n];
        for (int i = n - 2; i >= 0; i--) {
            ls[i] = s[i] < s[i + 1] || (s[i] == s[i + 1] && ls[i + 1]);
        }

        int[] sumL = new int[upper + 1];
        int[] sumS = new int[upper + 1];

        for (int i = 0; i < n; i++) {
            if (ls[i]) {
                sumL[s[i] + 1]++;
            } else {
                sumS[s[i]]++;
            }
        }

        for (int i = 0; i <= upper; i++) {
            sumS[i] += sumL[i];
            if (i < upper) sumL[i + 1] += sumS[i];
        }

        Consumer<int[]> induce = lms -> {
            Arrays.fill(sa, -1);
            int[] buf = new int[upper + 1];
            System.arraycopy(sumS, 0, buf, 0, upper + 1);
            for (int d : lms) {
                if (d == n) continue;
                sa[buf[s[d]]++] = d;
            }
            System.arraycopy(sumL, 0, buf, 0, upper + 1);
            sa[buf[s[n - 1]]++] = n - 1;
            for (int i = 0; i < n; i++) {
                int v = sa[i];
                if (v >= 1 && !ls[v - 1]) {
                    sa[buf[s[v - 1]]++] = v - 1;
                }
            }
            System.arraycopy(sumL, 0, buf, 0, upper + 1);
            for (int i = n - 1; i >= 0; i--) {
                int v = sa[i];
                if (v >= 1 && ls[v - 1]) {
                    sa[--buf[s[v - 1] + 1]] = v - 1;
                }
            }
        };

        int[] lmsMap = new int[n + 1];
        Arrays.fill(lmsMap, -1);
        int m = 0;
        for (int i = 1; i < n; i++) {
            if (!ls[i - 1] && ls[i]) lmsMap[i] = m++;
        }

        int[] lms = new int[m];
        {
            int p = 0;
            for (int i = 1; i < n; i++) {
                if (!ls[i - 1] && ls[i]) lms[p++] = i;
            }
        }

        induce.accept(lms);

        if (m == 0) return sa;
        int[] sortedLms = new int[m];
        {
            int p = 0;
            for (int v : sa) {
                if (lmsMap[v] != -1) sortedLms[p++] = v;
            }
        }
        int[] recS = new int[m];
        int recUpper = 0;
        recS[lmsMap[sortedLms[0]]] = 0;
        for (int i = 1; i < m; i++) {
            int l = sortedLms[i - 1], r = sortedLms[i];
            int endL = (lmsMap[l] + 1 < m) ? lms[lmsMap[l] + 1] : n;
            int endR = (lmsMap[r] + 1 < m) ? lms[lmsMap[r] + 1] : n;
            boolean same = true;
            if (endL - l != endR - r) {
                same = false;
            } else {
                while (l < endL && s[l] == s[r]) {
                    l++;
                    r++;
                }
                if (l == n || s[l] != s[r]) same = false;
            }
            if (!same) {
                recUpper++;
            }
            recS[lmsMap[sortedLms[i]]] = recUpper;
        }

        int[] recSA = sais(recS, recUpper);
        for (int i = 0; i < m; i++) {
            sortedLms[i] = lms[recSA[i]];
        }
        induce.accept(sortedLms);
        return sa;
    }

    public static int[] suffixArray(int[] s, int upper) {
        return sais(s, upper);
    }

    public static int[] suffixArray(int[] s) {
        int n = s.length;
        int[] idx = new int[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        IntroSort.sort(idx, IntComparator.comparingInt(l -> s[l]));
        int[] s2 = new int[n];
        int now = 0;
        for (int i = 0; i < n; i++) {
            if (i > 0 && s[idx[i - 1]] != s[idx[i]]) now++;
            s2[idx[i]] = now;
        }
        return sais(s2, now);
    }

    public static int[] suffixArray(char[] s) {
        int n = s.length;
        int[] s2 = new int[n];
        for (int i = 0; i < n; i++) s2[i] = s[i] - 32;
        return sais(s2, 96);
    }

    public static int[] suffixArray(String s) {
        return suffixArray(s.toCharArray());
    }

    public static int[] lcpArray(int[] s, int[] sa) {
        int n = s.length;
        int[] rnk = new int[n];
        for (int i = 0; i < n; i++) rnk[sa[i]] = i;
        int[] lcp = new int[n - 1];
        int h = 0;
        for (int i = 0; i < n; i++) {
            if (h > 0) h--;
            if (rnk[i] == 0) continue;
            for (int j = sa[rnk[i] - 1]; j + h < n && i + h < n; h++) {
                if (s[j + h] != s[i + h]) break;
            }
            lcp[rnk[i] - 1] = h;
        }
        return lcp;
    }

    public static int[] lcpArray(char[] s, int[] sa) {
        int n = s.length;
        int[] s2 = new int[n];
        for (int i = 0; i < n; i++) s2[i] = s[i];
        return lcpArray(s2, sa);
    }

    public static int[] lcpArray(String s, int[] sa) {
        return lcpArray(s.toCharArray(), sa);
    }

    public static int[] lcpArray(int[] s, int upper) {
        return lcpArray(s, suffixArray(s, upper));
    }

    public static int[] lcpArray(int[] s) {
        return lcpArray(s, suffixArray(s));
    }

    public static int[] lcpArray(char[] s) {
        int n = s.length;
        int[] is = new int[n];
        for (int i = 0; i < n; i++) is[i] = s[i] - 32;
        return lcpArray(is, suffixArray(is, 96));
    }

    public static int[] lcpArray(String s) {
        return lcpArray(s.toCharArray());
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
