package dev.mikit.atcoder.lib.misc;

import dev.mikit.atcoder.lib.graph.NodeLike;
import dev.mikit.atcoder.lib.graph.SCCDecomposer;
import dev.mikit.atcoder.lib.meta.Verified;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Verified("https://judge.yosupo.jp/submission/21558")
public class TwoSat {

    private final int n;
    private final Node[] nodes;

    public TwoSat(int n) {
        this.n = n;
        this.nodes = new Node[2 * n];
        for (int i = 0; i < 2 * n; i++) nodes[i] = new Node(i);
    }

    private int encodeIndex(int x) {
        if (0 < x) {
            if (n < x) throw new IllegalArgumentException();
            return x - 1;
        } else if (x < 0) {
            if (x < -n) throw new IllegalArgumentException();
            return n - x - 1;
        } else throw new IllegalArgumentException();
    }

    public void implies(int x, int y) {
        x = encodeIndex(x);
        y = encodeIndex(y);
        nodes[x].next.add(nodes[y]);
        nodes[y].prev.add(nodes[x]);
    }

    public void or(int x, int y) {
        implies(-x, y);
        implies(-y, x);
    }

    public boolean[] solve() {
        List<? extends Collection<Node>> groups = SCCDecomposer.decompose(nodes);
        int m = groups.size();
        int[] ord = new int[2 * n];
        for (int i = 0; i < m; i++) {
            for (Node node : groups.get(i)) ord[node.index] = i;
        }
        //System.out.println(m + "/" + Arrays.toString(ord));
        boolean[] result = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (ord[i] == ord[n + i]) return null;
            result[i] = ord[i] > ord[n + i];
        }
        return result;
    }

    private static class Node implements NodeLike<Node> {
        final int index;
        final List<Node> next = new ArrayList<>();
        final List<Node> prev = new ArrayList<>();

        Node(int index) {
            this.index = index;
        }

        @Override
        public int getIndex() {
            return index;
        }

        @Override
        public Collection<Node> getNextNodes() {
            return next;
        }

        @Override
        public Collection<Node> getPrevNodes() {
            return prev;
        }
    }

}
