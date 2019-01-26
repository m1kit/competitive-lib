package jp.llv.atcoder.lib.graph;

public interface Graph {
    int size();

    void addDirectedEdge(int from, int to);

    void addEdge(int one, int other);
}
