package dev.mikit.atcoder.lib.graph;

import java.util.Collection;
import java.util.stream.Collectors;

public interface WeightedNodeLike<T extends WeightedNodeLike<T, U>, U extends EdgeLike<T, U>> extends NodeLike<T> {

    default Collection<T> getNextNodes() {
        return getNextEdges().stream().map(EdgeLike::getDst).collect(Collectors.toList());
    }

    default Collection<T> getPrevNodes() {
        return getPrevEdges().stream().map(EdgeLike::getSrc).collect(Collectors.toList());
    }

    Collection<U> getNextEdges();

    default Collection<U> getPrevEdges() {
        // default implementation for an *undirected* graph
        return getNextEdges();
    }

}
