package dev.mikit.atcoder.lib.structure.unionfindtree;

import java.util.Arrays;

public final class IntUnionFind {

    private int groups;
    private final int[] nodes;
    private final int[] rank;

    public IntUnionFind(int n) {
        groups = n;
        nodes = new int[n];
        Arrays.fill(nodes, -1);
        rank = new int[n];
    }

    public int find(int i) {
        int ans = nodes[i];
        if (ans < 0) {
            return i;
        } else {
            return nodes[i] = find(ans);
        }
    }

    public int size(int i) {
        return -nodes[find(i)];
    }

    public int size() {
        return groups;
    }

    public int rank(int i) {
        return rank[i];
    }

    public int union(int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y) {
            return -1;
        } else if (rank[x] < rank[y]) {
            nodes[y] += nodes[x];
            nodes[x] = y;
        } else if (rank[x] == rank[y]) {
            rank[x]++;
            nodes[x] += nodes[y];
            nodes[y] = x;
        } else {
            nodes[x] += nodes[y];
            nodes[y] = x;
        }
        groups--;
        return x;
    }

    public int[] getNodes() {
        return nodes;
    }
}
