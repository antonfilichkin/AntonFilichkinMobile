package setup;

import API.com.epam.mobilecloud.API;
import API.com.epam.mobilecloud.exceptions.ServerResponseNotAsExpected;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.net.URISyntaxException;

import static enums.setup.PropertiesKeys.*;

public class TestHooks {
    @BeforeClass(groups = "setup", description = "Prepare driver to run test(s) on local Appium Server (app and web) and on MobileFarm (web)")
    @Parameters({"propertyFile"})
    public static void setUp(String propertyFile) throws Exception {
        Driver.prepareDriver(propertyFile);
        System.out.println("Driver prepared");
    }

    @BeforeClass(groups = {"setup_send_app"}, description = "Install app on remote device and prepare driver to run on remote on EPAM Mobile Farm")
    @Parameters({"propertyFile"})
    public static void setUpInstallFile(String propertyFile) throws Exception {
        installAppOnDesiredDevice(propertyFile);
        System.out.println("App Installed");
        setUp(propertyFile);
    }

    @AfterClass(groups = {"setup", "setup_send_app"}, description = "Close driver on all tests completion")
    public static void tearDown() throws InterruptedException {
        Driver.quit();
        System.out.println("Driver closed");
    }

    /**
     * Install app on desired device, using Mobile Farm api
     */
    private static void installAppOnDesiredDevice(String propertyFile) throws IOException, URISyntaxException, ServerResponseNotAsExpected {
        TestProperties testProperties = new TestProperties(propertyFile);
        String baseURI = testProperties.getProperty(DRIVER_URL);
        String token = testProperties.getProperty(TOKEN);
        String udid = testProperties.getProperty(DEVICE_UDID);
        String app = testProperties.getProperty(APP_UNDER_TEST);

        API.queryBuilder()
                .setAPIBaseURL(baseURI)
                .auth(token)
                .takeDevice(udid);

        API.queryBuilder()
                .setAPIBaseURL(baseURI)
                .auth(token)
                .installApp(udid, app);
    }
}