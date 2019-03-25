package enums.app;

public enum ContactTypes {
    HOME("Home", "Домашний"),
    WORK("Work", "Рабочий"),
    MOBILE("Mobile", "Мобильный"),
    OTHER("Other", "Другой");

    public final String typeEN;
    public final String typeRU;

    ContactTypes(String typeEN, String typeRU) {
        this.typeEN = typeEN;
        this.typeRU = typeRU;
    }
}
