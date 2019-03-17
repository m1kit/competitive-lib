package dev.mikit.atcoder.lib.graph;

import java.util.NoSuchElementException;

public class DoubleNodeSet<N, E> extends NodeSet<N, DoubleEdge<N, E>> implements DoubleGraph {
    public DoubleNodeSet(int n) {
        super(n);
    }

    @Override
    public void addDirectedEdge(int from, int to, double weight) {
        DoubleEdge<N, E> e = new DoubleEdge<>(nodes.get(from), nodes.get(to), weight);
        nodes.get(from).addEdge(e);
    }

    @Override
    public double getWeightBetween(int from, int to) {
        return nodes.get(from).getEdges().stream()
                .filter(e -> e.getTo() == nodes.get(to))
                .mapToDouble(DoubleEdge::getWeight)
                .findFirst().orElseThrow(NoSuchElementException::new);
    }
}