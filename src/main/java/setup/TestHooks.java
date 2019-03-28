package setup;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Test(groups = "setup")
public class TestHooks {
    @BeforeClass(description = "Prepare driver to run test(s) on local Appium Server (app and web) and on MobileFarm (web)")
    @Parameters({"propertyFile"})
    public static void setUp(String propertyFile) throws Exception {
        Driver.prepareDriver(propertyFile);
        System.out.println("Driver prepared");
    }

//    @Test(groups = "setupFarmAppTest")
//    @BeforeClass(description = "Prepare driver to run app test(s) on EPAM Mobile Farm")
//    @Parameters({"propertyFile"})
//    public static void setUpMobileFarm(String propertyFile) throws Exception {
////        MobileFarmConnection.
//        Driver.setPropertyFile(propertyFile);
//        Driver.prepareDriver();
//        System.out.println("Driver prepared");
//    }

    @AfterClass(description = "Close driver on all tests completion")
    public void tearDown(){
        Driver.quit();
        System.out.println("Driver closed");
    }
}