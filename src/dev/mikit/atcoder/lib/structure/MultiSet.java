package dev.mikit.atcoder.lib.structure;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class MultiSet<T> {

    private final HashMap<T, Long> tree;

    public MultiSet() {
        this.tree = new HashMap<>();
    }

    /*public MultiSet(Comparator<T> cmp) {
        this.tree = new HashMap<>(cmp);
    }*/

    public void add(T entry) {
        tree.merge(entry, 1L, Long::sum);
    }

    public void remove(T entry) {
        Long v = tree.get(entry);
        if (v == null) throw new RuntimeException("Remove non-existing entry " + entry);
        else if (v == 1) tree.remove(entry);
        else tree.put(entry, v - 1L);
    }

    public int size() {
        return tree.size();
    }

}
