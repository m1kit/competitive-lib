package dev.mikit.atcoder.lib.util.function;

import java.util.Comparator;
import java.util.function.*;

@FunctionalInterface
public interface DoubleComparator {

    int compare(double k1, double k2);

    default DoubleComparator reversed() {
        return (k1, k2) -> compare(k2, k1);
    }

    default DoubleComparator thenComparing(DoubleComparator other) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return other.compare(k1, k2);
        };
    }

    default <U extends Comparable<? super U>> DoubleComparator thenComparing(DoubleFunction<? extends U> keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return keyExtractor.apply(k1).compareTo(keyExtractor.apply(k2));
        };
    }

    default DoubleComparator thenComparingInt(DoubleToIntFunction keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return Integer.compare(keyExtractor.applyAsInt(k1), keyExtractor.applyAsInt(k2));
        };
    }

    default DoubleComparator thenComparingLong(DoubleToLongFunction keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return Long.compare(keyExtractor.applyAsLong(k1), keyExtractor.applyAsLong(k2));
        };
    }

    default DoubleComparator thenComparingDouble(DoubleUnaryOperator keyExtractor) {
        return (k1, k2) -> {
            int t = compare(k1, k2);
            if (t != 0) return t;
            return Double.compare(keyExtractor.applyAsDouble(k1), keyExtractor.applyAsDouble(k2));
        };
    }

    static <U extends Comparable<? super U>> DoubleComparator comparing(DoubleFunction<? extends U> keyExtractor) {
        return (k1, k2) -> keyExtractor.apply(k1).compareTo(keyExtractor.apply(k2));
    }

    static <U> DoubleComparator comparing(DoubleFunction<? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return (k1, k2) -> keyComparator.compare(keyExtractor.apply(k1), keyExtractor.apply(k2));
    }

    static DoubleComparator comparingInt(DoubleToIntFunction keyExtractor) {
        return (k1, k2) -> Integer.compare(keyExtractor.applyAsInt(k1), (keyExtractor.applyAsInt(k2)));
    }

    static DoubleComparator comparingLong(DoubleToLongFunction keyExtractor) {
        return (k1, k2) -> Long.compare(keyExtractor.applyAsLong(k1), (keyExtractor.applyAsLong(k2)));
    }

    static DoubleComparator comparingDouble(DoubleUnaryOperator keyExtractor) {
        return (k1, k2) -> Double.compare(keyExtractor.applyAsDouble(k1), (keyExtractor.applyAsDouble(k2)));
    }

    static DoubleComparator naturalOrder() {
        return Double::compare;
    }

    static DoubleComparator reverseOrder() {
        return (k1, k2) -> Double.compare(k2, k1);
    }
}
