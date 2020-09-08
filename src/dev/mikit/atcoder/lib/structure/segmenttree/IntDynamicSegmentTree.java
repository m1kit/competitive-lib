package dev.mikit.atcoder.lib.structure.segmenttree;

import dev.mikit.atcoder.lib.meta.Verified;

import java.util.Arrays;
import java.util.Stack;
import java.util.function.LongBinaryOperator;
import java.util.function.LongPredicate;

@Verified({
        "https://yukicoder.me/submissions/489971",
})
public class IntDynamicSegmentTree {

    private final long lb, ub;
    private final LongBinaryOperator op, up; // op: 区間併合, up: 要素更新
    private final long zero;

    private int used, allocated;
    private int[] l, r;
    private long[] v;

    public IntDynamicSegmentTree(long lb, long ub, int cap, LongBinaryOperator op, long zero, LongBinaryOperator up) {
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
        this.v = new long[allocated];
        l[0] = r[0] = -1;
        v[0] = zero;
    }

    public IntDynamicSegmentTree(long n, int cap, LongBinaryOperator op, long zero, LongBinaryOperator up) {
        this(0, n == 1 ? 1 : Long.highestOneBit(n - 1) << 1, cap, op, zero, up);
    }

    public IntDynamicSegmentTree(long n, LongBinaryOperator op, long zero, LongBinaryOperator up) {
        this(n, 128, op, zero, up);
    }

    public IntDynamicSegmentTree(LongBinaryOperator op, long zero, LongBinaryOperator up) {
        this(Integer.MAX_VALUE, op, zero, up);
    }

    private int l(int i) {
        return i == -1 ? -1 : l[i];
    }

    private int r(int i) {
        return i == -1 ? -1 : r[i];
    }

    private long v(int i) {
        return i == -1 ? zero : v[i];
    }

    private void propagate(int i) {
        this.v[i] = op.applyAsLong(v(l(i)), v(r(i)));
    }

    private int createNode(long x) {
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

    private int update(long i, long x, int node, long lo, long hi) {
        if (node == -1) node = createNode(zero);
        if (hi - lo == 1) {
            v[node] = up.applyAsLong(v[node], x);
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

    public void update(long i, long x) {
        update(i, x, 0, lb, ub);
    }

    private long query(long l, long r, int node, long lo, long hi) {
        if (l <= lo && hi <= r) return v(node);
        if (r <= lo || hi <= l || node == -1) return zero;
        long mid = (lo + hi) >> 1;
        return op.applyAsLong(query(l, r, l(node), lo, mid), query(l, r, r(node), mid, hi));
    }

    public long query(long l, long r) {
        if (l < lb || ub < r || l > r) throw new IndexOutOfBoundsException();
        return query(l, r, 0, lb, ub);
    }

    private long findR(long l, LongPredicate p, long acc, int node, long lo, long hi) {
        if (hi - lo == 1) {
            acc = op.applyAsLong(acc, v(node));
            return p.test(acc) ? hi : -1;
        }
        long mid = (lo + hi) >> 1;
        if (mid <= l) return findR(l, p, acc, r(node), mid, hi);
        return 0;
    }

    public long findR(long l, LongPredicate p) {
        return findR(l, p, zero, 0, lb, ub);
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
