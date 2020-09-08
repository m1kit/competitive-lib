package dev.mikit.atcoder.lib.io;

import net.egork.chelper.tester.State;
import net.egork.chelper.tester.Verdict;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ExternalInteractor extends Interactor {
    @Override
    public Verdict interact(InputStream caseIn, InputStream solIn, OutputStream solOut, State<Boolean> state) {
        Process interactor;
        try {
            String arg = new Scanner(caseIn).nextLine();
            interactor = new ProcessBuilder(arg.split(" ")).start();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Verdict.UNDECIDED;
        }
        InputStream intrIn = new BufferedInputStream(interactor.getInputStream());
        InputStream intrErr = new BufferedInputStream(interactor.getErrorStream());
        OutputStream intrOut = new BufferedOutputStream(interactor.getOutputStream());
        try {
            while (state.getState()) {
                if (!interactor.isAlive()) break;
                transfer(intrIn, solOut);
                transfer(solIn, intrOut);
            }
            System.out.flush();
            intrOut.close();
        } catch (Throwable e) {
            e.printStackTrace();
            return new Verdict(Verdict.VerdictType.RTE, e.getClass().getName());
        }
        try {
            if (!interactor.waitFor(1, TimeUnit.SECONDS)) {
                System.err.println("External interactor has not finished yet (Presentation Error)");
                interactor.destroyForcibly();
                interactor.waitFor();
                return Verdict.WA;
            }

            transfer(intrErr, System.err);
            System.err.flush();

            if (interactor.exitValue() != 0) {
                System.err.println("External interactor exited with status code " + interactor.exitValue());
                return Verdict.WA;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Verdict.UNDECIDED;
        }
        return Verdict.OK;
    }
}
