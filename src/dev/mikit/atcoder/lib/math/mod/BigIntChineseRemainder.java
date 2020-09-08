package dev.mikit.atcoder.lib.math.mod;

import dev.mikit.atcoder.lib.math.euclid.BigIntEuclidSolver;
import dev.mikit.atcoder.lib.math.geo.Vec3bi;

import java.math.BigInteger;

public class BigIntChineseRemainder {
    private BigInteger remainder = BigInteger.ZERO, modulo = BigInteger.ONE;

    public void grow(BigInteger b2, BigInteger m2) {
        Vec3bi sol = BigIntEuclidSolver.solve(modulo, m2);
        BigInteger p = sol.x, d = sol.z;
        BigInteger w = b2.subtract(remainder);
        if (!BigInteger.ZERO.equals(w.mod(d))) {
            throw new IllegalStateException("Given x % " + m2 + " = " + b2 + " and x % " + modulo + " = " + remainder + ", gcd was " + sol.z);
        }
        m2 = m2.divide(d);
        BigInteger m = modulo.multiply(m2);
        BigInteger tmp = w.divide(d).multiply(p).mod(m2);
        remainder = remainder.add(modulo.multiply(tmp)).mod(m);
        modulo = m;
    }

    public BigInteger getRemainder() {
        return remainder;
    }

    public BigInteger getModulo() {
        return modulo;
    }
}
