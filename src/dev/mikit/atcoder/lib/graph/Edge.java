package dev.mikit.atcoder.lib.graph;

public class Edge<N, E extends Edge<N, E>> {

    private final Node<N, E> from;
    private final Node<N, E> to;
    private E meta;

    public Edge(Node<N, E> from, Node<N, E> to) {
        this.from = from;
        this.to = to;
    }

    public E getMeta() {
        return meta;
    }

    public void setMeta(E meta) {
        this.meta = meta;
    }

    public Node<N, E> getFrom() {
        return from;
    }

    public Node<N, E> getTo() {
        return to;
    }
}
