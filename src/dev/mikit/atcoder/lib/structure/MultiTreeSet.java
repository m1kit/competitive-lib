package dev.mikit.atcoder.lib.structure;

import java.util.*;

public class MultiTreeSet<T> {

    private final TreeMap<T, Long> tree;

    public MultiTreeSet() {
        this.tree = new TreeMap<>((Comparator<T>) Comparator.naturalOrder());
    }

    public MultiTreeSet(Comparator<T> cmp) {
        this.tree = new TreeMap<>(cmp);
    }

    public void add(T entry) {
        tree.merge(entry, 1L, Long::sum);
    }

    public void add(T entry, long count) {
        tree.merge(entry, count, Long::sum);
    }

    public void remove(T entry) {
        Long v = tree.get(entry);
        if (v == null) throw new RuntimeException("Remove non-existing entry " + entry);
        else if (v == 1) tree.remove(entry);
        else tree.put(entry, v - 1L);
    }

    public void remove(T entry, long count) {
        Long v = tree.get(entry);
        if (v == null || v < count) throw new RuntimeException("Remove non-existing entry " + entry);
        else if (v == count) tree.remove(entry);
        else tree.put(entry, v - count);
    }

    public T peek() {
        Map.Entry<T, Long> entry = tree.firstEntry();
        return entry == null ? null : entry.getKey();
    }

    public T peek2() {
        Map.Entry<T, Long> entry = tree.firstEntry();
        if (entry == null) return null;
        if (entry.getValue() > 1) return entry.getKey();
        entry = tree.higherEntry(entry.getKey());
        return entry == null ? null : entry.getKey();
    }

    public int size() {
        return tree.size();
    }

}
