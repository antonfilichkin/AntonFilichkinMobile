package scenarios;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FirstSimpleTest extends DriverSetup {
    /**
     * Prepare driver to run test(s)
     */
    @BeforeClass
    public void setUp() throws Exception {
        //prepareAndroidNative();
        prepareAndroidWeb();
    }

    /**
     * This simple test just click on button 'Add contact'
     */
    @Test
    public void SimplestTest() {
        String app_package_name = "com.example.android.contactmanager:id/";
        By add_btn = By.id(app_package_name + "addContactButton");
        driver.findElement(add_btn).click();
        System.out.println("Simplest Appium test done");
    }

    @Test(description = "Open website")
    public void webTest() throws InterruptedException {
        driver.get("http://iana.org");
        Thread.sleep(5000);
        System.out.println("Site opening done");
    }

    /**
     * Close driver on all tests completion
     */
    @AfterClass
    public void tearDown()  {
        driver.quit();
    }
}

//
//    Implement improvements that described in slides 26 – 50 of lecture #2 and were discussed in class, including the followings:
//
//        Remove hardcoded values; move values of settings and parameters to external configuration files; implement reading of values from config. Files
//
//        Improve source code structure: separate settings from tests itself and move them into main\java and test\java respectively
//
//        Create unified driver initialization procedure that considers:
//
//        Test for native and web mobile apps
//
//        Android or iOS platforms
//
//        Reading properties from config
//
//        Using WebDriverWait object(s) instead of Thread.sleep
//
//        DesiredCapabilities object can't contains non-NULL “app” or “browser” capabilities simultaneously (only one of them)
//
//        Rewrite Driver using “singleton” pattern
//
//        Implement verifications (do expected results meet?)
//
//        Separate packages for web and native tests
//
//        Use TestNG options for test run management
//
//        Move set up and tear down procedures into separate class (“hooks”)
//
//        Do not forget JavaDocs for classes and methods, and required comments as well
//
//        Follow the code conventions
//
//        You can implement any other improvements that you suppose required/desired