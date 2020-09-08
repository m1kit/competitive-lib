package dev.mikit.atcoder.lib.graph;

import dev.mikit.atcoder.lib.meta.Verified;

import java.util.*;

@Verified("https://onlinejudge.u-aizu.ac.jp/solutions/problem/GRL_6_A/review/3898292/mikit/JAVA")
public class Dinic {

    private final int n;
    private final List<List<Edge>> edges;
    private final int[] itr, level;


    public Dinic(int n) {
        this.n = n;
        this.edges = new ArrayList<>(n);
        for (int i = 0; i < n; i++) this.edges.add(new ArrayList<>());
        this.itr = new int[n];
        this.level = new int[n];
    }

    public void addEdge(int from, int to, long cap) {
        Edge natural = new Edge(to, cap), reverse = new Edge(from, 0);
        natural.rev = reverse;
        reverse.rev = natural;
        edges.get(from).add(natural);
        edges.get(to).add(reverse);
    }

    private void bfs(int s) {
        Arrays.fill(level, -1);
        Queue<Integer> q = new ArrayDeque<>();
        level[s] = 0;
        q.offer(s);
        while (!q.isEmpty()) {
            int from = q.poll();
            for (Edge edge : edges.get(from)) {
                if (edge.cap > 0 && level[edge.to] < 0) {
                    level[edge.to] = level[from] + 1;
                    q.offer(edge.to);
                }
            }
        }
    }

    private long dfs(int from, int to, long flow) {
        if (from == to) return flow;
        for (int i = itr[from]; i < edges.get(from).size(); i++) {
            Edge edge = edges.get(from).get(i);
            if (edge.cap > 0 && level[from] < level[edge.to]) {
                long d = dfs(edge.to, to, Math.min(flow, edge.cap));
                if (d > 0) {
                    edge.cap -= d;
                    edge.rev.cap += d;
                    return d;
                }
            }
        }
        return 0;
    }

    public long run(int from, int to) {
        long res = 0, f;
        bfs(from);
        while (level[to] >= 0) {
            Arrays.fill(itr, 0);
            while (0 < (f = dfs(from, to, Long.MAX_VALUE))) res += f;
            bfs(from);
        }
        return res;
    }

    public List<List<Edge>> getRawEdges() {
        return edges;
    }

    public static class Edge {
        public int to;
        public long cap;
        public Edge rev;

        Edge(int to, long cap) {
            this.to = to;
            this.cap = cap;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "to=" + to +
                    ", cap=" + cap +
                    '}';
        }
    }

}
