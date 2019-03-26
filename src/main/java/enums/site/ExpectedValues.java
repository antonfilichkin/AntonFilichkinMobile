package enums.site;

public enum ExpectedValues {
    //Main Page
    MAIN_PAGE_TITLE("Internet Assigned Numbers Authority"),
    MAIN_PAGE_INTRO("The global coordination of the DNS Root, IP addressing, and other Internet protocol resources" +
            " is performed as the Internet Assigned Numbers Authority (IANA) functions.b Learn more.");

    public final String expectedValue;

    ExpectedValues(String expectedValue) {
        this.expectedValue = expectedValue;
    }
}
