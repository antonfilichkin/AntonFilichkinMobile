package exceptions;

public class NoAccountIsSet extends Exception {
    private final static String MESSAGE = "Account on device is not set";

    public NoAccountIsSet() {
        super(MESSAGE);
    }
}
