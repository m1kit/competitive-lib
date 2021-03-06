package dev.mikit.atcoder.lib.util.function;

@FunctionalInterface
public interface BiIntFunction<T> {

    T apply(int t, int u);

}
