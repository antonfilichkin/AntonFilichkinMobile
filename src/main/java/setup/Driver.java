package setup;

import exceptions.UnknownPlatformException;
import exceptions.UnknownTypeException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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

    //Properties
    // Common
    private static String aut;
    private static String sut;
    private static String autPackage;
    private static String autActivivty;

    // Driver
    private static String url;
    private static String port;
    private static String path;
    private static String user;
    private static String token;

    // Device
    private static String platform;
    private static String name;
    private static String udid;

    // Setup
    private static String waitBeforeClose;

    /**
     * Initialize driver with test properties
     *
     * @param propertyFile - file containing test properties
     * @throws UnknownPlatformException - on unset platform property
     * @throws UnknownTypeException     - on unset type (app, web, hybrid) of test
     * @throws MalformedURLException    - on incorrect Appium driver URL
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
                //use device name if udid is not passed
                if ((udid != null)) {
                    capabilities.setCapability(MobileCapabilityType.UDID, udid);
                } else {
                    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, name);
                }
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

            // set AppPackage and AppActivity capabilities required by Mobile Farm
            if (autPackage != null) capabilities.setCapability(APP_PACKAGE.property, autPackage);
            if (autActivivty != null) capabilities.setCapability(APP_ACTIVITY.property, autActivivty);
//            capabilities.setCapability("autoLaunch", true) - true by default;

            // default behavior (ignored by Mobile Farm)
            capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        } else if (sut != null && aut == null) {
            // Web
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
        } else {
            throw new UnknownTypeException();
        }

        // Init driver for local Appium server with set capabilities
        if (driver == null) {
            driver = new AppiumDriver(buildUrl(), capabilities);
        }

        // Set driver wait for elements to load
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Set an object to handle timeouts
        if (wait == null) {
            wait = new WebDriverWait(driver(), 5);
        }
    }

    // Get driver instance
    public static AppiumDriver driver() throws Exception {
        if (driver == null) {
            prepareDriver(propertyFile);
        }
        return driver;
    }

    // Close driver
    static void quit() throws InterruptedException {
        // Wait before closing driver if requested in property file
        if (waitBeforeClose != null) {
            Thread.sleep(Integer.parseInt(waitBeforeClose) * 1000);
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
    private static void setProperties(String propertyFile) throws IOException {
        TestProperties testProperties = new TestProperties(propertyFile);
        // Common
        aut = testProperties.getProperty(APP_UNDER_TEST);
        sut = testProperties.getProperty(SITE_UNDER_TEST);
        autPackage = testProperties.getProperty(APP_PACKAGE);
        autActivivty = testProperties.getProperty(APP_ACTIVITY);

        // Driver
        url = testProperties.getProperty(DRIVER_URL);
        port = testProperties.getProperty(DRIVER_PORT);
        path = testProperties.getProperty(DRIVER_PATH);
        user = testProperties.getProperty(USER);
        token = testProperties.getProperty(TOKEN);

        // Device
        platform = testProperties.getProperty(TEST_PLATFORM);
        name = testProperties.getProperty(DEVICE_NAME);
        udid = testProperties.getProperty(DEVICE_UDID);

        // Setup
        waitBeforeClose = testProperties.getProperty(WAIT_CLOSE);
    }

    /**
     * Method for managing driver url - defaults to local appium driver if no properties set
     *
     * @return - driver url
     */
    private static URL buildUrl() throws URISyntaxException, MalformedURLException {
        URIBuilder uriBuilder = new URIBuilder();
        // Set to default - if not provided
        String bUrl = (url == null) ? "127.0.0.1" : url;
        int bPort = (port == null) ? 4723 : Integer.parseInt(port);
        String bPath = (path == null) ? "/wd/hub" : path;

        uriBuilder.setScheme("http")
                .setHost(bUrl)
                .setPort(bPort)
                .setPath(bPath)
                .setPort(bPort);

        // Do not add username:password - if either is not provided
        if (user != null && token != null) {
            uriBuilder.setUserInfo(user, token);
        }
        return uriBuilder.build().toURL();
    }
}