package exceptions;

public class PropertiesNotSetException extends Exception {
    private final static String MESSAGE = "Properties for driver is not set";

    public PropertiesNotSetException() {
        super(MESSAGE);
    }
}
