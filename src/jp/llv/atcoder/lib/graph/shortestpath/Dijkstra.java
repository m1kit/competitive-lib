package jp.llv.atcoder.lib.graph.shortestpath;

import jp.llv.atcoder.lib.graph.*;

import java.util.*;

public class Dijkstra {
    private Dijkstra() {
    }

    public static <N, E> IntDistanceSet<N, ? extends IntEdge<N, E>> solve(IntNodeSet<N, E> graph, Node<N, IntEdge<N, E>> start, Node<N, IntEdge<N, E>> goal) {
        IntDistanceSet<N, IntEdge<N, E>> res = new IntDistanceSet<>(graph.size());

        PriorityQueue<Node<N, IntEdge<N, E>>> q = new PriorityQueue<>(
                Comparator.comparing(n -> res.distance[n.getIndex()])
        );
        for (int i = 0; i < graph.size(); i++) {
            res.distance[i] = Long.MAX_VALUE;
            q.add(graph.getNode(i));
        }
        q.remove(start);
        res.distance[start.getIndex()] = 0;
        q.offer(start);
        while (q.size() > 0) {
            Node<N, IntEdge<N, E>> node = q.poll();
            if (res.distance[node.getIndex()] == Long.MAX_VALUE || node == goal) {
                break;
            }
            for (IntEdge<N, E> e : node.getEdges()) {
                Node<N, IntEdge<N, E>> dest = e.getTo();
                long newValue = res.distance[node.getIndex()] + e.getWeight();
                if (newValue < res.distance[dest.getIndex()]) {
                    if (q.remove(dest)) {
                        res.distance[dest.getIndex()] = newValue;
                        //res.via.set(dest.getIndex(), node);
                        q.offer(dest);
                    }
                }
            }
        }
        return res;
    }

    public static class IntDistanceSet<N, E> {
        private final long[] distance;
        private final List<Node<N, IntEdge<N, E>>> via;

        private IntDistanceSet(int n) {
            this.distance = new long[n];
            this.via = new ArrayList<>(n);
        }

        public long getDistanceTo(Node<N, IntEdge<N, E>> goal) {
            return distance[goal.getIndex()];
        }

        public Path<N, IntEdge<N, E>> getPathTo(Node<N, IntEdge<N, E>> goal) {
            LinkedList<Node<N, IntEdge<N, E>>> v = new LinkedList<>();
            for (Node<N, IntEdge<N, E>> node = goal; node != null; node = via.get(node.getIndex())) {
                v.addFirst(node);
            }
            return new Path<>(v);
        }
    }
}
