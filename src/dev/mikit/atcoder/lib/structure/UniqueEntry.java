package dev.mikit.atcoder.lib.structure;

import java.util.Objects;

public class UniqueEntry<T extends Comparable<T>> implements Comparable<UniqueEntry<T>> {

    private final T value;
    private final long ord;

    private UniqueEntry(T value) {
        this(value, Double.doubleToLongBits(Math.random()));
    }

    private UniqueEntry(T value, long ord) {
        this.value = value;
        this.ord = ord;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqueEntry<?> that = (UniqueEntry<?>) o;
        if (ord != that.ord) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (int) (ord ^ (ord >>> 32));
        return result;
    }

    @Override
    public int compareTo(UniqueEntry<T> o) {
        int sgn = value.compareTo(o.value);
        return sgn == 0 ? Long.compare(ord, o.ord) : sgn;
    }

    public static <T extends Comparable<T>> UniqueEntry<T> of(T value) {
        return new UniqueEntry<>(value);
    }

    public static <T extends Comparable<T>> UniqueEntry<T> min(T value) {
        return new UniqueEntry<>(value, Long.MIN_VALUE);
    }

    public static <T extends Comparable<T>> UniqueEntry<T> max(T value) {
        return new UniqueEntry<>(value, Long.MAX_VALUE);
    }
}
