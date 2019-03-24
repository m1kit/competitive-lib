package dev.mikit.atcoder.lib.structure.unionfindtree;

import dev.mikit.atcoder.lib.meta.Verified;

import java.util.stream.IntStream;

@Verified({
        "https://atcoder.jp/contests/arc090/submissions/4637871",
})
public class IntWeightedUnionFind {

    private final int[] groups;
    private final int[] rank;
    private final long[] weight;

    public IntWeightedUnionFind(int n) {
        groups = IntStream.range(0, n).toArray();
        rank = new int[n];
        this.weight = new long[n];
    }

    public int find(int i) {
        int parent = groups[i];
        if (parent == i) {
            return i;
        } else {
            int root = find(parent);
            weight[i] += weight[parent];
            return groups[i] = root;
        }
    }

    public int getRank(int i) {
        return rank[i];
    }

    public long getWeight(int i) {
        find(i);
        return weight[i];
    }

    public long getDistance(int x, int y) {
        return getWeight(y) - getWeight(x);
    }

    public boolean union(int x, int y, long w) {
        w += getWeight(x);
        w -= getWeight(y);
        x = find(x);
        y = find(y);
        if (x == y) {
            return getDistance(x, y) == w;
        } else if (rank[x] < rank[y]) {
            groups[x] = y;
            weight[x] = -w;
        } else if (rank[x] == rank[y]) {
            rank[x]++;
            groups[y] = x;
            weight[y] = w;
        } else {
            groups[y] = x;
            weight[y] = w;
        }
        return true;
    }

    public int[] getGroups() {
        return groups;
    }
}
