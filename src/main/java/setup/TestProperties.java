package setup;

import enums.setup.PropertiesKeys;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class TestProperties {
    private final String propertyFileName;
    private Properties properties = new Properties();

    TestProperties(String propertyFile) {
        this.propertyFileName = propertyFile;
    }

    String getProperty(PropertiesKeys pKey) throws IOException {
        String key = pKey.property;
        if (!properties.containsKey(key)) {
            properties = getProperties();
        }
        // "default" form used to handle the absence of parameter
        return getProperties().getProperty(key, null);
    }

    private Properties getProperties() throws IOException {
        try (InputStream in = TestProperties.class.getResourceAsStream("/" + propertyFileName)) {
            properties.load(in);
        }
        return properties;
    }
}