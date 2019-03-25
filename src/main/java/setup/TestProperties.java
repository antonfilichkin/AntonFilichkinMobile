package setup;

import enums.setup.PropertiesKeys;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class TestProperties {
    private String propertyFileName;
    private Properties properties = new Properties();

    TestProperties(String propertyFile) {
        this.propertyFileName = propertyFile;
    }

    String getProperty(PropertiesKeys pKey) {
        String key = pKey.property;
        if (!properties.containsKey(key)) {
            properties = getProperties();
        }
        // "default" form used to handle the absence of parameter
        return getProperties().getProperty(key, null);
    }

    private Properties getProperties() {
        try (InputStream in = TestProperties.class.getResourceAsStream("/" + propertyFileName)) {
            properties.load(in);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return properties;
    }
}