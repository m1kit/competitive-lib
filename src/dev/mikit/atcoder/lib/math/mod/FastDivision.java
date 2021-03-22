package dev.mikit.atcoder.lib.math.mod;

public final class FastDivision {
    private FastDivision() {
    }

    /**
     * Returns highest 64 bits of (unsigned) long multiplication.
     *
     * @param x the number
     * @param y the number
     * @return highest 64 bits of (unsigned) long multiplication.
     */
    public static long multiplyHighUnsigned(long x, long y) {
        long x_high = x >>> 32;
        long y_high = y >>> 32;
        long x_low = x & 0xFFFFFFFFL;
        long y_low = y & 0xFFFFFFFFL;

        long z2 = x_low * y_low;
        long t = x_high * y_low + (z2 >>> 32);
        long z1 = t & 0xFFFFFFFFL;
        long z0 = t >>> 32;
        z1 += x_low * y_high;
        return x_high * y_high + z0 + (z1 >>> 32);
    }

    /**
     * Returns lowest 64 bits of either signed or unsigned long multiplication.
     *
     * @param x the number
     * @param y the number
     * @return lowest 64 bits of long multiplication.
     */
    public static long multiplyLow(long x, long y) {
        long n = x * y;
        return n;
    }

    /**
     * Return's quotient and remainder of 128 bit integer division by 64 bit integer. <p> Code taken from Hacker's
     * Delight: http://www.hackersdelight.org/HDcode/divlu.c.
     *
     * @param u1 highest 64 dividend bits
     * @param u0 lowest 64 dividend bits
     * @param v  the divider
     * @return {quotient, remainder}
     */
    public static long[] divideAndRemainder128(long u1, long u0, long v) {
        long b = (1L << 32); // Number base (16 bits).
        long
                un1, un0,           // Norm. dividend LSD's.
                vn1, vn0,           // Norm. divisor digits.
                q1, q0,             // Quotient digits.
                un64, un21, un10,   // Dividend digit pairs.
                rhat;               // A remainder.
        int s;              // Shift amount for norm.

        if (u1 >= v)                          // If overflow, set rem.
            return new long[]{-1L, -1L};      // possible quotient.


        // count leading zeros
        s = Long.numberOfLeadingZeros(v); // 0 <= s <= 63.
        if (s > 0) {
            v = v << s;         // Normalize divisor.
            un64 = (u1 << s) | ((u0 >>> (64 - s)) & (-s >> 31));
            un10 = u0 << s;     // Shift dividend left.
        } else {
            // Avoid undefined behavior.
            un64 = u1 | u0;
            un10 = u0;
        }

        vn1 = v >>> 32;            // Break divisor up into
        vn0 = v & 0xFFFFFFFFL;     // two 32-bit digits.

        un1 = un10 >>> 32;         // Break right half of
        un0 = un10 & 0xFFFFFFFFL;  // dividend into two digits.

        q1 = Long.divideUnsigned(un64, vn1);            // Compute the first
        rhat = un64 - q1 * vn1;     // quotient digit, q1.
        while (true) {
            if (Long.compareUnsigned(q1, b) >= 0 || Long.compareUnsigned(q1 * vn0, b * rhat + un1) > 0) { //if (q1 >= b || q1 * vn0 > b * rhat + un1) {
                q1 = q1 - 1;
                rhat = rhat + vn1;
                if (Long.compareUnsigned(rhat, b) < 0)
                    continue;
            }
            break;
        }

        un21 = un64 * b + un1 - q1 * v;  // Multiply and subtract.

        q0 = Long.divideUnsigned(un21, vn1);            // Compute the second
        rhat = un21 - q0 * vn1;     // quotient digit, q0.
        while (true) {
            if (Long.compareUnsigned(q0, b) >= 0 || Long.compareUnsigned(q0 * vn0, b * rhat + un0) > 0) {
                q0 = q0 - 1;
                rhat = rhat + vn1;
                if (Long.compareUnsigned(rhat, b) < 0)
                    continue;
            }
            break;
        }
        long r = (un21 * b + un0 - q0 * v) >>> s;    // return it.
        return new long[]{q1 * b + q0, r};
    }

    /**
     * Computes magic for fast unsigned integer division.
     *
     * @param d          the divider
     * @return the magic
     */
    public static Magic magicUnsigned(long d) {
        if (d == 0) throw new ArithmeticException("divide by zero");

        long resultMagic;
        int resultMore;
        int floor_log_2_d = 63 - Long.numberOfLeadingZeros(d);
        if ((d & (d - 1)) == 0) {
            // Power of 2
            resultMagic = 0;
            resultMore = floor_log_2_d | 0x80;
        } else {
            long proposed_m, rem;
            int more;

            long[] tmp = divideAndRemainder128(1L << floor_log_2_d, 0, d); // == (1 << (64 + floor_log_2_d)) / d
            proposed_m = tmp[0];
            rem = tmp[1];

//            assert (rem > 0 && rem < d);
            long e = d - rem;

            // This power works if e < 2**floor_log_2_d.
            if (e < 1L << floor_log_2_d) {
                // This power works
                more = floor_log_2_d;
            } else {
                // We have to use the general 65-bit algorithm.  We need to compute
                // (2**power) / d. However, we already have (2**(power-1))/d and
                // its remainder. By doubling both, and then correcting the
                // remainder, we can compute the larger division.
                // don't care about overflow here - in fact, we expect it
                proposed_m += proposed_m;
                long twice_rem = rem + rem;
                if (twice_rem >= d || twice_rem < rem) proposed_m += 1;
                more = floor_log_2_d | 0x40;
            }
            resultMagic = 1 + proposed_m;
            resultMore = more;
            // result.more's shift should in general be ceil_log_2_d. But if we
            // used the smaller power, we subtract one from the shift because we're
            // using the smaller power. If we're using the larger power, we
            // subtract one from the shift because it's taken care of by the add
            // indicator. So floor_log_2_d happens to be correct in both cases,
            // which is why we do it outside of the if statement.
        }
        return new Magic(resultMagic, resultMore, d);
    }

    /**
     * Returns unsigned {@code dividend / divider} using fast integer division
     *
     * @param dividend the dividend
     * @param divider  the divider
     * @return {@code dividend / divider }
     */
    public static long divideUnsignedFast(long dividend, Magic divider) {
        int more = divider.more;
        if ((more & 0x80) != 0) {
            return dividend >>> (more & 0x3F);
        } else {
            long q = multiplyHighUnsigned(divider.magic, dividend);
            if ((more & 0x40) != 0) {
                long t = ((dividend - q) >>> 1) + q;
                return t >>> (more & 0x3F);
            } else {
                return q >>> more; // all upper bits are 0 - don't need to mask them off
            }
        }
    }

    /**
     * Calculates the remainder using fast integer division
     *
     * @param dividend the dividend
     * @param divider  the divider
     * @return {@code dividend % divider }
     */
    public static long remainderUnsignedFast(long dividend, Magic divider) {
        long quot = divideUnsignedFast(dividend, divider);
        return dividend - quot * divider.divider;
    }

    /**
     * Computes magic for fast mulmod operation.
     *
     * @param divider the divider (must be positive)
     * @return the magic
     */
    public static Magic magic32ForMultiplyMod(long divider) {
        long v = divider;
        int s = Long.numberOfLeadingZeros(v); // 0 <= s <= 63.
        if (s > 0)
            v = v << s;
        return magicUnsigned(v >>> 32);
    }

    /**
     * Returns unsigned {@code (a*b)%divider}
     *
     * @param a       the first multiplier
     * @param b       the second multiplier
     * @param divider the divider
     * @param magic32 magic for fast division {@link #magic32ForMultiplyMod(long)}
     * @return {@code (a*b)%divider }
     */
    public static long multiplyMod128Unsigned(long a, long b, long divider, Magic magic32) {
        return multiplyMod128Unsigned0(multiplyHighUnsigned(a, b), multiplyLow(a, b), divider, magic32);
    }

    /**
     * Returns unsigned {@code (low|(high<<64))%divider}
     *
     * @param high    the highest bits
     * @param low     the lowest bits
     * @param divider the divider
     * @param magic32 magic for fast division {@link #magic32ForMultiplyMod(long)}
     * @return {@code (low|(high<<64))%divider}
     */
    public static long multiplyMod128Unsigned0(long high, long low, long divider, Magic magic32) {
        long b = (1L << 32); // Number base (16 bits).
        long
                un1, un0,           // Norm. dividend LSD's.
                vn1, vn0,           // Norm. divisor digits.
                q1, q0,             // Quotient digits.
                un64, un21, un10,   // Dividend digit pairs.
                rhat;               // A remainder.
        int s;              // Shift amount for norm.

        if (high >= divider)                          // If overflow, set rem.
            throw new IllegalArgumentException();


        // count leading zeros
        s = Long.numberOfLeadingZeros(divider); // 0 <= s <= 63.
        if (s > 0) {
            divider = divider << s;         // Normalize divisor.
            un64 = (high << s) | ((low >>> (64 - s)) & (-s >> 31));
            un10 = low << s;     // Shift dividend left.
        } else {
            // Avoid undefined behavior.
            un64 = high | low;
            un10 = low;
        }

        vn1 = divider >>> 32;            // Break divisor up into
        vn0 = divider & 0xFFFFFFFFL;     // two 32-bit digits.

        un1 = un10 >>> 32;         // Break right half of
        un0 = un10 & 0xFFFFFFFFL;  // dividend into two digits.

        q1 = divideUnsignedFast(un64, magic32);            // Compute the first
        rhat = un64 - q1 * vn1;     // quotient digit, q1.
        while (true) {
            if (Long.compareUnsigned(q1, b) >= 0 || Long.compareUnsigned(q1 * vn0, b * rhat + un1) > 0) { //if (q1 >= b || q1 * vn0 > b * rhat + un1) {
                q1 = q1 - 1;
                rhat = rhat + vn1;
                if (Long.compareUnsigned(rhat, b) < 0)
                    continue;
            }
            break;
        }

        un21 = un64 * b + un1 - q1 * divider;  // Multiply and subtract.

        q0 = divideUnsignedFast(un21, magic32);            // Compute the second
        rhat = un21 - q0 * vn1;     // quotient digit, q0.
        while (true) {
            if (Long.compareUnsigned(q0, b) >= 0 || Long.compareUnsigned(q0 * vn0, b * rhat + un0) > 0) {
                q0 = q0 - 1;
                rhat = rhat + vn1;
                if (Long.compareUnsigned(rhat, b) < 0)
                    continue;
            }
            break;
        }
        long r = (un21 * b + un0 - q0 * divider) >>> s;    // return it.
        return r;
    }

    /**
     * Magic structure.
     */
    public static final class Magic {

        final long magic;
        final int more;
        final long divider;

        Magic(long magic, int more, long divider) {
            this.magic = magic;
            this.more = more;
            this.divider = divider;
        }
    }
}