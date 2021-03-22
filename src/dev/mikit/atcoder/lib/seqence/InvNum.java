package dev.mikit.atcoder.lib.seqence;

import dev.mikit.atcoder.lib.math.BitMath;
import dev.mikit.atcoder.lib.meta.Verified;
import dev.mikit.atcoder.lib.sort.IntroSort;
import dev.mikit.atcoder.lib.util.function.IntComparator;

import java.util.stream.IntStream;

@Verified("http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=4805527")
public class InvNum {
    private InvNum() {
    }

    public static long calcPerm(int[] a) {
        int n = a.length;
        RangeSumPointAddFenwickTree bit = new RangeSumPointAddFenwickTree(n);
        long ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            ans += bit.query(a[i]);
            bit.inc(a[i]);
        }
        return ans;
    }

    public static long calc(long[] a) {
        int n = a.length;
        int[] p = IntStream.range(0, n).toArray();
        IntComparator cmp = (x, y) -> {
            if (a[x] != a[y]) return Long.compare(a[x], a[y]);
            return x - y;
        };
        IntroSort.sort(p, cmp);
        return calcPerm(p);
    }

    private static class RangeSumPointAddFenwickTree {

        final int n;
        final int[] tree;

        RangeSumPointAddFenwickTree(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void inc(int index) {
            for (index++; index <= n; index += BitMath.extractLsb(index)) {
                tree[index]++;
            }
        }

        int query(int last) {
            int res = 0;
            for (; last > 0; last -= BitMath.extractLsb(last)) {
                res += tree[last];
            }
            return res;
        }
    }
}
