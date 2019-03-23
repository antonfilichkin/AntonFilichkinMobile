package enums;

public enum PropertiesKeys {
    DEVICE_ID("device"),
    APP_UNDER_TEST("aut"),
    SITE_UNDER_TEST("sut"),
    TEST_PLATFORM("platform"),
    DRIVER_URL("driver");

    public final String property;

    PropertiesKeys(String property) {
        this.property = property;
    }
}
