package dev.mikit.atcoder.lib.misc;

import dev.mikit.atcoder.lib.sort.IntroSort;

import java.util.*;

public class OrderedCompressor implements Iterable<Long> {

    private final Map<Long, Integer> natural = new HashMap<>();
    private final long[] reverse;

    public OrderedCompressor(long... values) {
        int n = values.length, counter = 0;
        long[] tmp = new long[n];
        IntroSort.sort(values);
        for (int i = 0; i < n; i++) {
            if (i != 0 && values[i - 1] == values[i]) continue;
            natural.put(values[i], counter);
            tmp[counter++] = values[i];
        }
        this.reverse = Arrays.copyOf(tmp, counter);
    }

    public OrderedCompressor(Collection<Long> values) {
        this(values.stream().mapToLong(x -> x).toArray());
    }

    public int map(long x) {
        return natural.get(x);
    }

    public long unmap(int x) {
        return reverse[x];
    }

    public int size() {
        return reverse.length;
    }

    @Override
    public Iterator<Long> iterator() {
        return new Iterator<>() {
            int pos = 0;
            @Override
            public boolean hasNext() {
                return pos < reverse.length;
            }
            @Override
            public Long next() {
                return reverse[pos++];
            }
        };
    }
}
