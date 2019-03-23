package exceptions;

public class UnknownPlatformException extends Exception {
    private final static String MESSAGE = "Unknown mobile platform";

    public UnknownPlatformException() {
        super(MESSAGE);
    }
}
