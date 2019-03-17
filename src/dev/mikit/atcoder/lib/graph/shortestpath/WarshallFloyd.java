package dev.mikit.atcoder.lib.graph.shortestpath;

import java.util.Arrays;

public class WarshallFloyd {

    private static final int INF = 0x2fffffff; //無限にMAX_VALUE使うとオーバーフローしてバグる
    private int[][] graph; //隣接グラフ
    private boolean calculated = false;

    public WarshallFloyd(int n) {
        graph = new int[n][n];
        Arrays.stream(graph).forEach(a -> Arrays.fill(a, INF));
    }

    public void addEdge(int from, int to, int cost) {
        graph[from][to] = cost;
    }

    public long shortestPath(int src, int dst) {
        if (!calculated) {
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph.length; j++) {
                    for (int k = 0; k < graph.length; k++) {
                        graph[j][k] = Math.min(graph[j][k], graph[j][i] + graph[i][k]);
                    }
                }
            }
            calculated = true;
        }
        return graph[src][dst] == INF ? Integer.MAX_VALUE : graph[src][dst];
    }
}
