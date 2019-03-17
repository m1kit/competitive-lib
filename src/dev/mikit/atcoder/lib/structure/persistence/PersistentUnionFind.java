package dev.mikit.atcoder.lib.structure.persistence;

import dev.mikit.atcoder.lib.geo.Vec2i;
import dev.mikit.atcoder.lib.meta.Verified;

import java.util.Arrays;

@Verified({
        "https://atcoder.jp/contests/agc002/submissions/4615270",
})
public class PersistentUnionFind {

    private final PersistentIntArray array;

    public PersistentUnionFind(int n, int initialTimeCapacity, int initialNodeCapacity) {
        int[] init = new int[n];
        Arrays.fill(init, -1);
        this.array = new PersistentIntArray(init, initialTimeCapacity, initialNodeCapacity);
    }

    public PersistentUnionFind(int n) {
        int[] init = new int[n];
        Arrays.fill(init, -1);
        this.array = new PersistentIntArray(init);
    }

    public int find(int i, int t) {
        int c = array.get(i, t);
        while (c >= 0) {
            i = c;
            c = array.get(i, t);
        }
        return i;
    }

    public int find(int a) {
        return find(a, getLastUpdate());
    }

    public int size(int i, int t) {
        int c = array.get(i, t);
        while (c >= 0) {
            i = c;
            c = array.get(i, t);
        }
        return -c;
    }

    public int size(int i) {
        return size(i, getLastUpdate());
    }

    public Vec2i findWithSize(int i, int t) {
        int c = array.get(i, t);
        while (c >= 0) {
            i = c;
            c = array.get(i, t);
        }
        return new Vec2i(i, -c);
    }

    public boolean union(int a, int b, int t) {
        Vec2i av = findWithSize(a, t);
        Vec2i bv = findWithSize(b, t);
        if (av.x == bv.x) {
            array.set(array.getLastUpdate());
            return false;
        }
        Vec2i parent, child;
        if (av.y < bv.y) {
            parent = bv;
            child = av;
        } else {
            parent = av;
            child = bv;
        }
        array.set(parent.x, v -> -parent.y - child.y, t, true);
        array.set(child.x, parent.x, false);
        return true;
    }

    public boolean union(int a, int b) {
        return union(a, b, getLastUpdate());
    }

    public int getLastUpdate() {
        return array.getLastUpdate();
    }

    public void debug(int t) {
        System.out.println(Arrays.toString(array.toArray(t)));
    }
}
