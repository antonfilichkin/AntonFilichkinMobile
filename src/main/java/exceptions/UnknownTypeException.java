package exceptions;

public class UnknownTypeException extends Exception {
    private final static String MESSAGE = "Unclear type of mobile app";

    public UnknownTypeException() {
        super(MESSAGE);
    }
}
