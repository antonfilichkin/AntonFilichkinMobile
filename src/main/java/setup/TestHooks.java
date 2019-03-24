package setup;

import org.testng.annotations.*;

@Test(groups = "setup")
public class TestHooks {
    @BeforeClass(description = "Prepare driver to run test(s)")
    @Parameters({"propertyFile"})
    public static void setUp(String propertyFile) throws Exception {
        Driver.setPropertyFile(propertyFile);
        Driver.prepareDriver();
        System.out.println("Driver prepared");
    }

    @AfterClass(description = "Close driver on all tests completion")
    public void tearDown() {
        Driver.quit();
        System.out.println("Driver closed");
    }
}