package dev.mikit.atcoder.lib.graph;

public interface EdgeLike<T extends WeightedNodeLike<T, U>, U extends EdgeLike<T, U>> {

    default T getSrc() {
        throw new UnsupportedOperationException("This edge struct does not provide source node information");
    }

    T getDst();

    long getWeight();

}
