package dev.mikit.atcoder.lib.math;

import dev.mikit.atcoder.lib.util.ArrayUtil;

import java.util.Arrays;

public class FFT {
    public static void fft(double[] a, double[] b, boolean invert) {
        int count = a.length;
        for (int i = 1, j = 0; i < count; i++) {
            int bit = count >> 1;
            for (; j >= bit; bit >>= 1) j -= bit;
            j += bit;
            if (i < j) {
                ArrayUtil.swap(a, i, j);
                ArrayUtil.swap(b, i, j);
            }
        }
        for (int len = 2; len <= count; len <<= 1) {
            int halfLen = len >> 1;
            double angle = 2 * Math.PI / len;
            if (invert) angle = -angle;
            double wLenA = Math.cos(angle);
            double wLenB = Math.sin(angle);
            for (int i = 0; i < count; i += len) {
                double wA = 1; // cos(i * angle)
                double wB = 0; // sin(i * angle)
                for (int j = 0; j < halfLen; j++) {
                    double uA = a[i + j];
                    double uB = b[i + j];
                    // (vA, vB) = (wA, wB) * (a[~], b[~])
                    double vA = a[i + j + halfLen] * wA - b[i + j + halfLen] * wB;
                    double vB = a[i + j + halfLen] * wB + b[i + j + halfLen] * wA;
                    a[i + j] = uA + vA;
                    b[i + j] = uB + vB;
                    a[i + j + halfLen] = uA - vA;
                    b[i + j + halfLen] = uB - vB;
                    // cos((i + 1) * angle) = cos(i * angle) * cos(angle) - sin(i * angle) * sin(angle)
                    // sin((i + 1) * angle) = cos(i * angle) * sin(angle) + sin(i * angle) * cos(angle)
                    double nextWA = wA * wLenA - wB * wLenB;
                    wB = wA * wLenB + wB * wLenA;
                    wA = nextWA;
                }
            }
        }
        if (invert) {
            for (int i = 0; i < count; i++) {
                a[i] /= count;
                b[i] /= count;
            }
        }
    }

    public static long[] convolve(long[] a, long[] b) {
        int len = Math.max(Integer.highestOneBit(Math.max(a.length, b.length) - 1) << 2, 1);
        double[] aRe = new double[len], aIm = new double[len];
        double[] bRe = new double[len], bIm = new double[len];
        for (int i = 0; i < a.length; i++) aRe[i] = a[i];
        fft(aRe, aIm, false);
        for (int i = 0; i < b.length; i++) bRe[i] = b[i];
        fft(bRe, bIm, false);

        for (int i = 0; i < len; i++) {
            double real = aRe[i] * bRe[i] - aIm[i] * bIm[i];
            aIm[i] = aIm[i] * bRe[i] + bIm[i] * aRe[i];
            aRe[i] = real;
        }
        fft(aRe, aIm, true);

        long[] result = new long[len];
        for (int i = 0; i < len; i++) result[i] = Math.round(aRe[i]);
        return result;
    }
}
