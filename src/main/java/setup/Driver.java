package setup;

import exceptions.PropertiesNotSetException;
import exceptions.UnknownPlatformException;
import exceptions.UnknownTypeException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;

import static enums.PropertiesKeys.*;
import static io.appium.java_client.remote.MobileBrowserType.CHROME;
import static io.appium.java_client.remote.MobileBrowserType.SAFARI;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;


public class Driver {
    private static AppiumDriver driver;
    private static WebDriverWait waitSingle;

    // Set Property File name
    private static String propertyFile;

    public static void setPropertyFile(String propertyFile) {
        Driver.propertyFile = propertyFile;
    }

    /**
     * Initialize driver with test properties
     */
    public static void prepareDriver() throws Exception {
        //Check if Property File name has been set
        if (propertyFile == null) {
            throw new PropertiesNotSetException();
        }

        //Reading properties
        TestProperties testProperties = new TestProperties(propertyFile);
        String aut = testProperties.getProperty(APP_UNDER_TEST);
        String sut = testProperties.getProperty(SITE_UNDER_TEST);
        String platform = testProperties.getProperty(TEST_PLATFORM);
        String device = testProperties.getProperty(DEVICE_ID);
        String driver = testProperties.getProperty(DRIVER_URL);

        //Test capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String browserName;

        // Setup test platform: Android or iOS, set Browser.
        switch (platform) {
            case ANDROID:
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
                browserName = CHROME;
                break;
            case IOS:
                // TODO set Capabilities for iOS device here.
                browserName = SAFARI;
                break;
            default:
                throw new UnknownPlatformException();
        }
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);

        // Setup type of application: mobile, web (or hybrid)
        if (aut != null && sut == null) {
            // Native
            File app = new File(aut);
            capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        } else if (sut != null && aut == null) {
            // Web
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
        } else {
            throw new UnknownTypeException();
        }

        // Init driver for local Appium server with set capabilities
        if (Driver.driver == null) {
            Driver.driver = new AppiumDriver(new URL(driver), capabilities);
        }

        // TODO Do we really need this?
        // Set an object to handle timeouts
        if (waitSingle == null) {
            waitSingle = new WebDriverWait(driver(), 10);
        }
    }

    // Get driver instance
    public static AppiumDriver driver() throws Exception {
        if (driver == null) {
            prepareDriver();
        }
        return driver;
    }

    // Close driver
    public static void quit() {
        driver.quit();
    }

    private static WebDriverWait driverWait() {
        return waitSingle;
    }
}