package dev.mikit.atcoder.lib.geo;

import java.util.Objects;

public class Vec3l implements Comparable<Vec3l> {
    public long x;
    public long y;
    public long z;

    public Vec3l(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec3i vec3i = (Vec3i) o;
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
    public int compareTo(Vec3l o) {
        if (x == o.x) {
            if (y == o.y) {
                return Long.compare(z, o.z);
            }
            return Long.compare(y, o.z);
        }
        return Long.compare(x, o.x);
    }
}
