package dev.mikit.atcoder.lib.graph;

public class IntEdge<N, E> extends Edge<N, IntEdge<N, E>> {

    private final long w;

    public IntEdge(Node<N, IntEdge<N, E>> from, Node<N, IntEdge<N, E>> to, long w) {
        super(from, to);
        this.w = w;
    }

    public long getWeight() {
        return w;
    }
}
