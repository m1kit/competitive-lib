package dev.mikit.atcoder.lib.seqence;

import dev.mikit.atcoder.lib.meta.Verified;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Verified("http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3897322#1")
public class KMP {

    private final int n;
    private final int[] target;
    private final int[] table;

    public KMP(int[] target) {
        this.n = target.length;
        this.target = Arrays.copyOf(target, n);
        this.table = new int[n];
        this.table[0] = -1;
        int i = 2, j = 0;
        while (i < n) {
            if (this.target[i - 1] == this.target[j]) {
                this.table[i] = ++j;
                i++;
            } else if (j > 0) {
                j = this.table[j];
            } else {
                this.table[i] = 0;
                i++;
            }
        }
    }

    public KMP(String target) {
        this(target.chars().toArray());
    }


    public Iterator<Integer> search(int[] s) {
        return new Match(s);
    }

    public  Iterator<Integer> search(String s) {
        return search(s.chars().toArray());
    }

    private class Match implements Iterator<Integer> {

        int[] s;
        int m = 0;
        int i = 0;
        boolean hasNext;

        Match(int[] s) {
            this.s = s;
            hasNext = seek();
        }

         boolean seek() {
            while (m + i < s.length) {
                if (target[i] == s[m + i]) {
                    if (++i == n) return true;
                } else {
                    m = m + i - table[i];
                    if (i > 0) i = table[i];
                }
            }
            return false;
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public Integer next() {
            if (!hasNext) throw new NoSuchElementException();
            int res = m++;
            i = n > 1 ? table[n - 1] : 0;
            hasNext = seek();
            return res;
        }
    }

}
