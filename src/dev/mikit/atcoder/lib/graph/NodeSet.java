package dev.mikit.atcoder.lib.graph;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NodeSet<N, E extends Edge<N, E>> {

    final int n;
    final List<Node<N, E>> nodes;

    NodeSet(int n) {
        this.n = n;
        this.nodes = IntStream.range(0, n).mapToObj(Node<N, E>::new).collect(Collectors.toList());
    }

    public int size() {
        return n;
    }

    public Node<N, E> getNode(int index) {
        return nodes.get(index);
    }

    @Override
    public String toString() {
        return nodes.toString();
    }
}
