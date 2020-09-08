package dev.mikit.atcoder.lib.graph;

import dev.mikit.atcoder.lib.meta.Verified;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Verified("https://judge.yosupo.jp/submission/21542")
public class SCCDecomposer {

    private SCCDecomposer() {
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
        boolean[] visited = new boolean[n];
        int[] counter = new int[n];
        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            allocated = allocate(nodes.get(i), allocated, counter, visited);
        }
        int[] order = new int[n];
        for (int i = 0; i < n; i++) order[n - counter[i] - 1] = i;
        List<List<T>> result = new ArrayList<>();
        for (int i : order) {
            if (counter[i] == -1) continue;
            List<T> group = new ArrayList<>();
            makeGroup(nodes.get(i), counter, group);
            result.add(group);
        }
        return result;
    }

    private static int allocate(NodeLike<?> node, int allocated, int[] counter, boolean[] visited) {
        visited[node.getIndex()] = true;
        for (NodeLike<?> next : node.getNextNodes()) {
            if (visited[next.getIndex()]) continue;
            allocated = allocate(next, allocated, counter, visited);
        }
        counter[node.getIndex()] = allocated;
        return allocated + 1;
    }

    private static<T extends NodeLike<T>>  void makeGroup(T node, int[] counter, List<T> group) {
        counter[node.getIndex()] = -1;
        group.add(node);
        for (T next : node.getPrevNodes()) {
            if (counter[next.getIndex()] == -1) continue;
            makeGroup(next, counter, group);
        }
    }

}
