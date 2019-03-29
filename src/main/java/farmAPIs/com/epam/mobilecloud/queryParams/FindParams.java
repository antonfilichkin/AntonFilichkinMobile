package farmAPIs.com.epam.mobilecloud.queryParams;

public enum FindParams {
    TYPE("type"),
    MANUFACTURER("manufacturer"),
    MODE("mode"),
    OS_VERSION("version"),
    WEB("web"),
    OWNER("owner");

    public final String queryParam;

    FindParams(String queryParam) {
        this.queryParam = queryParam;
    }
}
