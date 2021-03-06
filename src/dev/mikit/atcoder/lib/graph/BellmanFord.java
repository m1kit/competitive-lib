package dev.mikit.atcoder.lib.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BellmanFord {

    private static class Edge {
        int from, to;
        long cost;

        Edge(int from, int to, long cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    private final int nodes;
    private final List<Edge> edges = new ArrayList<>();
    private long[] distance;

    public BellmanFord(int n) {
        nodes = n;
        distance = new long[n];
    }

    public void addEdge(int from, int to, int cost) {
        edges.add(new Edge(from, to, cost));
    }

    public long shortestPath(int src, int dst) {
        Arrays.fill(distance, Long.MAX_VALUE);
        distance[src] = 0;
        for (int i = 0; i < 2 * nodes; i++) {
            boolean updated = false;
            for (Edge e : edges) {
                if (distance[e.from] < Long.MAX_VALUE && distance[e.to] > distance[e.from] + e.cost) {
                    updated = true;
                    distance[e.to] = distance[e.from] + e.cost;
                    if (i >= nodes - 1 && e.to == dst) {
                        throw new RuntimeException("negative loop");
                    }
                }
            }
            if (!updated) {
                break;
            }
        }
        for (Edge edge : edges) {
            if (distance[edge.from] + edge.cost < distance[edge.to]) {
                throw new RuntimeException("negative loop");
            }
        }
        return distance[dst];
    }

    public long[] shortestPath(int src, boolean detectNegativeLoop) {
        Arrays.fill(distance, Long.MAX_VALUE);
        distance[src] = 0;
        boolean updated = true;
        for (int i = 0; i < 2 * nodes && updated; i++) {
            updated = false;
            for (Edge e : edges) {
                if (distance[e.from] < Long.MAX_VALUE && distance[e.to] > distance[e.from] + e.cost) {
                    updated = true;
                    distance[e.to] = distance[e.from] + e.cost;
                }
            }
        }
        if (detectNegativeLoop) {
            for (Edge edge : edges) {
                if (distance[edge.from] + edge.cost < distance[edge.to]) {
                    throw new RuntimeException("negative loop");
                }
            }
        }
        return distance;
    }
}

