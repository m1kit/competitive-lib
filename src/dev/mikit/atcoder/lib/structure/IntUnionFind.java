package dev.mikit.atcoder.lib.structure;

import java.util.Arrays;

public final class IntUnionFind {

    private final int[] nodes;
    private final int[] rank;

    public IntUnionFind(int n) {
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

    public int rank(int i) {
        return rank[i];
    }

    public boolean union(int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y) {
            return false;
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
        return true;
    }

    public int[] getNodes() {
        return nodes;
    }
}
