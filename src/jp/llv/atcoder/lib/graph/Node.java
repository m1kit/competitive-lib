package jp.llv.atcoder.lib.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Node<N, E extends Edge<N, E>> {

    private final int index;
    private final Set<E> edges = new HashSet<>();
    private N meta;

    public Node(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public N getMeta() {
        return meta;
    }

    public void setMeta(N meta) {
        this.meta = meta;
    }

    void addEdge(E e) {
        edges.add(e);
    }

    public E getEdgeTo(Node<N, E> node) {
        for (E e : edges) {
            if (e.getTo() == node) {
                return e;
            }
        }
        return null;
    }

    public Set<E> getEdges() {
        return edges;
    }

    public List<Node<N, E>> resolvePathTo(Node<N, E> dest) {
        Set<Node<N, E>> walked = new HashSet<>();
        walked.add(this);
        return resolvePathTo(dest, walked);
    }

    private LinkedList<Node<N, E>> resolvePathTo(Node<N, E> dest, Set<Node<N, E>> walked) {
        LinkedList<Node<N, E>> res = null;
        if (dest == this) {
            res = new LinkedList<>();
            res.add(this);
        } else {
            for (E e : edges) {
                if (!walked.contains(e.getTo())) {
                    walked.add(e.getTo());
                    if ((res = e.getTo().resolvePathTo(dest, walked)) != null) {
                        res.addFirst(this);
                        break;
                    }
                }
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return index + "(" + meta + ")";
    }
}
