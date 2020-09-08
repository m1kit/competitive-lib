package dev.mikit.atcoder.lib.math.geo;

import java.math.BigInteger;
import java.util.Objects;

public class Vec3bi implements Comparable<Vec3bi> {
    public BigInteger x;
    public BigInteger y;
    public BigInteger z;

    public Vec3bi(BigInteger x, BigInteger y, BigInteger z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec3bi vec3i = (Vec3bi) o;
        return x == vec3i.x &&
                y == vec3i.y &&
                z == vec3i.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @Override
    public int compareTo(Vec3bi o) {
        if (x.equals(o.x)) {
            if (y.equals(o.y)) {
                return z.compareTo(o.z);
            }
            return y.compareTo(o.y);
        }
        return x.compareTo(o.x);
    }
}
