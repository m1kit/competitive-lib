package dev.mikit.atcoder.lib.graph;

import java.util.Iterator;
import java.util.List;

@FunctionalInterface
public interface GraphLike<T extends NodeLike<T>> extends Iterable<T> {

    List<T> getNodes();

    @Override
    default Iterator<T> iterator() {
        return getNodes().iterator();
    }

    default int size() {
        return getNodes().size();
    }

}
