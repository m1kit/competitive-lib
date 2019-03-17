package dev.mikit.atcoder.lib.util;

import java.lang.reflect.Array;

public class Reflection {

    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T> getComponentClass(T[] a) {
        return (Class<? extends T>) a.getClass().getComponentType();
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T> getClass(T t) {
        return (Class<? extends T>) t.getClass();
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] newInstance(Class<T> clazz, int length) {
        return (T[]) Array.newInstance(clazz, length);
    }

}
