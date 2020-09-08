package dev.mikit.atcoder.lib.graph;

import java.util.Collection;

public interface NodeLike<T extends NodeLike<T>> {

    int getIndex();

    Collection<T> getNextNodes();

    default Collection<T> getPrevNodes() {
        // default implementation for an *undirected* graph
        return getNextNodes();
    }

}
