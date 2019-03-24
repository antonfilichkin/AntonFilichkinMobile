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
import java.util.concurrent.TimeUnit;

import static enums.PropertiesKeys.*;
import static io.appium.java_client.remote.MobileBrowserType.CHROME;
import static io.appium.java_client.remote.MobileBrowserType.SAFARI;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;


public class Driver {
    private static String propertyFile;
    private static AppiumDriver driver;
    private static WebDriverWait wait;

    /**
     * Property file setter
     *
     * @param file - file containing test properties
     */
    static void setPropertyFile(String file) {
        propertyFile = file;
    }

    /**
     * Initialize driver with test properties
     */
    static void prepareDriver() throws Exception {
        //Check if propertyFile has been set
        if (propertyFile == null) {
            throw new PropertiesNotSetException();
        }

        //Reading properties
        TestProperties testProperties = new TestProperties(propertyFile);
        String aut = testProperties.getProperty(APP_UNDER_TEST);
        String sut = testProperties.getProperty(SITE_UNDER_TEST);
        String platform = testProperties.getProperty(TEST_PLATFORM);
        String device = testProperties.getProperty(DEVICE_ID);
        String driver_url = testProperties.getProperty(DRIVER_URL);

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
        if (driver == null) {
            driver = new AppiumDriver(new URL(driver_url), capabilities);
        }

        // Set driver wait - for element to load
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Set an object to handle timeouts
        if (wait == null) {
            wait = new WebDriverWait(driver(), 5);
        }
    }

    // Get driver instance
    public static AppiumDriver driver() {
        if (driver == null) {
            try {
                prepareDriver();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return driver;
    }

    // Close driver
    static void quit() {
        driver.quit();
    }

    // Get wait object
    public static WebDriverWait driverWait() {
        return wait;
    }

    // Private constructor for singleton
    private Driver() {
    }
}