package dev.mikit.atcoder.lib.util;

public class WithMoreStack {
    // CodeForces Standard: 64MB
    private static final long STACK_SIZE = 64L * 1024 * 1024;

    private WithMoreStack() {}

    public static void run(Runnable task) {
        try {
            Thread thread = new Thread(null, task, "run", STACK_SIZE);
            thread.start();
            thread.join();
        } catch (InterruptedException ignored) {
        }
    }
}
