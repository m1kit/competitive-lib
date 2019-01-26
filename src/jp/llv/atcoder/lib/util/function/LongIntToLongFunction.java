package jp.llv.atcoder.lib.util.function;

@FunctionalInterface
public interface LongIntToLongFunction<T, R> {

    long applyAsLong(long x, int y);

}
