package API.com.epam.mobilecloud.exceptions;

import static java.lang.String.format;

public class ServerResponseNotAsExpected extends Exception {
    private final static String MESSAGE = "Server response is not as expected!";

    public ServerResponseNotAsExpected() {
        super(MESSAGE);
    }

    public ServerResponseNotAsExpected(int expected, int actual, String response) {
        super(MESSAGE + " Expected SC: " + expected + ", but was: " + actual +  ", original response: " + response);
    }
}
