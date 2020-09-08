package dev.mikit.atcoder.lib.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.NoSuchElementException;

public class LightScanner2 extends LightScannerAdapter {

    private static final int BUF_SIZE = 1024 * 1024;

    private final InputStream stream;
    private final StringBuilder builder = new StringBuilder();
    private final byte[] buf = new byte[BUF_SIZE];
    private int ptr, len;

    public LightScanner2(InputStream stream) {
        this.stream = stream;
    }

    private int read() {
        if (ptr < len) return buf[ptr++];
        try {
            ptr = 0;
            len = stream.read(buf);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        if (len == -1) return -1;
        return buf[ptr++];
    }

    private void skip() {
        int b;
        while (isTokenSeparator(b = read()) && b != -1) ;
        if (b == -1) throw new NoSuchElementException("EOF");
        ptr--;
    }

    private void loadToken() {
        builder.setLength(0);
        skip();
        for (int b = read(); !isTokenSeparator(b); b = read()) {
            builder.appendCodePoint(b);
        }
    }

    private void loadLine() {
        builder.setLength(0);
        for (int b = read(); !isLineSeparator(b); b = read()) {
            builder.appendCodePoint(b);
        }
    }

    @Override
    public char chr() {
        int res = read();
        while (isTokenSeparator(res)) res = read();
        return (char) res;
    }

    @Override
    public String string() {
        loadToken();
        return builder.toString();
    }

    @Override
    public char[] chars() {
        loadToken();
        char[] res = new char[builder.length()];
        builder.getChars(0, builder.length(), res, 0);
        return res;
    }

    @Override
    public int ints() {
        long x = longs();
        if (x < Integer.MIN_VALUE || Integer.MAX_VALUE < x) throw new NumberFormatException("Overflow");
        return (int) x;
    }

    @Override
    public long longs() {
        skip();
        int b = read();
        boolean negate;
        if (b == '-') {
            negate = true;
            b = read();
        } else negate = false;
        long x = 0;
        for (; !isTokenSeparator(b); b = read()) {
            if ('0' <= b && b <= '9') x = x * 10 + b - '0';
            else throw new NumberFormatException("Unexpected character '" + b + "'");
        }
        return negate ? -x : x;
    }

    @Override
    public String nextLine() {
        loadLine();
        return builder.toString();
    }

    @Override
    public void close() {
        try {
            stream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static boolean isLineSeparator(int b) {
        return b < 32 || 126 < b;
    }

    private static boolean isTokenSeparator(int b) {
        return b < 33 || 126 < b;
    }
}
