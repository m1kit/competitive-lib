package dev.mikit.atcoder.lib.math.geo.g2d;

public class Vec2i implements Comparable<Vec2i> {
    public int x;
    public int y;

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2i add(Vec2i other) {
        return new Vec2i(x + other.x, y + other.y);
    }

    public Vec2i sub(Vec2i other) {
        return new Vec2i(x - other.x, y - other.y);
    }

    public int det(Vec2i other) {
        return x * other.y - other.x * y;
    }

    public int dot(Vec2i other) {
        return x * other.x + y * other.y;
    }

    public Vec2i mul(int c) {
        return new Vec2i(c * x, c * y);
    }

    public Vec2i neg() {
        return new Vec2i(-x, -y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2i vec2i = (Vec2i) o;
        return x == vec2i.x && y == vec2i.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Vec2i o) {
        if (x == o.x) {
            return Integer.compare(y, o.y);
        }
        return Integer.compare(x, o.x);
    }

    public int compareToByAngle(Vec2i o) {
        int za = y > 0 || y == 0 && x > 0 ? 0 : 1;
        int zb = o.y > 0 || o.y == 0 && o.x > 0 ? 0 : 1;
        if (za != zb) return za - zb;
        int det = o.x * y - x * o.y;
        if (det != 0) return Integer.signum(det);
        return Integer.compare(x * x + y * y, o.x * o.x + o.y * o.y);
    }
}
