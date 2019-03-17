package dev.mikit.atcoder.lib.graph.maximumflow;

import dev.mikit.atcoder.lib.graph.IntAdjacencyMatrix;

import java.util.Arrays;

public class MaximumFlow {

    private MaximumFlow() {}


    public static long solve(IntAdjacencyMatrix m, int start, int goal) {
        int n = m.size();
        int ans = 0;
        int[] path;
        while ((path = dfs(m, start, goal, 0, new boolean[n])) != null) {
            System.out.println(Arrays.toString(path));
            System.out.println(m);
            ans++;
            for (int i = 1; i < path.length; i++) {
                m.addDirectedEdge(path[i - 1], path[i], m.getWeightBetween(path[i - 1], path[i]) - 1);
                m.addDirectedEdge(path[i], path[i - 1], m.getWeightBetween(path[i], path[i - 1]) + 1);
            }
        }
        return ans;
    }

    private static int[] dfs(IntAdjacencyMatrix graph, int start, int goal, int depth, boolean[] searched) {
        if (start == goal) {
            int[] res = new int[depth + 1];
            res[depth] = goal;
            return res;
        }
        searched[start] = true;
        for (int i = 0; i < graph.size(); i++) {
            if (!searched[i] && graph.getWeightBetween(start, i) > 0) {
                int[] res = dfs(graph, i, goal, depth + 1, searched);
                if (res != null) {
                    res[depth] = start;
                    return res;
                }
            }
        }
        return null;
    }

}
