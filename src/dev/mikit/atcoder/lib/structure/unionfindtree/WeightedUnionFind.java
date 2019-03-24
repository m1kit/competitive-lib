package dev.mikit.atcoder.lib.structure.unionfindtree;

import dev.mikit.atcoder.lib.util.Reflection;

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class WeightedUnionFind<T> {

    private final int[] groups;
    private final int[] rank;
    private final T[] weight;
    private final BinaryOperator<T> addition, subtraction;
    private final T zero;

    public WeightedUnionFind(int n, Class<T> clazz, BinaryOperator<T> addition, BinaryOperator<T> subtraction, T zero) {
        groups = IntStream.range(0, n).toArray();
        rank = new int[n];
        this.weight = Reflection.newInstance(clazz, n);
        this.addition = addition;
        this.subtraction = subtraction;
        Arrays.fill(weight, zero);
        this.zero = zero;
    }

    public int find(int i) {
        int parent = groups[i];
        if (parent == i) {
            return i;
        } else {
            int root = find(parent);
            weight[i] = addition.apply(weight[i], weight[parent]);
            return groups[i] = root;
        }
    }

    public int getRank(int i) {
        return rank[i];
    }

    public T getWeight(int i) {
        find(i);
        return weight[i];
    }

    public T getDistance(int x, int y) {
        return subtraction.apply(getWeight(y), getWeight(x));
    }

    public boolean union(int x, int y, T w) {
        w = addition.apply(w, getWeight(x));
        w = subtraction.apply(w, getWeight(y));
        x = find(x);
        y = find(y);
        if (x == y) {
            return getDistance(x, y).equals(w);
        } else if (rank[x] < rank[y]) {
            groups[x] = y;
            weight[x] = subtraction.apply(zero, w);
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
