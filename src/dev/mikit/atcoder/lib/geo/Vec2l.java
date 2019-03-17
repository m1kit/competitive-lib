package dev.mikit.atcoder.lib.geo;

import java.util.Objects;

public class Vec2l implements Comparable<Vec2l> {

    public long x;
    public long y;

    public Vec2l(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Vec2l add(Vec2l other) {
        return new Vec2l(x + other.x, y + other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2l vec2i = (Vec2l) o;
        return x == vec2i.x &&
                y == vec2i.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Vec2l o) {
        if (x == o.x) {
            return Long.compare(y, o.y);
        }
        return Long.compare(x, o.x);
    }
}
