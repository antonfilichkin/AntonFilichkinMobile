package enums.app;

public enum ContactTypes {
    HOME("Home"),
    WORK("Work"),
    MOBILE("Mobile"),
    OTHER("Other");

    public final String type;

    ContactTypes(String type) {
        this.type = type;
    }
}
