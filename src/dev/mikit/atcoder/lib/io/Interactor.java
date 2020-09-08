package dev.mikit.atcoder.lib.io;

import net.egork.chelper.tester.State;
import net.egork.chelper.tester.Verdict;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Interactor {
    public Verdict interact(InputStream input, InputStream solutionOutput, OutputStream solutionInput, State<Boolean> state) {
        try {
            while (state.getState()) {
                transfer(input, solutionInput);
                transfer(System.in, solutionInput);
                transfer(solutionOutput, System.out);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return new Verdict(Verdict.VerdictType.RTE, e.getClass().getName());
        }
        return Verdict.UNDECIDED;
    }

    static void transfer(InputStream source, OutputStream dest) throws IOException {
        while (source.available() > 0) dest.write(source.read());
        dest.flush();
    }
}
