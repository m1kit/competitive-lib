package dev.mikit.atcoder.lib.structure;

public class IntMultiSet {

    private final int max;
    private final int[] count;
    private int size = 0;

    public IntMultiSet(int max) {
        this.max = max;
        this.count = new int[max];
    }

    public void add(int v) {
        if (count[v] == 0) size++;
        count[v]++;
    }

    public void remove(int v) {
        count[v]--;
        if (count[v] == 0) size--;
    }

    public int size() {
        return size;
    }

}
