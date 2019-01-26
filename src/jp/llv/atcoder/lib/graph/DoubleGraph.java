package jp.llv.atcoder.lib.graph;

public interface DoubleGraph extends Graph {

    void addDirectedEdge(int from, int to, double weight);

    @Override
    default void addDirectedEdge(int from, int to) {
        addDirectedEdge(from, to, 1);
    }

    default void addEdge(int one, int other, double weight) {
        addDirectedEdge(one, other, weight);
        addDirectedEdge(other, one, weight);
    }

    @Override
    default void addEdge(int one, int other) {
        addDirectedEdge(one, other);
        addDirectedEdge(other, one);
    }

    double getWeightBetween(int from, int to);
}
