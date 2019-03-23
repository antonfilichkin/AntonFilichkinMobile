package setup;

import enums.PropertiesKeys;
import enums.PropertyFiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class TestProperties {
    private String propertyFileName;
    private Properties properties = new Properties();

    TestProperties(String propertyFile) {
        this.propertyFileName = propertyFile;
    }

//    TestProperties(PropertyFiles propertyFile) {
//        this.propertyFileName = propertyFile.fileName;
//    }

    String getProperty(PropertiesKeys key) {
        return getProperty(key.property);
    }

    // backward comparability TODO refactor method
    private String getProperty(String key) {
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