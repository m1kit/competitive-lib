package dev.mikit.atcoder.lib.math;

import dev.mikit.atcoder.lib.seqence.StringUtil;

public class Fraction implements Comparable<Fraction> {

    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);

    private final long numerator, denominator;

    public Fraction(long numerator, long denominator) {
        if (denominator == 0) throw new ArithmeticException("Division by zero");
        if (numerator == 0) denominator = 1;
        if (denominator < 0) {
            denominator = -denominator;
            numerator = -numerator;
        }
        long g = IntMath.gcd(Math.abs(numerator), denominator);
        this.numerator = numerator / g;
        this.denominator = denominator / g;
    }

    public Fraction(long numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    public Fraction abs() {
        return new Fraction(Math.abs(numerator), denominator);
    }

    public Fraction add(Fraction other) {
        return new Fraction(numerator * other.denominator + other.numerator * denominator, denominator * other.denominator);
    }

    public Fraction divide(Fraction other) {
        return new Fraction(numerator * other.denominator, denominator * other.numerator);
    }

    public float floatValue() {
        return numerator / (float) denominator;
    }

    public double doubleValue() {
        return numerator / (double) denominator;
    }

    public long floor() {
        return numerator / denominator;
    }

    public long ceil() {
        return (numerator + denominator - 1) / denominator;
    }

    public Fraction min(Fraction other) {
        return numerator * other.denominator <= other.numerator * denominator ? this : other;
    }

    public Fraction max(Fraction other) {
        return numerator * other.denominator >= other.numerator * denominator ? this : other;
    }

    public Fraction inv() {
        return new Fraction(denominator, numerator);
    }

    public Fraction multiply(Fraction other) {
        return new Fraction(numerator * other.numerator, denominator * other.denominator);
    }

    public Fraction negate() {
        return new Fraction(-numerator, denominator);
    }

    public int signum() {
        return Long.signum(numerator);
    }

    public Fraction subtract(Fraction other) {
        return new Fraction(numerator * other.denominator - other.numerator * denominator, denominator * other.denominator);
    }

    @Override
    public int compareTo(Fraction other) {
        return Long.compare(numerator * other.denominator, other.numerator * denominator);
    }

    @Override
    public String toString() {
        return StringUtil.toSuperscript(Long.toString(numerator)) + "/" + StringUtil.toSubscript(Long.toString(denominator));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Fraction fraction = (Fraction) other;
        return numerator == fraction.numerator && denominator == fraction.denominator;
    }

    @Override
    public int hashCode() {
        int result = (int) (numerator ^ (numerator >>> 32));
        result = 31 * result + (int) (denominator ^ (denominator >>> 32));
        return result;
    }
}
