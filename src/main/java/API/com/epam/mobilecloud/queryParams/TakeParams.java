package API.com.epam.mobilecloud.queryParams;

public enum TakeParams {
    DEVICE_NAME("deviceName"),
    PLATFORM_NAME("platformName"),
    PLATFORM_VERSION("platformVersion"),
    UDID("udid");

    public final String queryParam;

    TakeParams(String queryParam) {
        this.queryParam = queryParam;
    }
}