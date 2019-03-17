package dev.mikit.atcoder.lib.util.function;

@FunctionalInterface
public interface ObjIntFunction<T, R> {

    R apply(T x, int y);

}
