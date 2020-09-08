package dev.mikit.atcoder.lib.structure.bst;

import java.util.Comparator;

class RedBlackTree<N extends RedBlackTree.Node<N, K>, K> {

    private final Comparator<K> comparator;
    private N root;

    RedBlackTree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    static class Node<N extends Node<N, K>, K> {
        boolean red = true;
        final K key;
        N l, r, p;

        Node(K key) {
            this.key = key;
        }
    }

    void insert(N e) {
        root = insert(root, e);
        root.red = false;
    }

    private N insert(N t, N e) {
        if (t == null) return e;
        int cmp = comparator.compare(e.key, t.key);
        if (cmp < 0) {
            t.l = insert(t.l, e);
            return balance(t);
        }
        if (cmp > 0) {
            t.r = insert(t.r, e);
            return balance(t);
        }
        e.l = t.l;
        e.r = t.r;
        e.red = t.red;
        return e;
    }

    void delete(K key) {
        root = delete(root, key);
        if (root != null) root.red = false;
    }

    private N delete(N t, K key) {
        if (t == null) return null;
        int cmp = comparator.compare(key, t.key);
        if (cmp < 0) {
            t.l = delete(t.l, key);
            return balanceL(t);
        }
        if (cmp > 0) {
            t.r = delete(t.r, key);
            return balanceR(t);
        }
        if (t.l == null) return t.r;
        t.l = deleteMax(t.l);
        //t.key =
        return null;
    }

    private N rotateL(N v) {
        N u = v.r;
        N t2 = u.l;
        u.l = v;
        v.r = t2;
        return u;
    }

    private N rotateR(N u) {
        N v = u.l;
        N t2 = v.r;
        v.r = u;
        u.l = t2;
        return v;
    }

    private N rotateLR(N t) {
        t.l = rotateL(t.l);
        return rotateR(t);
    }

    private N rotateRL(N t) {
        t.r = rotateR(t.r);
        return rotateL(t);
    }

    private N balance(N t) {
        if (t.red) return t;
        if (t.l != null && t.l.red) {
            if (t.l.l != null && t.l.l.red) {
                t = rotateR(t);
                t.l.red = false;
            } else if (t.l.r != null && t.l.r.red) {
                t = rotateLR(t);
                t.l.red = false;
            }
        } else if (t.r != null && t.r.red) {
            if (t.r.l != null && t.r.l.red) {
                t = rotateRL(t);
                t.r.red = false;
            } else if (t.r.r != null && t.r.r.red) {
                t = rotateL(t);
                t.r.red = false;
            }
        }
        return t;
    }

    private N balanceL(N t) {
        return null;
    }

    private N balanceR(N t) {
        return null;
    }

    private N deleteMax(N t) {
        return null;
    }

}
