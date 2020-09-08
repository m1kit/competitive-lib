package dev.mikit.atcoder.lib.seqence;

import dev.mikit.atcoder.lib.meta.Verified;
import dev.mikit.atcoder.lib.sort.IntroSort;
import dev.mikit.atcoder.lib.structure.fenwicktree.IntFenwickTree;

@Verified("http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=4805527")
public class InvNum {
    private InvNum() {
    }

    public static long calcPerm(int[] a) {
        int n = a.length;
        IntFenwickTree bit = new IntFenwickTree(n, Long::sum, 0L);
        long ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            ans += bit.query(a[i]);
            bit.add(a[i], 1);
        }
        return ans;
    }

    public static long calc(long[] a) {
        int n = a.length;
        Entry[] entries = new Entry[n];
        for (int i = 0; i < n; i++) entries[i] = new Entry(i, a[i]);
        IntroSort.sort(entries);
        int[] perm = new int[n];
        for (int i = 0; i < n; i++) perm[i] = entries[i].index;
        return calcPerm(perm);
    }

    private static class Entry implements Comparable<Entry> {
        int index;
        long value;

        Entry(int index, long value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Entry o) {
            return value == o.value ? Integer.compare(index, o.index) : Long.compare(value, o.value);
        }
    }
}
