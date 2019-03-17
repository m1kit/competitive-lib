package dev.mikit.atcoder.lib.graph;

import dev.mikit.atcoder.lib.util.ArrayUtil;

import java.util.Arrays;

public final class IntAdjacencyMatrix implements IntGraph {

    private static final int INF = 0x2fffffff;

    private final int n;
    private final long[][] matrix;

    public IntAdjacencyMatrix(int n) {
        this.n = n;
        this.matrix = new long[n][n];
        ArrayUtil.fill(matrix, INF);
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public void addDirectedEdge(int from, int to, long weight) {
        matrix[from][to] = weight;
    }

    @Override
    public long getWeightBetween(int from, int to) {
        return matrix[from][to] == INF ? Long.MAX_VALUE : matrix[from][to];
    }

    /**
     * Calcurate shortest path by Warshall Floyd's algorithm
     */
    public void shorten() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    matrix[j][k] = Math.min(matrix[j][k], matrix[j][i] + matrix[i][k]);
                }
            }
        }
    }

    @Override
    public String toString() {
        return Arrays.deepToString(matrix);
    }
}
