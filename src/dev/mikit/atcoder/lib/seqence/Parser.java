package dev.mikit.atcoder.lib.seqence;

public abstract class Parser<T> {
    private int pos = 0;
    private final char[] seq;

    protected Parser(char[] seq) {
        this.seq = seq;
    }

    protected Parser(String seq) {
        this(seq.toCharArray());
    }

    public abstract <T> T parse();

    public long number() {
        int res = 0;
        while (Character.isDigit(now())) {
            res = res * 10 + (now() - '0');
            next();
        }
        return res;
    }

    protected char now() {
        return pos < seq.length ? seq[pos] : 0;
    }

    protected char next() {
        pos++;
        return now();
    }

}
