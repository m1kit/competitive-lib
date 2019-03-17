package dev.mikit.atcoder.lib.geo;

import java.util.Objects;

public class Vec3i implements Comparable<Vec3i> {
    public int x;
    public int y;
    public int z;

    public Vec3i(int x, int y, int z) {
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
    public int compareTo(Vec3i o) {
        if (x == o.x) {
            if (y == o.y) {
                return Integer.compare(z, o.z);
            }
            return Integer.compare(y, o.z);
        }
        return Integer.compare(x, o.x);
    }
}
