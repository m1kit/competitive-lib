package jp.llv.atcoder.lib.structure.tuple;

import java.util.Objects;

public class Triple<K, V1, V2> {
    public K key;
    public V1 value1;
    public V2 value2;

    public Triple(K key, V1 value1, V2 value2) {
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return Objects.equals(key, triple.key) &&
                Objects.equals(value1, triple.value1) &&
                Objects.equals(value2, triple.value2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value1, value2);
    }

    @Override
    public String toString() {
        return "Triple{" +
                "x=" + key +
                ", value1=" + value1 +
                ", value2=" + value2 +
                '}';
    }
}
