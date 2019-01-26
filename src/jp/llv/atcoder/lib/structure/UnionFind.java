package jp.llv.atcoder.lib.structure;

import java.util.stream.IntStream;

public final class UnionFind {

    private final int[] groups;
    private final int[] rank;

    public UnionFind(int n) {
        groups = IntStream.range(0, n).toArray();
        rank = new int[n];
    }

    public int find(int i) {
        int ans = groups[i];
        if (ans == i) {
            return i;
        } else {
            return groups[i] = find(ans);
        }
    }

    public int rank(int i) {
        return rank[i];
    }

    public boolean union(int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y) {
            return false;
        } else if (rank[x] < rank[y]) {
            groups[x] = y;
        } else if (rank[x] == rank[y]) {
            rank[x]++;
            groups[y] = x;
        } else {
            groups[y] = x;
        }
        return true;
    }

    public int[] getGroups() {
        return groups;
    }
}
