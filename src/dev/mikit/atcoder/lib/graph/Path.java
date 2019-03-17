package dev.mikit.atcoder.lib.graph;

import java.util.List;

public class Path<N, E extends Edge<N, E>> {

    private final List<Node<N, E>> path;

    public Path(List<Node<N, E>> path) {
        this.path = path;
    }

    public List<Node<N, E>> getPath() {
        return path;
    }
}
