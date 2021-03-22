package dev.mikit.atcoder.lib.acl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class AclMinCostFlow {
    private static final long INF = Long.MAX_VALUE;

    private final int n;
    private int m;
    private final ArrayList<Edge> edges;
    private final int[] count;
    private final Edge[][] g;
    private final long[] potential;

    private final long[] dist;
    private final Edge[] prev;

    public AclMinCostFlow(int n) {
        this.n = n;
        this.edges = new ArrayList<>(n);
        this.count = new int[n];
        this.g = new Edge[n][];
        this.potential = new long[n];
        this.dist = new long[n];
        this.prev = new Edge[n];
    }

    public int addEdge(int s, int t, long cap, long cost) {
        if (s < 0 || n <= s || t < 0 || n <= t || cap < 0 || cost < 0) throw new IllegalArgumentException();
        Edge e = new Edge(s, t, cap, cost, count[t]);
        count[s]++;
        count[t]++;
        edges.add(e);
        return m++;
    }

    private void buildGraph() {
        for (int i = 0; i < n; i++) {
            g[i] = new Edge[count[i]];
        }
        int[] idx = new int[n];
        for (Edge e : edges) {
            g[e.to][idx[e.to]++] = new Edge(e.to, e.from, 0, -e.cost, idx[e.from]);
            g[e.from][idx[e.from]++] = e;
        }
    }

    private long addFlow;
    private long addCost;

    public long[] minCostMaxFlow(int s, int t) {
        return minCostMaxFlow(s, t, INF);
    }

    public long[] minCostMaxFlow(int s, int t, long flowLimit) {
        if (s < 0 || n <= s || t < 0 || n <= t || s == t || flowLimit < 0) throw new IllegalArgumentException();
        buildGraph();
        long flow = 0;
        long cost = 0;
        while (true) {
            dijkstra(s, t, flowLimit - flow);
            if (addFlow == 0) break;
            flow += addFlow;
            cost += addFlow * addCost;
        }
        return new long[]{flow, cost};
    }

    public ArrayList<WeightedFlow> minCostSlope(int s, int t) {
        return minCostSlope(s, t, INF);
    }

    public ArrayList<WeightedFlow> minCostSlope(int s, int t, long flowLimit) {
        if (s < 0 || n <= s || t < 0 || n <= t || s == t || flowLimit < 0) throw new IllegalArgumentException();
        buildGraph();
        ArrayList<WeightedFlow> slope = new ArrayList<>();
        long prevCost = -1;
        long flow = 0;
        long cost = 0;
        while (true) {
            slope.add(new WeightedFlow(flow, cost));
            dijkstra(s, t, flowLimit - flow);
            if (addFlow == 0) return slope;
            flow += addFlow;
            cost += addFlow * addCost;
            if (addCost == prevCost) {
                slope.remove(slope.size() - 1);
            }
            prevCost = addCost;
        }
    }

    private void dijkstra(int s, int t, long maxFlow) {
        final class State implements Comparable<State> {
            private final int v;
            private final long d;

            private State(int v, long d) {
                this.v = v;
                this.d = d;
            }

            public int compareTo(State s) {
                return d == s.d ? v - s.v : d > s.d ? 1 : -1;
            }
        }
        Arrays.fill(dist, INF);
        dist[s] = 0;
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(s, 0L));
        while (pq.size() > 0) {
            State st = pq.poll();
            int u = st.v;
            if (st.d != dist[u]) continue;
            for (Edge e : g[u]) {
                if (e.cap <= 0) continue;
                int v = e.to;
                long nextCost = dist[u] + e.cost + potential[u] - potential[v];
                if (nextCost < dist[v]) {
                    dist[v] = nextCost;
                    prev[v] = e;
                    pq.add(new State(v, dist[v]));
                }
            }
        }
        if (dist[t] == INF) {
            addFlow = 0;
            addCost = INF;
            return;
        }
        for (int i = 0; i < n; i++) {
            potential[i] += dist[i];
        }
        addCost = 0;
        addFlow = maxFlow;
        for (int v = t; v != s; ) {
            Edge e = prev[v];
            addCost += e.cost;
            addFlow = Math.min(addFlow, e.cap);
            v = e.from;
        }
        for (int v = t; v != s; ) {
            Edge e = prev[v];
            e.cap -= addFlow;
            g[v][e.rev].cap += addFlow;
            v = e.from;
        }
    }

    public void clearFlow() {
        Arrays.fill(potential, 0);
        for (Edge e : edges) {
            long flow = e.getFlow();
            e.cap += flow;
            g[e.to][e.rev].cap -= flow;
        }
    }

    public Edge getEdge(int i) {
        return edges.get(i);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public final class Edge {
        private final int from, to, rev;
        private long cap, cost;

        private Edge(int from, int to, long cap, long cost, int rev) {
            this.from = from;
            this.to = to;
            this.cap = cap;
            this.cost = cost;
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

        public long getCost() {
            return cost;
        }

        public long getFlow() {
            return g[to][rev].cap;
        }
    }

    private static final class WeightedFlow {
        private final long flow, cost;

        private WeightedFlow(long flow, long cost) {
            this.flow = flow;
            this.cost = cost;
        }

        public long getFlow() {
            return flow;
        }

        public long getCost() {
            return cost;
        }
    }
}