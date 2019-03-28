package enums.farmAPIs;

public enum Paths {
    API("automation/api"),
    BOOKING("booking"),
    DEVICE("device"),
    APP("storage/install");

    public final String path;

    Paths(String path) {
        this.path = path;
    }
}
