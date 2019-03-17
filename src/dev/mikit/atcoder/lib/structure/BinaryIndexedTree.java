package dev.mikit.atcoder.lib.structure;

import dev.mikit.atcoder.lib.util.Reflection;

public class BinaryIndexedTree<T> {

    private final int n;
    private final T[] tree;

    public BinaryIndexedTree(T[] array) {
        n = array.length;
        tree = Reflection.newInstance(Reflection.getComponentClass(array), n + 1);
    }
}
