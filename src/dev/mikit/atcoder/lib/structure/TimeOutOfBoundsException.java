package dev.mikit.atcoder.lib.structure;

public class TimeOutOfBoundsException extends RuntimeException {
    public TimeOutOfBoundsException() {
    }

    public TimeOutOfBoundsException(String message) {
        super(message);
    }

    public TimeOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeOutOfBoundsException(Throwable cause) {
        super(cause);
    }
}
