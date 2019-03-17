package dev.mikit.atcoder.lib.io;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Objects;

public class LightWriter implements AutoCloseable {

    private static final int DEFAULT_DOUBLE_ACC = 9;

    private final Writer out;
    private boolean autoflush = false;
    private boolean breaked = true;


    public LightWriter(Writer out) {
        this.out = out;
    }

    public LightWriter(OutputStream out) {
        this(new BufferedWriter(new OutputStreamWriter(out, Charset.defaultCharset())));
    }

    public void enableAutoFlush() {
        autoflush = true;
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
        return ans(Boolean.toString(b));
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
}
