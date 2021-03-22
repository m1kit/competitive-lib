package dev.mikit.atcoder.lib.graph;

import dev.mikit.atcoder.lib.meta.Verified;
import org.w3c.dom.Node;

import java.util.*;

@Verified("https://judge.yosupo.jp/submission/21542")
public class SCCDecomposer {
    private SCCDecomposer() {
    }

    public static <T extends NodeLike<T>> List<? extends Collection<T>> decompose2(List<T> nodes) {
        int n = nodes.size();
        int[][] g = new int[n][], ig = new int[n][];
        for (int i = 0; i < n; i++) {
            g[i] = nodes.get(i).getNextNodes().stream().mapToInt(NodeLike::getIndex).toArray();
            ig[i] = nodes.get(i).getPrevNodes().stream().mapToInt(NodeLike::getIndex).toArray();
        }

        boolean[] visited = new boolean[n];
        int[] route = new int[n], stack = new int[n], context = new int[n];
        int rc = 0, sp = -1;
        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            context[++sp] = 0;
            stack[sp] = i; // push (i, 0)
            while (sp >= 0) {
                int cur = stack[sp];
                visited[cur] = true;
                while (context[sp] < g[cur].length && visited[g[cur][context[sp]]]) context[sp]++;
                if (context[sp] == g[cur].length) {
                    route[rc++] = cur;
                    sp--; // pop
                } else {
                    int next = g[cur][context[sp]];
                    stack[++sp] = next; // push (next, 0)
                    context[sp] = 0;
                }
            }
        }

        List<List<T>> res = new ArrayList<>();
        /*
        int qbegin = 0, qend = 0;
        int[] q = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            if (!visited[route[i]]) continue;
            List<T> group = new ArrayList<>();
            res.add(group);
            q[qend++] = route[i];
            visited[route[i]] = false;
            while (qbegin < qend) {
                int cur = q[qbegin++];
                group.add(nodes.get(cur));
                for (int k : ig[cur]) {
                    if (!visited[k]) continue;
                    q[qend++] = k;
                    visited[k] = false;
                }
            }
        }
         */
        return res;
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

    private static <T extends NodeLike<T>> void makeGroup(T node, int[] counter, List<T> group) {
        counter[node.getIndex()] = -1;
        group.add(node);
        for (T next : node.getPrevNodes()) {
            if (counter[next.getIndex()] == -1) continue;
            makeGroup(next, counter, group);
        }
    }

}
