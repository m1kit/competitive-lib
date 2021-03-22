package dev.mikit.atcoder.lib.structure.bst;

import java.util.ArrayList;

public class AVLTree<K extends Comparable<? super K>, V> { // K:キーの型,V:値の型

    private final Node nil;
    private Node root;

    public AVLTree() {
        this.nil = new Node();
        this.root = nil;
    }

    // 左右の部分木の高さの差を返す。左が高いと正、右が高いと負
    private int bias(Node t) {
        return t.lst.height - t.rst.height;
    }

    // 左右の部分木の高さから、その木の高さを計算して修正する
    private void updateHeight(Node t) {
        t.height = 1 + Math.max(t.lst.height, t.rst.height);
    }

    private void replace(Node u, Node v) {
        Node p = u.parent;
        if (p.lst == u) p.lst = v;
        else p.rst = v;
        v.parent = p;
    }

    private Node rotateL(Node v) {
        Node u = v.rst;
        replace(v, u);
        v.rst = u.lst;
        u.lst.parent = v;
        u.lst = v;
        v.parent = u;
        updateHeight(u.lst);
        updateHeight(u);
        return u;
    }

    private Node rotateR(Node u) {
        Node v = u.lst;
        replace(u, v);
        u.lst = v.rst;
        v.rst.parent = u;
        v.rst = u;
        u.parent = v;
        updateHeight(v.rst);
        updateHeight(v);
        return v;
    }

    private Node rotateLR(Node t) {
        rotateL(t.lst);
        return rotateR(t);
    }

    private Node rotateRL(Node t) {
        rotateR(t.rst);
        return rotateL(t);
    }

    private void balance(boolean insert, Node t) {
        while (t.parent != nil) {
            Node u = t.parent;
            final int h = u.height;
            if ((u.lst == t) == insert) {
                if (bias(u) == 2) {
                    u = bias(u.lst) >= 0 ? rotateR(u) : rotateLR(u);
                } else updateHeight(u);
            } else {
                if (bias(u) == -2) {
                    u = bias(u.rst) <= 0 ? rotateL(u) : rotateRL(u);
                } else updateHeight(u);
            }
            if (h == u.height) break;
            t = u;
        }
    }

    public void insert(K key, V x) {
        Node p = nil, t = root;
        boolean right = false;
        while (t != nil) { // key に対応するノードが無いか検索する
            int cmp = key.compareTo(t.key);
            if (cmp < 0) {
                p = t;
                t = t.lst;
                right = false;
            } else if (cmp > 0) {
                p = t;
                t = t.rst;
                right = true;
            } else {
                t.value = x;
                return;
            }
        }
        if (!right) {
            p.lst = new Node(key, x, p);
            balance(true, p.lst);
        } else {
            p.rst = new Node(key, x, p);
            balance(true, p.rst);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // delete(削除)
    ///////////////////////////////////////////////////////////////////////////

    // 木からキー key のノードを削除する
    public void delete(K key) {
        Node t = root;
        while (t != nil) {
            int cmp = key.compareTo(t.key);
            if (cmp == 0) {
                if (t.lst == nil) {
                    replace(t, t.rst); // 右部分木を昇格させる
                    balance(false, t.rst);
                } else {
                    Node m = max(t.lst); // 左部分木の最大値ノードを取得
                    t.key = m.key; // 削除するノードのキーを左部分木の最大値に
                    t.value = m.value;
                    replace(m, m.lst); // 左部分木を昇格させる
                    balance(false, m.lst);
                }
                return;
            }
            t = cmp < 0 ? t.lst : t.rst;
        }
    }

    // 部分木 t の最大値ノードを返す
    private Node max(Node t) {
        while (t.rst != nil) t = t.rst;
        return t;
    }

    // 部分木 t の最小値ノードを返す
    private Node min(Node t) {
        while (t.lst != nil) t = t.lst;
        return t;
    }

    ///////////////////////////////////////////////////////////////////////////
    // member(検索)等
    ///////////////////////////////////////////////////////////////////////////

    // キーの検索。ヒットすれば true、しなければ false
    public boolean member(K key) {
        Node t = root;
        while (t != nil) {
            if (key.compareTo(t.key) < 0) t = t.lst;
            else if (key.compareTo(t.key) > 0) t = t.rst;
            else return true;
        }
        return false;
    }

    // キーから値を得る。キーがヒットしない場合は null を返す
    public V lookup(K key) {
        Node t = root;
        while (t != nil) {
            if (key.compareTo(t.key) < 0) t = t.lst;
            else if (key.compareTo(t.key) > 0) t = t.rst;
            else return t.value;
        }
        return null;
    }

    // マップが空なら true、空でないなら false
    public boolean isEmpty() {
        return root == nil;
    }

    // マップを空にする
    public void clear() {
        root = nil;
    }

    // キーのリスト
    public ArrayList<K> keys() {
        ArrayList<K> al = new ArrayList<K>();
        keys(root, al);
        return al;
    }

    // 値のリスト
    public ArrayList<V> values() {
        ArrayList<V> al = new ArrayList<V>();
        values(root, al);
        return al;
    }

    // マップのサイズ
    public int size() {
        return keys().size();
    }

    // キーの最小値
    public K min() {
        return min(root).key;
    }

    // キーの最大値
    public K max() {
        return max(root).key;
    }

    private void keys(Node t, ArrayList<K> al) {
        if (t != nil) {
            keys(t.lst, al);
            al.add(t.key);
            keys(t.rst, al);
        }
    }

    private void values(Node t, ArrayList<V> al) {
        if (t != nil) {
            values(t.lst, al);
            al.add(t.value);
            values(t.rst, al);
        }
    }

    private class Node { // ノードの型
        int height;      // その部分木の高さ
        K key;           // そのノードのキー
        V value;         // そのノードの値
        Node parent;         // 親ノード
        Node lst = nil;  // 左部分木
        Node rst = nil;  // 右部分木

        Node() {
            height = 0;
            key = null;
            value = null;
            parent = null;
        }

        Node(K key, V x, Node pn) {
            this.height = 1;
            this.key = key;
            this.value = x;
            this.parent = pn;
        }
    }


}
