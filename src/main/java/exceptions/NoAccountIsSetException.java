package exceptions;

public class NoAccountIsSetException extends Exception {
    private final static String MESSAGE = "Account on device is not set";

    public NoAccountIsSetException() {
        super(MESSAGE);
    }
}
