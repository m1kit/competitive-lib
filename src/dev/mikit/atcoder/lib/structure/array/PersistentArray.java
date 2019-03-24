package dev.mikit.atcoder.lib.structure.array;

import dev.mikit.atcoder.lib.meta.Verified;
import dev.mikit.atcoder.lib.structure.TimeOutOfBoundsException;

import java.util.Arrays;
import java.util.function.UnaryOperator;

@Verified({
        "https://codeforces.com/contest/707/submission/51387090",
})
public class PersistentArray<T> {

    private static final int BASE = 4;
    private static final int MASK = (1 << BASE) - 1;

    private final int n;
    private final int height;
    private int lastUpdate = 0;
    private int[] roots;
    private int newElements = 0;
    private int[][] nodes;
    private T[] elements;

    public PersistentArray(T[] array, int initialTimeCapacity, int initialNodeCapacity) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Empty array is not supported");
        } else if (initialTimeCapacity < 1 || initialNodeCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be a positive number");
        }
        n = array.length;
        int log = 1;
        while (n > (1 << (log * BASE))) {
            log++;
        }
        height = log - 1;

        roots = new int[1 + initialTimeCapacity];
        nodes = new int[1 << BASE][nodes(height) + initialNodeCapacity * (height + 1)];
        elements = Arrays.copyOf(array, n + initialNodeCapacity);
        int i = 0;
        for (; i < nodes(height - 1); i++) {
            for (int j = 0; j < (1 << BASE); j++) {
                nodes[j][i] = (1 << BASE) * i + j + 1;  // 子ノードへのリンク
            }
        }
        int j = 0;
        for (; j < n; j++) {
            nodes[j & MASK][i + (j >> BASE)] = j;       // 実要素へのリンク
        }
        for (; i + (j >> BASE) < nodes(height); j++) {
            nodes[j & MASK][i + (j >> BASE)] = -1;      // 番兵
        }
        newElements = 0;
    }

    public PersistentArray(T[] array) {
        this(array, 1, 1);
    }

    public T get(int i, int t) {
        if (i < 0 || n <= i) {
            throw new ArrayIndexOutOfBoundsException("Index " + i + " is out of bounds");
        } else if (t < 0 || lastUpdate < t) {
            throw new TimeOutOfBoundsException("Time " + t + " is out of bounds");
        }
        int c = roots[t];
        for (int ss = BASE * height; ss >= 0; ss -= BASE) {
            c = nodes[(i >>> ss) & MASK][c];
        }
        return elements[c];
    }

    public T get(int i) {
        return get(i, lastUpdate);
    }

    private void growRoots(int minCapacity) {
        int capacity = roots.length - 1;
        while (capacity < minCapacity) {
            capacity <<= 1;
        }
        roots = Arrays.copyOf(roots, 1 + capacity);
    }

    private void growNodes(int minCapacity) {
        int capacity = elements.length - n;
        while (capacity < minCapacity) {
            capacity <<= 1;
        }
        for (int i = 0; i < (1 << BASE); i++) {
            nodes[i] = Arrays.copyOf(nodes[i], nodes(height) + capacity * (height + 1));
        }
        elements = Arrays.copyOf(elements, n + capacity);
    }

    public int set(int i, T x, int t) {
        return set(i, unused -> x, t, true);
    }

    public int set(int i, UnaryOperator<T> x, int t, boolean tick) {
        if (i < 0 || n <= i) {
            throw new ArrayIndexOutOfBoundsException("Index " + i + " is out of bounds");
        } else if (t < 0 || lastUpdate < t) {
            throw new TimeOutOfBoundsException("Time " + t + " is out of bounds");
        }
        if (n + newElements == elements.length) {
            growNodes(newElements + 1);
        }

        int c = roots[t];
        int d = nodes(height) + newElements * (height + 1);
        if (tick) {
            lastUpdate++;
            if (lastUpdate == roots.length) {
                growRoots(lastUpdate + 1);
            }
        }
        roots[lastUpdate] = d;
        for (int ss = BASE * height; ss > 0; ss -= BASE) {
            for (int j = 0; j < (1 << BASE); j++) {
                nodes[j][d] = nodes[j][c];
            }
            nodes[(i >> ss) & MASK][d] = ++d;
            c = nodes[(i >>> ss) & MASK][c];
        }
        for (int j = 0; j < (1 << BASE); j++) {
            nodes[j][d] = nodes[j][c];
        }
        elements[n + newElements] = x.apply(elements[nodes[i & MASK][d]]);
        nodes[i & MASK][d] = n + newElements++;
        return lastUpdate;
    }

    public int set(int i, T x) {
        return set(i, x, lastUpdate);
    }

    public int set(int i, T x, boolean tick) {
        return set(i, unused -> x, lastUpdate, tick);
    }

    public int set(int i, UnaryOperator<T> x) {
        return set(i, x, lastUpdate, true);
    }

    public int set(int t) {
        if (lastUpdate + 1 == roots.length) {
            growRoots(lastUpdate + 1);
        }
        roots[++lastUpdate] = roots[t];
        return lastUpdate;
    }

    public static int nodes(int h) {
        return ((1 << (BASE * (h + 1))) - 1) / ((1 << BASE) - 1);
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public T[] toArray(int t) {
        T[] a = Arrays.copyOf(elements, n);
        for (int i = 0; i < n; i++) {
            a[i] = get(i, t);
        }
        return a;
    }

    public int size() {
        return n;
    }
}
