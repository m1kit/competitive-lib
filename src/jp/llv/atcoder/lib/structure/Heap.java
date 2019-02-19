package jp.llv.atcoder.lib.structure;

import jp.llv.atcoder.lib.meta.Verified;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.IntFunction;

@Verified({
        "https://codeforces.com/contest/1114/submission/49740827"
})
public class Heap<T> {

    private static final int DEFAULT_INITIAL_CAPACITY = 32;

    private int size = 0;
    private final Comparator<? super T> comparator;
    transient T[] tree;


    public Heap(IntFunction<T[]> clazz, Comparator<? super T> comparator) {
        this(clazz, comparator, DEFAULT_INITIAL_CAPACITY);
    }

    public Heap(IntFunction<T[]> clazz, Comparator<? super T> comparator, int initialCapacity) {
        this.tree = clazz.apply(initialCapacity);
        this.comparator = comparator;
    }

    private void grow(int minCapacity) {
        int capacity = tree.length;
        while (capacity < minCapacity) {
            capacity <<= 1;
        }
        tree = Arrays.copyOf(tree, capacity);
    }

    private void siftUp(int k, T x) {
        while (k > 0) {
            int p = (k - 1) / 2;
            if (comparator.compare(x, tree[p]) >= 0) {
                break;
            }
            tree[k] = tree[p];
            k = p;
        }
        tree[k] = x;
    }

    private void siftDown(int k, T x) {
        int half = size / 2;
        while (k < half) {
            int cmp = 2 * k + 1;
            int right = cmp + 1;
            if (right < size && comparator.compare(tree[cmp], tree[right]) > 0) {
                cmp = right;
            }
            if (comparator.compare(x, tree[cmp]) <= 0) {
                break;
            }
            tree[k] = tree[cmp];
            k = cmp;
        }
        tree[k] = x;
    }

    public void offer(T t) {
        Objects.requireNonNull(t);
        if (size >= tree.length) {
            grow(size + 1);
        }
        if (size == 0) {
            tree[0] = t;
        } else {
            siftUp(size, t);
        }
        size++;
    }

    public T peek() {
        return size == 0 ? null : tree[0];
    }

    public T poll() {
        if (size == 0) {
            return null;
        }
        size--;
        T result = tree[0];
        T x = tree[size];
        tree[size] = null;
        if (size != 0) {
            siftDown(0, x);
        }
        return result;
    }

    private T removeAt(int i) {
        size--;
        if (size == i) {
            tree[i] = null;
        } else {
            T moved = tree[size];
            tree[size] = null;
            siftDown(i, moved);
            if (tree[i] == moved) {
                siftUp(i, moved);
                if (tree[i] != moved) {
                    return moved;
                }
            }
        }
        return null;
    }

   private int indexOf(T t) {
        for (int i = 0; i < size; i++) {
            if (tree[i].equals(t)) {
                return i;
            }
        }
        return -1;
    }

    public boolean remove(T t) {
        int i = indexOf(t);
        if (i == -1) {
            return false;
        }
        removeAt(i);
        return true;
    }

    public boolean contains(T t) {
        return indexOf(t) != -1;
    }

    public T[] toArray() {
        return Arrays.copyOf(tree, size);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            tree[i] = null;
        }
        size = 0;
    }

    public Comparator<? super T> comparator() {
        return comparator;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(tree[i]).append(' ');
        }
        sb.setLength(sb.length() - 1);
        return sb.append("]").toString();
    }
}