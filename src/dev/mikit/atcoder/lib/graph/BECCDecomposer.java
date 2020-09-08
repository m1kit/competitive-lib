package dev.mikit.atcoder.lib.graph;

import dev.mikit.atcoder.lib.meta.Verified;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Verified("https://judge.yosupo.jp/submission/21608")
public class BECCDecomposer {

    private BECCDecomposer() {
        throw new UnsupportedOperationException();
    }

    @SafeVarargs
    public static <T extends NodeLike<T>> List<? extends Collection<T>> decompose(T... nodes) {
        return decompose(Arrays.asList(nodes));
    }

    public static <T extends NodeLike<T>> List<? extends Collection<T>> decompose(GraphLike<T> graph) {
        return decompose(graph.getNodes());
    }

    public static <T extends NodeLike<T>> List<? extends Collection<T>> decompose(List<T> nodes) {
        int n = nodes.size(), allocated = 0;
        int[] ord = new int[n], low = new int[n];
        Arrays.fill(ord, -1);
        for (int i = 0; i < n; i++) {
            if (ord[i] != -1) continue;
            allocated = allocate(nodes.get(i), null, allocated, ord, low);
        }
        //System.out.println(Arrays.toString(ord));
        //System.out.println(Arrays.toString(low));
        int[] rev = new int[n];
        for (int i = 0; i < n; i++) rev[ord[i]] = i;
        List<List<T>> result = new ArrayList<>();
        for (int i : rev) {
            if (low[i] == -1) continue;
            List<T> group = new ArrayList<>();
            makeGroup(nodes.get(i), ord, low, group);
            result.add(group);
        }
        return result;
    }

    private static int allocate(NodeLike<?> node, NodeLike<?> from, int allocated, int[] ord, int[] low) {
        int now = node.getIndex(), count = 0;
        ord[now] = low[now] = allocated++;
        for (NodeLike<?> next : node.getNextNodes()) {
            int nxt = next.getIndex();
            if (ord[nxt] == -1) {
                allocated = allocate(next, node, allocated, ord, low);
                low[now] = Math.min(low[now], low[nxt]);
            } else if (next != from || 1 < ++count) {
                low[now] = Math.min(low[now], ord[nxt]);
            }
        }
        return allocated;
    }

    private static <T extends NodeLike<T>> void makeGroup(T node, int[] ord, int[] low, List<T> group) {
        int now = node.getIndex();
        //System.out.println("Visit " + now);
        group.add(node);
        low[now] = -1;
        for (T next : node.getNextNodes()) {
            int nxt = next.getIndex();
            if (low[nxt] == -1 || ord[now] < low[nxt]) continue;
            makeGroup(next, ord, low, group);
        }
    }
}
