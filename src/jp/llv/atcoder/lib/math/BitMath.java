package jp.llv.atcoder.lib.math;

public final class BitMath {

    private BitMath() {
    }

    public static int count(int v) {
        v = (v & 0x55555555) + ((v >> 1) & 0x55555555);
        v = (v & 0x33333333) + ((v >> 2) & 0x33333333);
        v = (v & 0x0f0f0f0f) + ((v >> 4) & 0x0f0f0f0f);
        v = (v & 0x00ff00ff) + ((v >> 8) & 0x00ff00ff);
        v = (v & 0x0000ffff) + ((v >> 16) & 0x0000ffff);
        return v;
    }

    public static int count(long v) {
        v = (v & 0x5555555555555555L) + ((v >> 1) & 0x5555555555555555L);
        v = (v & 0x3333333333333333L) + ((v >> 2) & 0x3333333333333333L);
        v = (v & 0x0f0f0f0f0f0f0f0fL) + ((v >> 4) & 0x0f0f0f0f0f0f0f0fL);
        v = (v & 0x00ff00ff00ff00ffL) + ((v >> 8) & 0x00ff00ff00ff00ffL);
        v = (v & 0x0000ffff0000ffffL) + ((v >> 16) & 0x0000ffff0000ffffL);
        v = (v & 0x00000000ffffffffL) + ((v >> 32) & 0x00000000ffffffffL);
        return (int) v;
    }

    public static int msb(int v) {
        if (v == 0) {
            throw new IllegalArgumentException("Bit not found");
        }
        v |= (v >> 1);
        v |= (v >> 2);
        v |= (v >> 4);
        v |= (v >> 8);
        v |= (v >> 16);
        return count(v) - 1;
    }

    public static int msb(long v) {
        if (v == 0) {
            throw new IllegalArgumentException("Bit not found");
        }
        v |= (v >> 1);
        v |= (v >> 2);
        v |= (v >> 4);
        v |= (v >> 8);
        v |= (v >> 16);
        v |= (v >> 32);
        return count(v) - 1;
    }

    public static int lsb(int v) {
        if (v == 0) {
            throw new IllegalArgumentException("Bit not found");
        }
        v |= (v << 1);
        v |= (v << 2);
        v |= (v << 4);
        v |= (v << 8);
        v |= (v << 16);
        return  32 - count(v);
    }

    public static int lsb(long v) {
        if (v == 0) {
            throw new IllegalArgumentException("Bit not found");
        }
        v |= (v << 1);
        v |= (v << 2);
        v |= (v << 4);
        v |= (v << 8);
        v |= (v << 16);
        v |= (v << 32);
        return  64 - count(v);
    }

    public static int extractLsb(int v) {
        return v & (-v);
    }

    public static long extractLsb(long v) {
        return v & (-v);
    }

    public static int extractMsb(int v) {
        v = (v & 0xFFFF0000) > 0 ? v & 0xFFFF0000 : v;
        v = (v & 0xFF00FF00) > 0 ? v & 0xFF00FF00 : v;
        v = (v & 0xF0F0F0F0) > 0 ? v & 0xF0F0F0F0 : v;
        v = (v & 0xCCCCCCCC) > 0 ? v & 0xCCCCCCCC : v;
        v = (v & 0xAAAAAAAA) > 0 ? v & 0xAAAAAAAA : v;
        return v;
    }

    public static long extractMsb(long v) {
        v = (v & 0xFFFFFFFF00000000L) > 0 ? v & 0xFFFFFFFF00000000L : v;
        v = (v & 0xFFFF0000FFFF0000L) > 0 ? v & 0xFFFF0000FFFF0000L : v;
        v = (v & 0xFF00FF00FF00FF00L) > 0 ? v & 0xFF00FF00FF00FF00L : v;
        v = (v & 0xF0F0F0F0F0F0F0F0L) > 0 ? v & 0xF0F0F0F0F0F0F0F0L : v;
        v = (v & 0xCCCCCCCCCCCCCCCCL) > 0 ? v & 0xCCCCCCCCCCCCCCCCL : v;
        v = (v & 0xAAAAAAAAAAAAAAAAL) > 0 ? v & 0xAAAAAAAAAAAAAAAAL : v;
        return v;
    }

}
