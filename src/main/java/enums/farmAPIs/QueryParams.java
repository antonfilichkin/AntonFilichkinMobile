package enums.farmAPIs;

public enum QueryParams {
    TYPE("type"),
    MANUFACTURER("manufacturer"),
    MODE("mode"),
    VERSION("version"),
    WEB("web"),
    OWNER("owner");

    public final String queryParam;

    QueryParams(String queryParam) {
        this.queryParam = queryParam;
    }
}
