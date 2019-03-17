package dev.mikit.atcoder.lib.structure;

import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class UnionFind<T> {

    private final BinaryOperator<T> op;
    private final int[] groups;
    private final int[] rank;
    private final T[] meta;

    public UnionFind(T[] meta, BinaryOperator<T> op) {
        this.op = op;
        groups = IntStream.range(0, meta.length).toArray();
        rank = new int[meta.length];
        this.meta = meta;
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
            meta[y] = op.apply(meta[y], meta[x]);
        } else if (rank[x] == rank[y]) {
            rank[x]++;
            groups[y] = x;
            meta[x] = op.apply(meta[x], meta[y]);
        } else {
            groups[y] = x;
            meta[x] = op.apply(meta[x], meta[y]);
        }
        return true;
    }

    public T getMeta(int i) {
        return meta[find(i)];
    }

    public int[] getGroups() {
        return groups;
    }
}
