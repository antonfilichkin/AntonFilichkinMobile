package exceptions;

public class UnknownTypeException extends Exception {
    private final static String MESSAGE = "Unclear type of mobile pageObjects.app";

    public UnknownTypeException() {
        super(MESSAGE);
    }
}
