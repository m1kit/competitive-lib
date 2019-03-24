package dev.mikit.atcoder.lib.structure.unionfindtree;

import dev.mikit.atcoder.lib.geo.Vec2i;
import dev.mikit.atcoder.lib.meta.Verified;

import java.util.Arrays;

@Verified({
        "https://atcoder.jp/contests/agc002/submissions/4617433",
        "https://atcoder.jp/contests/code-thanks-festival-2017/submissions/4617418",
})
public class SemiPersistentUnionFind {

    private int now = 0;
    private final int[] node;
    private final int[] time;

    private final int[] updateCount;
    private final int[][] updateTime;
    private final int[][] updateSize;

    public SemiPersistentUnionFind(int n, int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be a positive number");
        }
        node = new int[n];
        time = new int[n];
        Arrays.fill(time, Integer.MAX_VALUE);

        updateCount = new int[n];
        Arrays.fill(updateCount, 1);
        updateTime = new int[n][];
        updateSize = new int[n][];
        for (int i = 0; i < n; i++) {
            updateTime[i] = new int[initialCapacity];
            updateSize[i] = new int[initialCapacity];
            updateSize[i][0] = 1;
        }
    }

    public SemiPersistentUnionFind(int n) {
        this(n, 4);
    }

    public int find(int i, int t) {
        while (time[i] <= t) {
            i = node[i];
        }
        return i;
    }

    public int find(int i) {
        return find(i, now);
    }

    public boolean isSame(int a, int b, int t) {
        return find(a, t) == find(b, t);
    }

    public int union(int a, int b) {
        now++;
        a = find(a, now);
        b = find(b, now);
        if (a == b) {
            return now;
        }

        if (node[a] < node[b]) {
            int t = a;
            a = b;
            b = t;
        }

        if (updateCount[a] >= updateTime[a].length) {
            updateTime[a] = Arrays.copyOf(updateTime[a], updateTime[a].length << 1);
            updateSize[a] = Arrays.copyOf(updateSize[a], updateSize[a].length << 1);
        }
        updateTime[a][updateCount[a]] = now;
        updateSize[a][updateCount[a]] = updateSize[a][updateCount[a] - 1] + updateSize[b][updateCount[b] - 1];
        updateCount[a]++;

        if (node[a] == node[b]) {
            node[a]++; // increase rank
        }
        node[b] = a;
        time[b] = now;
        return now;
    }

    public Vec2i findWithSize(int i, int t) {
        i = find(i, t);
        int ok = 0, ng = updateCount[i];
        while (ng - ok > 1) {
            int mid = (ok + ng) / 2;
            if (updateTime[i][mid] <= t) {
                ok = mid;
            } else {
                ng = mid;
            }
        }
        return new Vec2i(i, updateSize[i][ok]);
    }

    public int size(int i, int t) {
        return findWithSize(i, t).y;
    }

    public int size(int i) {
        i = find(i);
        return updateSize[i][updateCount[i] - 1];
    }


}
