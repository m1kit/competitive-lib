package dev.mikit.atcoder.lib.io;

import java.io.*;
import java.util.NoSuchElementException;

public class LightScanner2 extends LightScannerAdapter {

    private static final int BUF_SIZE = 16 * 1024;

    private final InputStream stream;
    private final StringBuilder builder = new StringBuilder();
    private final byte[] buf = new byte[BUF_SIZE];
    private int ptr, len;

    public LightScanner2(InputStream stream) {
        this.stream = stream;
    }

    private int read() {
        if (ptr < len) return buf[ptr++];
        reload();
        if (len == -1) return -1;
        return buf[ptr++];
    }

    private void reload() {
        try {
            ptr = 0;
            len = stream.read(buf);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private void load(int n) {
        if (ptr + n <= len) return;
        System.arraycopy(buf, ptr, buf, 0, len - ptr);
        len -= ptr;
        ptr = 0;
        try {
            int r = stream.read(buf, len, BUF_SIZE - len);
            if (r == -1) return;
            len += r;
            if (len != BUF_SIZE) buf[len] = '\n';
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private void skip() {
        while (len != -1) {
            while (ptr < len && isTokenSeparator(buf[ptr])) ptr++;
            if (ptr < len) return;
            reload();
        }
        throw new NoSuchElementException("EOF");
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
        skip();
        load(12);
        int b = buf[ptr++];
        boolean negate;
        if (b == '-') {
            negate = true;
            b = buf[ptr++];
        } else negate = false;
        int x = 0;
        for (; !isTokenSeparator(b); b = buf[ptr++]) {
            if ('0' <= b && b <= '9') x = x * 10 + b - '0';
            else throw new NumberFormatException("Unexpected character '" + ((char) b) + "'");
        }
        return negate ? -x : x;
    }

    @Override
    public long longs() {
        skip();
        load(20);
        int b = buf[ptr++];
        boolean negate;
        if (b == '-') {
            negate = true;
            b = buf[ptr++];
        } else negate = false;
        long x = 0;
        for (; !isTokenSeparator(b); b = buf[ptr++]) {
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
