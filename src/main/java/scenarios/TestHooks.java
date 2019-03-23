package scenarios;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import setup.Driver;

@Test(groups = {"setup"})
public class TestHooks {
//    private static Driver driver = new Driver();

    @BeforeSuite(description = "Prepare driver to run test(s)")
    @Parameters({"propertyFile"})
    public static void setUp(String propertyFile) throws Exception {
        Driver.setPropertyFile(propertyFile);
        Driver.prepareDriver();
        System.out.println("Driver prepared");
    }

    @AfterSuite(description = "Close driver on all tests completion")
    public void tearDown() {
        Driver.quit();
//        System.out.println("Driver closed");
    }
}