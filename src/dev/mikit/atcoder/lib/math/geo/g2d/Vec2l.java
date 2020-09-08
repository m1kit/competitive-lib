package dev.mikit.atcoder.lib.math.geo.g2d;

import dev.mikit.atcoder.lib.math.IntMath;

import java.util.Objects;

public class Vec2l implements Comparable<Vec2l> {

    public long x;
    public long y;

    public Vec2l(long x, long y) {
        this.x = x;
        this.y = y;
    }

    void normalizeRatio() {
        if (x == 0) y = Long.signum(y);
        else if (y == 0) x = Long.signum(x);
        else {
            long g = IntMath.gcd(Math.abs(x), Math.abs(y)) * Long.signum(x);
            x /= g;
            y /= g;
        }
    }

    public Vec2l add(Vec2l other) {
        return new Vec2l(x + other.x, y + other.y);
    }

    public Vec2l sub(Vec2l other) {
        return new Vec2l(x - other.x, y - other.y);
    }

    public long det(Vec2l other) {
        return x * other.y - other.x * y;
    }

    public long dot(Vec2l other) {
        return x * other.x + y * other.y;
    }

    public Vec2l mul(long c) {
        return new Vec2l(c * x, c * y);
    }

    public Vec2d mul(double c) {
        return new Vec2d(c * x, c * y);
    }

    public Vec2l rotate() {
        return new Vec2l(y, -x);
    }

    public Vec2l neg() {
        return new Vec2l(-x, -y);
    }

    public long lengthSquared() {
        return x * x + y * y;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public long distanceSquaredTo(Vec2l other) {
        long dx = x - other.x, dy = y - other.y;
        return dx * dx + dy * dy;
    }

    public double distanceTo(Vec2l other) {
        return Math.sqrt(distanceSquaredTo(other));
    }

    public double arg() {
        return Math.atan2(y, x);
    }

    public Vec2d toVec2d() {
        return new Vec2d(x, y);
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

    public int compareToByAngle(Vec2l o) {
        int za = y > 0 || y == 0 && x > 0 ? 0 : 1;
        int zb = o.y > 0 || o.y == 0 && o.x > 0 ? 0 : 1;
        if (za != zb) return za - zb;
        long det = o.x * y - x * o.y;
        if (det != 0) return Long.signum(det);
        return Long.compare(x * x + y * y, o.x * o.x + o.y * o.y);
    }

    @Override
    public Vec2l clone() throws CloneNotSupportedException {
        return (Vec2l) super.clone();
    }
}
