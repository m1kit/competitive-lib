package dev.mikit.atcoder.lib.util.function;

import java.util.Comparator;
import java.util.function.*;

@FunctionalInterface
public interface LongComparator {

    int compare(long k1, long k2);

    default LongComparator reversed() {
        return (k1, k2) -> compare(k2, k1);
    }

    default LongComparator thenComparing(LongComparator other) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return other.compare(k1, k2);
        };
    }

    default <U extends Comparable<? super U>> LongComparator thenComparing(LongFunction<? extends U> keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return keyExtractor.apply(k1).compareTo(keyExtractor.apply(k2));
        };
    }

    default LongComparator thenComparingInt(LongToIntFunction keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return Integer.compare(keyExtractor.applyAsInt(k1), keyExtractor.applyAsInt(k2));
        };
    }

    default LongComparator thenComparingLong(LongUnaryOperator keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return Long.compare(keyExtractor.applyAsLong(k1), keyExtractor.applyAsLong(k2));
        };
    }

    default LongComparator thenComparingDouble(LongToDoubleFunction keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return Double.compare(keyExtractor.applyAsDouble(k1), keyExtractor.applyAsDouble(k2));
        };
    }

    static <U extends Comparable<? super U>> LongComparator comparing(LongFunction<? extends U> keyExtractor) {
        return (k1, k2) -> keyExtractor.apply(k1).compareTo(keyExtractor.apply(k2));
    }

    static <U> LongComparator comparing(LongFunction<? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return (k1, k2) -> keyComparator.compare(keyExtractor.apply(k1), keyExtractor.apply(k2));
    }

    static LongComparator comparingInt(LongToIntFunction keyExtractor) {
        return (k1, k2) -> Integer.compare(keyExtractor.applyAsInt(k1), (keyExtractor.applyAsInt(k2)));
    }

    static LongComparator comparingLong(LongUnaryOperator keyExtractor) {
        return (k1, k2) -> Long.compare(keyExtractor.applyAsLong(k1), (keyExtractor.applyAsLong(k2)));
    }

    static LongComparator comparingDouble(LongToDoubleFunction keyExtractor) {
        return (k1, k2) -> Double.compare(keyExtractor.applyAsDouble(k1), (keyExtractor.applyAsDouble(k2)));
    }

    static LongComparator naturalOrder() {
        return Long::compare;
    }

    static LongComparator reverseOrder() {
        return (k1, k2) -> Long.compare(k2, k1);
    }
}
