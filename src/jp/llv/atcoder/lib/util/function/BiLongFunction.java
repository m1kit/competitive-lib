package jp.llv.atcoder.lib.util.function;

@FunctionalInterface
public interface BiLongFunction<T> {

    T apply(long t, long u);

}
