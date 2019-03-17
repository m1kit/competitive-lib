package dev.mikit.atcoder.lib.geo;

import java.math.BigInteger;
import java.util.Objects;

public class Vec2b {
    public final BigInteger x;
    public final BigInteger y;

    public Vec2b(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2b vec2b = (Vec2b) o;
        return Objects.equals(x, vec2b.x) &&
                Objects.equals(y, vec2b.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
