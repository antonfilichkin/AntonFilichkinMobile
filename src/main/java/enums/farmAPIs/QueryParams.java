package enums.farmAPIs;

public enum QueryParams {
    TYPE("type"),

    // Find
    MANUFACTURER("manufacturer"),
    MODE("mode"),
    OS_VERSION("version"),
    WEB("web"),
    OWNER("owner"),

    // Take
    DEVICE_NAME("deviceName"),
    PLATFORM_NAME("platformName"),
    PLATFORM_VERSION("platformVersion"),
    UDID("udid");

    public final String queryParam;

    QueryParams(String queryParam) {
        this.queryParam = queryParam;
    }
}
