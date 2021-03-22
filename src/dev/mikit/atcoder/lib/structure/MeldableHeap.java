package dev.mikit.atcoder.lib.structure;

import java.util.Random;

/**
 * Class MedlableHeap
 **/
public class MeldableHeap {
    private static final Random rand = new Random();
    private int n;
    private Node root;

    public MeldableHeap() {
        root = null;
        n = 0;
    }

    public int size() {
        return n;
    }

    public void offer(int x) {
        Node u = new Node(x);
        root = meld(u, root);
        n++;
    }

    /**
     * function to remove an element
     **/
    public int poll() {
        int x = root.x;
        root = meld(root.left, root.right);
        n--;
        return x;
    }

    /**
     * function to merge two nodes
     **/
    private static Node meld(Node q1, Node q2) {
        if (q1 == null) return q2;
        if (q2 == null) return q1;
        if (q2.x < q1.x) return meld(q2, q1);

        if (rand.nextBoolean()) {
            q1.left = meld(q1.left, q2);
        } else {
            q1.right = meld(q1.right, q2);
        }
        return q1;
    }

    public MeldableHeap meld(MeldableHeap heap) {
        this.root = meld(this.root, heap.root);
        n += heap.n;
        return this;
    }

    public void clear() {
        root = null;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    private static class Node {
        final int x;
        Node left, right;

        Node(int x) {
            this.x = x;
        }
    }
}
