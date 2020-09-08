package dev.mikit.atcoder.lib.io;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.security.AccessControlException;
import java.util.Objects;
import java.util.function.IntFunction;

public class LightWriter2 implements AutoCloseable {

    private static final int BUF_SIZE = 1024 * 1024;
    private static final int BUF_THRESHOLD = 64 * 1024;
    private static final int DEFAULT_DOUBLE_ACC = 15;

    private final OutputStream out;
    private final byte[] buf = new byte[BUF_SIZE];
    private int ptr;

    private final Field fastStringAccess;

    private boolean autoflush = false;
    private boolean breaked = true;
    private BoolLabel boolLabel = BoolLabel.YES_NO_FIRST_UP;
    private CaseLabel caseLabel = CaseLabel.NONE;

    public LightWriter2(OutputStream out) {
        this.out = out;
        Field f;
        try {
            f = String.class.getDeclaredField("value");
            f.setAccessible(true);
            if (f.getType() != byte[].class) f = null;
        } catch (ReflectiveOperationException | AccessControlException ignored) {
            f = null;
        }
        this.fastStringAccess = f;
    }

    public LightWriter2(Writer out) {
        this.out = new WriterOutputStream(out);
        this.fastStringAccess = null;
    }

    public void enableAutoFlush() {
        autoflush = true;
    }

    public void setBoolLabel(BoolLabel label) {
        this.boolLabel = Objects.requireNonNull(label);
    }

    public void setCaseLabel(CaseLabel label) {
        this.caseLabel = Objects.requireNonNull(label);
    }

    private void allocate(int n) {
        if (ptr + n <= BUF_SIZE) return;
        try {
            out.write(buf, 0, ptr);
            ptr = 0;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        if (BUF_SIZE < n) throw new IllegalArgumentException("Internal buffer exceeded");
    }

    public LightWriter2 flush() {
        try {
            out.write(buf, 0, ptr);
            ptr = 0;
            out.flush();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        return this;
    }

    @Override
    public void close() {
        try {
            out.write(buf, 0, ptr);
            ptr = 0;
            out.flush();
            out.close();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public LightWriter2 print(char c) {
        allocate(1);
        buf[ptr++] = (byte) c;
        breaked = false;
        return this;
    }

    public LightWriter2 print(String s) {
        byte[] bytes;
        if (this.fastStringAccess == null) bytes = s.getBytes();
        else {
            try {
                bytes = (byte[]) fastStringAccess.get(s);
            } catch (IllegalAccessException ignored) {
                bytes = s.getBytes();
            }
        }
        int n = bytes.length;
        if (n <= BUF_THRESHOLD) {
            allocate(n);
            System.arraycopy(bytes, 0, buf, ptr, n);
            ptr += n;
            return this;
        }
        try {
            out.write(buf, 0, ptr);
            ptr = 0;
            out.write(bytes);
            out.flush();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        return this;
    }

    public LightWriter2 cases(int x) {
        if (!breaked) ln();
        print(caseLabel.format.apply(x));
        breaked = true;
        return this;
    }

    public LightWriter2 ans(char c) {
        if (!breaked) {
            print(' ');
        }
        return print(c);
    }

    public LightWriter2 ans(String s) {
        if (!breaked) {
            print(' ');
        }
        breaked = false;
        return print(s);
    }

    public LightWriter2 ans(double x, int n) {
        if (!breaked) {
            print(' ');
        }
        if (x < 0) {
            print('-');
            x = -x;
        }
        x += Math.pow(10, -n) / 2;
        print(Long.toString((long) x)).print('.');
        x -= (long) x;
        for (int i = 0; i < n; i++) {
            x *= 10;
            print((char) ('0' + ((int) x)));
            x -= (int) x;
        }
        return this;
    }

    public LightWriter2 ans(double x) {
        return ans(x, DEFAULT_DOUBLE_ACC);
    }

    public LightWriter2 ans(BigDecimal x) {
        return ans(x == null ? "null" : x.toPlainString());
    }

    public LightWriter2 ans(long l) {
        if (!breaked) {
            print(' ');
        }
        breaked = false;
        if (l == 0) return print('0');
        if (l < 0) {
            print('-');
            l = -l;
        }
        int n = 0;
        long t = l;
        while (t > 0) {
            t /= 10;
            n++;
        }
        allocate(n);
        for (int i = 1; i <= n; i++) {
            buf[ptr + n - i] = (byte) (l % 10 + '0');
            l /= 10;
        }
        ptr += n;
        return this;
    }

    public LightWriter2 ans(int i) {
        if (!breaked) {
            print(' ');
        }
        breaked = false;
        if (i == 0) return print('0');
        if (i < 0) {
            print('-');
            i = -i;
        }
        int n = 0;
        long t = i;
        while (t > 0) {
            t /= 10;
            n++;
        }
        allocate(n);
        for (int j = 1; j <= n; j++) {
            buf[ptr + n - j] = (byte) (i % 10 + '0');
            i /= 10;
        }
        ptr += n;
        return this;
    }

    public LightWriter2 ans(boolean b) {
        return ans(boolLabel.transfer(b));
    }

    public LightWriter2 yes() {
        return ans(true);
    }

    public LightWriter2 no() {
        return ans(false);
    }

    public LightWriter2 yesln() {
        return ans(true).ln();
    }

    public LightWriter2 noln() {
        return ans(false).ln();
    }

    public LightWriter2 ans(Object obj) {
        return ans(Objects.toString(obj));
    }

    public LightWriter2 ans(int... n) {
        for (int n1 : n) {
            ans(n1);
        }
        return this;
    }

    public LightWriter2 ansln(int... n) {
        for (int n1 : n) {
            ans(n1).ln();
        }
        return this;
    }

    public LightWriter2 ans(long... n) {
        for (long n1 : n) {
            ans(n1);
        }
        return this;
    }

    public LightWriter2 ansln(long... n) {
        for (long n1 : n) {
            ans(n1).ln();
        }
        return this;
    }

    public LightWriter2 ans(char... n) {
        for (char n1 : n) {
            ans(n1);
        }
        return this;
    }

    public LightWriter2 ansln(char... n) {
        for (char n1 : n) {
            ans(n1).ln();
        }
        return this;
    }

    public LightWriter2 ans(String... n) {
        for (String n1 : n) {
            ans(n1);
        }
        return this;
    }

    public LightWriter2 ansln(String... n) {
        for (String n1 : n) {
            ans(n1).ln();
        }
        return this;
    }

    public LightWriter2 ans(int n, double... x) {
        for (double x1 : x) {
            ans(x1, n);
        }
        return this;
    }

    public LightWriter2 ansln(int n, double... x) {
        for (double x1 : x) {
            ans(x1, n).ln();
        }
        return this;
    }

    public LightWriter2 ans(boolean... f) {
        for (boolean f1 : f) {
            ans(f1);
        }
        return this;
    }

    public LightWriter2 ansln(boolean... f) {
        for (boolean f1 : f) {
            ans(f1).ln();
        }
        return this;
    }

    public LightWriter2 ans(double... x) {
        return ans(DEFAULT_DOUBLE_ACC, x);
    }

    public LightWriter2 ansln(double... x) {
        return ansln(DEFAULT_DOUBLE_ACC, x);
    }

    public LightWriter2 ansSingle(Object obj) {
        if (obj.getClass() == Double.class) {
            return ans((double) obj);
        } else if (obj.getClass() == BigDecimal.class) {
            return ans((BigDecimal) obj);
        } else {
            return ans(obj);
        }
    }

    public LightWriter2 ln() {
        print(System.lineSeparator());
        breaked = true;
        if (autoflush) {
            try {
                out.flush();
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
        return this;
    }

    public enum BoolLabel {
        YES_NO_FIRST_UP("Yes", "No"),
        YES_NO_ALL_UP("YES", "NO"),
        YES_NO_ALL_DOWN("yes", "no"),
        Y_N_ALL_UP("Y", "N"),
        POSSIBLE_IMPOSSIBLE_FIRST_UP("Possible", "Impossible"),
        POSSIBLE_IMPOSSIBLE_ALL_UP("POSSIBLE", "IMPOSSIBLE"),
        POSSIBLE_IMPOSSIBLE_ALL_DOWN("possible", "impossible"),
        FIRST_SECOND_FIRST_UP("First", "Second"),
        FIRST_SECOND_ALL_UP("FIRST", "SECOND"),
        FIRST_SECOND_ALL_DOWN("first", "second"),
        ALICE_BOB_FIRST_UP("Alice", "Bob"),
        ALICE_BOB_ALL_UP("ALICE", "BOB"),
        ALICE_BOB_ALL_DOWN("alice", "bob"),
        ;
        private final String positive, negative;

        BoolLabel(String positive, String negative) {
            this.positive = positive;
            this.negative = negative;
        }

        private String transfer(boolean f) {
            return f ? positive : negative;
        }
    }

    public enum CaseLabel {
        NONE(x -> ""),
        GCJ(x -> "Case #" + x + ": "),
        ;
        private final IntFunction<String> format;

        CaseLabel(IntFunction<String> format) {
            this.format = format;
        }
    }

    private static class WriterOutputStream extends OutputStream {
        final Writer writer;
        final CharsetDecoder decoder;

        WriterOutputStream(Writer writer) {
            this.writer = writer;
            this.decoder = StandardCharsets.UTF_8.newDecoder();
        }

        @Override
        public void write(int b) throws IOException {
            writer.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            writer.write(decoder.decode(ByteBuffer.wrap(b)).array());
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            writer.write(decoder.decode(ByteBuffer.wrap(b, off, len)).array());
        }

        @Override
        public void flush() throws IOException {
            writer.flush();
        }

        @Override
        public void close() throws IOException {
            writer.close();
        }
    }
}
