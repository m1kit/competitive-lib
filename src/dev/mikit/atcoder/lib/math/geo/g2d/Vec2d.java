package dev.mikit.atcoder.lib.math.geo.g2d;

import dev.mikit.atcoder.lib.math.Eps;

import java.util.Comparator;
import java.util.Objects;

public class Vec2d {
    public final double x;
    public final double y;

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2d add(Vec2d other) {
        return new Vec2d(x + other.x, y + other.y);
    }

    public Vec2d sub(Vec2d other) {
        return new Vec2d(x - other.x, y - other.y);
    }

    public double det(Vec2d other) {
        return x * other.y - other.x * y;
    }

    public double dot(Vec2d other) {
        return x * other.x + y * other.y;
    }

    public Vec2d mul(double c) {
        return new Vec2d(c * x, c * y);
    }

    public Vec2d neg() {
        return new Vec2d(-x, -y);
    }

    public Vec2d div(double c) {
        return new Vec2d(x / c, y / c);
    }

    public double lengthSquared() {
        return x * x + y * y;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vec2d normalize() {
        return div(length());
    }

    public double arg() {
        return Math.atan2(y, x);
    }

    public double distanceSquaredTo(Vec2d other) {
        double dx = x - other.x, dy = y - other.y;
        return dx * dx + dy * dy;
    }

    public double distanceTo(Vec2d other) {
        return Math.sqrt(distanceSquaredTo(other));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2d vec2d = (Vec2d) o;
        return Double.compare(vec2d.x, x) == 0 &&
                Double.compare(vec2d.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static Comparator<Vec2d> comparator(Eps eps) {
        return (v1, v2) -> eps.equals(v1.x, v2.x)
                ? Double.compare(v1.y, v2.y)
                : Double.compare(v1.x, v2.x);
    }
}
