package enums.setup;

public enum PropertiesKeys {
    DEVICE_ID("device"),
    DEVICE_UDID("udid"),
    APP_UNDER_TEST("aut"),
    SITE_UNDER_TEST("sut"),
    TEST_PLATFORM("platform"),
    DRIVER_URL("driver"),
    WAIT_CLOSE("wait_close");

    public final String property;

    PropertiesKeys(String property) {
        this.property = property;
    }
}
