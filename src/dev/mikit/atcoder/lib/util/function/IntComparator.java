package dev.mikit.atcoder.lib.util.function;

import java.util.Comparator;
import java.util.function.*;

@FunctionalInterface
public interface IntComparator {

    int compare(int k1, int k2);

    default IntComparator reversed() {
        return (k1, k2) -> compare(k2, k1);
    }

    default IntComparator thenComparing(IntComparator other) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return other.compare(k1, k2);
        };
    }

    default <U extends Comparable<? super U>> IntComparator thenComparing(IntFunction<? extends U> keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return keyExtractor.apply(k1).compareTo(keyExtractor.apply(k2));
        };
    }

    default IntComparator thenComparingInt(IntUnaryOperator keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return Integer.compare(keyExtractor.applyAsInt(k1), keyExtractor.applyAsInt(k2));
        };
    }

    default IntComparator thenComparingLong(IntToLongFunction keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return Long.compare(keyExtractor.applyAsLong(k1), keyExtractor.applyAsLong(k2));
        };
    }

    default IntComparator thenComparingDouble(IntToDoubleFunction keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return Double.compare(keyExtractor.applyAsDouble(k1), keyExtractor.applyAsDouble(k2));
        };
    }

    static <U extends Comparable<? super U>> IntComparator comparing(IntFunction<? extends U> keyExtractor) {
        return (k1, k2) -> keyExtractor.apply(k1).compareTo(keyExtractor.apply(k2));
    }

    static <U> IntComparator comparing(IntFunction<? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return (k1, k2) -> keyComparator.compare(keyExtractor.apply(k1), keyExtractor.apply(k2));
    }

    static IntComparator comparingInt(IntUnaryOperator keyExtractor) {
        return (k1, k2) -> Integer.compare(keyExtractor.applyAsInt(k1), (keyExtractor.applyAsInt(k2)));
    }

    static IntComparator comparingLong(IntToLongFunction keyExtractor) {
        return (k1, k2) -> Long.compare(keyExtractor.applyAsLong(k1), (keyExtractor.applyAsLong(k2)));
    }

    static IntComparator comparingDouble(IntToDoubleFunction keyExtractor) {
        return (k1, k2) -> Double.compare(keyExtractor.applyAsDouble(k1), (keyExtractor.applyAsDouble(k2)));
    }

    static IntComparator naturalOrder() {
        return Integer::compare;
    }

    static IntComparator reverseOrder() {
        return (k1, k2) -> Integer.compare(k2, k1);
    }

}
