package dev.mikit.atcoder.lib.io;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

public class LightWriter implements AutoCloseable {

    private static final int DEFAULT_DOUBLE_ACC = 15;

    private final Writer out;
    private boolean autoflush = false;
    private boolean breaked = true;
    private BoolLabel boolLabel = BoolLabel.YES_NO_FIRST_UP;
    private CaseLabel caseLabel = CaseLabel.NONE;

    public LightWriter(Writer out) {
        this.out = out;
    }

    public LightWriter(OutputStream out) {
        this(new OutputStreamWriter(new BufferedOutputStream(out), Charset.defaultCharset()));
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

    public LightWriter print(char c) {
        try {
            out.write(c);
            breaked = false;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        return this;
    }

    public LightWriter print(String s) {
        try {
            out.write(s, 0, s.length());
            breaked = false;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        return this;
    }

    public LightWriter cases(int x) {
        if (!breaked) ln();
        print(caseLabel.format.apply(x));
        breaked = true;
        return this;
    }

    public LightWriter ans(char c) {
        if (!breaked) {
            print(' ');
        }
        return print(c);
    }

    public LightWriter ans(String s) {
        if (!breaked) {
            print(' ');
        }
        return print(s);
    }

    public LightWriter ans(double x, int n) {
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

    public LightWriter ans(double x) {
        return ans(x, DEFAULT_DOUBLE_ACC);
    }

    public LightWriter ans(BigDecimal x) {
        return ans(x == null ? "null" : x.toPlainString());
    }

    public LightWriter ans(long l) {
        return ans(Long.toString(l));
    }

    public LightWriter ans(int i) {
        return ans(Integer.toString(i));
    }

    public LightWriter ans(byte b) {
        return ans(Byte.toString(b));
    }

    public LightWriter ans(boolean b) {
        return ans(boolLabel.transfer(b));
    }

    public LightWriter yes() {
        return ans(true);
    }

    public LightWriter no() {
        return ans(false);
    }

    public LightWriter yesln() {
        return ans(true).ln();
    }

    public LightWriter noln() {
        return ans(false).ln();
    }

    public LightWriter ans(Object obj) {
        return ans(Objects.toString(obj));
    }

    public LightWriter ans(int...n) {
        for (int n1 : n) {
            ans(n1);
        }
        return this;
    }

    public LightWriter ansln(int...n) {
        for (int n1 : n) {
            ans(n1).ln();
        }
        return this;
    }

    public LightWriter ans(long...n) {
        for (long n1 : n) {
            ans(n1);
        }
        return this;
    }

    public LightWriter ansln(long...n) {
        for (long n1 : n) {
            ans(n1).ln();
        }
        return this;
    }

    public LightWriter ans(char...n) {
        for (char n1 : n) {
            ans(n1);
        }
        return this;
    }

    public LightWriter ansln(char...n) {
        for (char n1 : n) {
            ans(n1).ln();
        }
        return this;
    }

    public LightWriter ans(String...n) {
        for (String n1 : n) {
            ans(n1);
        }
        return this;
    }

    public LightWriter ansln(String...n) {
        for (String n1 : n) {
            ans(n1).ln();
        }
        return this;
    }

    public LightWriter ans(int n, double ... x) {
        for (double x1 : x) {
            ans(x1, n);
        }
        return this;
    }

    public LightWriter ansln(int n, double ... x) {
        for (double x1 : x) {
            ans(x1, n).ln();
        }
        return this;
    }

    public LightWriter ans(boolean ... f) {
        for (boolean f1 : f) {
            ans(f1);
        }
        return this;
    }

    public LightWriter ansln(boolean ... f) {
        for (boolean f1 : f) {
            ans(f1).ln();
        }
        return this;
    }

    public LightWriter ans(double ... x) {
        return ans(DEFAULT_DOUBLE_ACC, x);
    }

    public LightWriter ansln(double ... x) {
        return ansln(DEFAULT_DOUBLE_ACC, x);
    }

    public LightWriter ansSingle(Object obj) {
        if (obj.getClass() == Double.class) {
            return ans((double) obj);
        } else if (obj.getClass() == BigDecimal.class) {
            return ans((BigDecimal) obj);
        } else {
            return ans(obj);
        }
    }

    public LightWriter ln() {
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

    public LightWriter flush() {
        try {
            out.flush();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        return this;
    }

    @Override
    public void close() {
        try {
            out.close();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
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
}
