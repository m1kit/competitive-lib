package dev.mikit.atcoder.lib.graph;

public class DoubleEdge<N, E> extends Edge<N, DoubleEdge<N, E>> {
    private final double w;

    public DoubleEdge(Node<N, DoubleEdge<N, E>> from, Node<N, DoubleEdge<N, E>> to, double w) {
        super(from, to);
        this.w = w;
    }

    public double getWeight() {
        return w;
    }
}
