package jp.llv.atcoder.lib.graph;

import java.util.NoSuchElementException;

public final class IntNodeSet<N, E> extends NodeSet<N, IntEdge<N, E>> implements IntGraph {
    public IntNodeSet(int n) {
        super(n);
    }

    @Override
    public void addDirectedEdge(int from, int to, long weight) {
        IntEdge<N, E> e = new IntEdge<>(nodes.get(from), nodes.get(to), weight);
        nodes.get(from).addEdge(e);
    }

    @Override
    public long getWeightBetween(int from, int to) {
        return nodes.get(from).getEdges().stream()
                .filter(e -> e.getTo() == nodes.get(to))
                .mapToLong(IntEdge::getWeight)
                .findFirst().orElseThrow(NoSuchElementException::new);
    }
}
