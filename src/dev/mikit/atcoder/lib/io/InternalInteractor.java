package dev.mikit.atcoder.lib.io;

import net.egork.chelper.tester.State;
import net.egork.chelper.tester.Verdict;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class InternalInteractor extends Interactor {
    @Override
    public Verdict interact(InputStream input, InputStream solutionOutput, OutputStream solutionInput, State<Boolean> state) {
        try (LightScanner caseIn = new LightScanner(input);
             LightScanner solIn = new LightScanner(solutionOutput);
             LightWriter solOut = new LightWriter(solutionInput)) {
            Thread thread = new Thread(
                    () -> test(caseIn, solIn, solOut),
                    "Interactor thread"
            );
            thread.start();
            while (state.getState()) {
                thread.join(500);
            }
        } catch (InterruptedException ignored) {
        }
        return Verdict.UNDECIDED;
    }

    protected abstract Verdict test(LightScanner caseIn, LightScanner solIn, LightWriter solOut);
}
