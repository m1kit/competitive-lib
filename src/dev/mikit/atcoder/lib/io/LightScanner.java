package dev.mikit.atcoder.lib.io;

import java.io.*;
import java.util.StringTokenizer;

public class LightScanner extends LightScannerAdapter {
    private BufferedReader reader;
    private StringTokenizer tokenizer = null;

    public LightScanner(InputStream in) {
        reader = new BufferedReader(new InputStreamReader(in));
    }

    @Override
    public String string() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return tokenizer.nextToken();
    }

    @Override
    public String nextLine() {
        if (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                return reader.readLine();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return tokenizer.nextToken("\n");
    }

    @Override
    public void close() {
        try {
            this.reader.close();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
