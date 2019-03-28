package setup;

import exceptions.UnknownPlatformException;
import exceptions.UnknownTypeException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static enums.setup.PropertiesKeys.*;
import static io.appium.java_client.remote.MobileBrowserType.CHROME;
import static io.appium.java_client.remote.MobileBrowserType.SAFARI;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;


public class Driver {
    private static String propertyFile;
    private static AppiumDriver driver;
    private static WebDriverWait wait;

    private static String aut;
    private static String sut;
    private static String platform;
//    private static String device;
    private static String udid;
    private static String driver_url;
    private static String wait_before_close;

    /**
     * Initialize driver with test properties
     *
     * @param propertyFile - file containing test properties
     *
     * @throws UnknownPlatformException - on unset platform property
     * @throws UnknownTypeException - on unset type (app, web, hybrid) of test
     * @throws MalformedURLException - on incorrect Appium driver URL
     */
    static void prepareDriver(String propertyFile) throws Exception {
        // Set Properties from file
        setProperties(propertyFile);

        // Test capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String browserName;

        // Setup test platform: Android or iOS, set Browser.
        switch (platform) {
            case ANDROID:
//                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
                capabilities.setCapability(MobileCapabilityType.UDID, udid);
                browserName = CHROME;
                break;
            case IOS:
                capabilities.setCapability(MobileCapabilityType.UDID, udid);
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
            capabilities.setCapability("appPackage", "com.example.android.contactmanager");
            capabilities.setCapability("appActivity", ".ContactManager");
//            capabilities.setCapability("autoLaunch", true);
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

        // Set driver wait for elements to load
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
                prepareDriver(propertyFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return driver;
    }

    // Close driver
    static void quit() {
        // Wait before closing driver if requested in property file
        if (wait_before_close != null) {
            try {
                Thread.sleep(Integer.parseInt(wait_before_close) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        driver.quit();
    }

    // Get wait object
    public static WebDriverWait driverWait() {
        return wait;
    }

    // Private constructor for singleton
    private Driver() {
    }

    //Reading properties
    private static void setProperties(String propertyFile) {
        TestProperties testProperties = new TestProperties(propertyFile);
        aut = testProperties.getProperty(APP_UNDER_TEST);
        sut = testProperties.getProperty(SITE_UNDER_TEST);
        platform = testProperties.getProperty(TEST_PLATFORM);
        // device = testProperties.getProperty(DEVICE_ID);
        udid = testProperties.getProperty(DEVICE_UDID);
        driver_url = testProperties.getProperty(DRIVER_URL);
        wait_before_close = testProperties.getProperty(WAIT_CLOSE);
    }
}