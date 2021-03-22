package dev.mikit.atcoder.lib.acl;

import java.util.ArrayList;
import java.util.Arrays;

public class AclMaxFlow {

    private static final long INF = Long.MAX_VALUE;

    private final int n;
    private int m;
    private final int[] count;
    private final ArrayList<Edge> edges;
    //private final ArrayList<ArrayList<CapEdge>> g;
    private final Edge[][] g;

    public AclMaxFlow(int n) {
        this.n = n;
        this.edges = new ArrayList<>();
        this.count = new int[n];
        this.g = new Edge[n][];
    }

    public int addEdge(int from, int to, long cap) {
        if (cap < 0) throw new IllegalArgumentException("Negative capacity");
        Edge e = new Edge(from, to, cap, count[to]);
        count[from]++;
        count[to]++;
        edges.add(e);
        return m++;
    }

    public Edge getEdge(int i) {
        return edges.get(i);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void changeEdge(int i, long newCap, long newFlow) {
        if (newCap < 0) throw new IllegalArgumentException("Negative capacity");
        if (newFlow > newCap) {
            throw new IllegalArgumentException(
                    String.format("Flow %d is greater than capacity %d.", newCap, newFlow)
            );
        }
        Edge e = edges.get(i);
        Edge er = g[e.to][e.rev]; // g[e.to][e.rev];
        e.cap = newCap - newFlow;
        er.cap = newFlow;
    }

    private void buildGraph() {
        for (int i = 0; i < n; i++) {
            g[i] = new Edge[count[i]];
        }
        int[] idx = new int[n];
        for (Edge e : edges) {
            g[e.to][idx[e.to]++] = new Edge(e.to, e.from, 0, idx[e.from]);
            g[e.from][idx[e.from]++] = e;
        }
    }

    public long maxFlow(int s, int t) {
        return flow(s, t, INF);
    }

    public long flow(int s, int t, long flowLimit) {
        buildGraph();
        long flow = 0;
        int[] level = new int[n], queue = new int[n], iter = new int[n];
        while (flow < flowLimit) {
            dinicBFS(s, t, level, queue);
            if (level[t] == -1) return flow;
            Arrays.fill(iter, 0);
            while (flow < flowLimit) {
                long d = dinicDFS(t, s, flowLimit - flow, iter, level);
                if (d <= 0) break;
                flow += d;
            }
        }
        return flow;
    }

    private void dinicBFS(int s, int t, int[] level, int[] queue) {
        Arrays.fill(level, -1);
        level[s] = 0;
        int front = 0, end = 0;
        queue[end++] = s;
        while (front < end) {
            int v = queue[front++];
            for (Edge e : g[v]) {
                if (e.cap <= 0 || level[e.to] >= 0) continue;
                level[e.to] = level[v] + 1;
                if (e.to == t) return;
                queue[end++] = e.to;
            }
        }
    }

    private long dinicDFS(int v, int s, long up, int[] iter, int[] level) {
        if (v == s) return up;
        long res = 0;
        while (iter[v] < count[v]) {
            Edge e = g[v][iter[v]++];
            Edge err = g[e.to][e.rev];
            if (level[v] <= level[e.to] || err.cap <= 0) continue;
            long d = dinicDFS(e.to, s, Math.min(up - res, err.cap), iter, level);
            if (d <= 0) continue;
            e.cap += d;
            err.cap -= d;
            res += d;
            if (res == up) break;
        }
        return res;
    }

    public boolean[] minCut(int s) {
        boolean[] visited = new boolean[n];
        int[] stack = new int[n];
        int ptr = 0;
        stack[ptr++] = s;
        visited[s] = true;
        while (ptr > 0) {
            int u = stack[--ptr];
            for (Edge e : g[u]) {
                int v = e.to;
                if (visited[v] || e.cap <= 0) continue;
                visited[v] = true;
                stack[ptr++] = v;
            }
        }
        return visited;
    }

    public class Edge {
        private final int from, to, rev;
        private long cap;

        Edge(int from, int to, long cap, int rev) {
            this.from = from;
            this.to = to;
            this.cap = cap;
            this.rev = rev;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        public long getCap() {
            return cap;
        }

        public long getFlow() {
            return g[to][rev].cap;
        }
    }
}
