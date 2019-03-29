package enums.setup;

public enum PropertiesKeys {
    APP_UNDER_TEST("aut"),
    SITE_UNDER_TEST("sut"),
    DRIVER_URL("driver"),
    DRIVER_PORT("port"),
    DRIVER_PATH("path"),
    USER("user"),
    TOKEN("token"),
    TEST_PLATFORM("platform"),
    DEVICE_NAME("device"),
    DEVICE_UDID("udid"),
    WAIT_CLOSE("wait");

    public final String property;

    PropertiesKeys(String property) {
        this.property = property;
    }
}
