package dev.mikit.atcoder.lib.graph;

public interface IntGraph extends Graph {


    void addDirectedEdge(int from, int to, long weight);

    @Override
    default void addDirectedEdge(int from, int to) {
        addDirectedEdge(from, to, 1);
    }

    default void addEdge(int one, int other, long weight) {
        addDirectedEdge(one, other, weight);
        addDirectedEdge(other, one, weight);
    }

    @Override
    default void addEdge(int one, int other) {
        addDirectedEdge(one, other);
        addDirectedEdge(other, one);
    }

    long getWeightBetween(int from, int to);

}
