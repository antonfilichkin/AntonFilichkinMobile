package enums.setup;

/**
 * Properties setup moved to testNG.xml
 */
@Deprecated
public enum PropertyFiles {
    NATIVE_PROPERTY_FILE("nativetest.properties"),
    WEB_PROPERTY_FILE("webtest.properties");

    public final String fileName;

    PropertyFiles(String fileName) {
        this.fileName = fileName;
    }
}
