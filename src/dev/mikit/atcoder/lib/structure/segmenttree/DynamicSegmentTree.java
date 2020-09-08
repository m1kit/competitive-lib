package dev.mikit.atcoder.lib.structure.segmenttree;

import dev.mikit.atcoder.lib.util.Reflection;

import java.util.Arrays;
import java.util.function.BinaryOperator;

public class DynamicSegmentTree<T> {
    private final long lb, ub;
    private final BinaryOperator<T> op, up; // op: 区間併合, up: 要素更新
    private final T zero;

    private int used, allocated;
    private int[] l, r;
    private T[] v;

    public DynamicSegmentTree(Class<T> component, long lb, long ub, int cap, BinaryOperator<T> op, T zero, BinaryOperator<T> up) {
        if (lb > ub) throw new IllegalArgumentException();
        this.lb = lb;
        this.ub = ub;
        this.op = op;
        this.up = up;
        this.zero = zero;
        this.used = 1;
        int height = 64 - Long.numberOfLeadingZeros(ub - lb - 1);
        this.allocated = Math.max(cap * height, 1);
        this.l = new int[allocated];
        this.r = new int[allocated];
        this.v = Reflection.newInstance(component, allocated);
        l[0] = r[0] = -1;
        v[0] = zero;
    }

    public DynamicSegmentTree(Class<T> component, long n, int cap, BinaryOperator<T> op, T zero, BinaryOperator<T> up) {
        this(component, 0, n == 1 ? 1 : Long.highestOneBit(n - 1) << 1, cap, op, zero, up);
    }

    public DynamicSegmentTree(Class<T> component, long n, BinaryOperator<T> op, T zero, BinaryOperator<T> up) {
        this(component, n, 128, op, zero, up);
    }

    public DynamicSegmentTree(Class<T> component, BinaryOperator<T> op, T zero, BinaryOperator<T> up) {
        this(component, Integer.MAX_VALUE, op, zero, up);
    }

    private int l(int i) {
        return i == -1 ? -1 : l[i];
    }

    private int r(int i) {
        return i == -1 ? -1 : r[i];
    }

    private T v(int i) {
        return i == -1 ? zero : v[i];
    }

    private void propagate(int i) {
        this.v[i] = op.apply(v(l(i)), v(r(i)));
    }

    private int createNode(T x) {
        if (used == allocated) {
            allocated <<= 1;
            l = Arrays.copyOf(l, allocated);
            r = Arrays.copyOf(r, allocated);
            v = Arrays.copyOf(v, allocated);
        }
        this.l[used] = this.r[used] = -1;
        this.v[used] = x;
        return used++;
    }

    private int update(long i, T x, int node, long lo, long hi) {
        if (node == -1) node = createNode(zero);
        if (hi - lo == 1) {
            v[node] = up.apply(v[node], x);
            return node;
        } else {
            long mid = (lo + hi) >> 1;
            if (i < mid) {
                l[node] = update(i, x, l[node], lo, mid);
            } else {
                r[node] = update(i, x, r[node], mid, hi);
            }
            propagate(node);
        }
        return node;
    }

    public void update(long i, T x) {
        update(i, x, 0, lb, ub);
    }

    private T query(long l, long r, int node, long lo, long hi) {
        if (l <= lo && hi <= r) return v(node);
        if (r <= lo || hi <= l || node == -1) return zero;
        long mid = (lo + hi) >> 1;
        return op.apply(query(l, r, l(node), lo, mid), query(l, r, r(node), mid, hi));
    }

    public T query(long l, long r) {
        if (l < lb || ub < r || l > r) throw new IndexOutOfBoundsException();
        return query(l, r, 0, lb, ub);
    }

    @Override
    public String toString() {
        return "IntDynamicSegmentTree(" +
                "range = [" + lb +
                ", " + ub +
                "), zero = " + zero +
                ", used " + used +
                " / " + allocated +
                ")\nl = " + Arrays.toString(Arrays.copyOf(l, used)) +
                "\nr = " + Arrays.toString(Arrays.copyOf(r, used)) +
                "\nv = " + Arrays.toString(Arrays.copyOf(v, used)) +
                '\n';
    }
}
