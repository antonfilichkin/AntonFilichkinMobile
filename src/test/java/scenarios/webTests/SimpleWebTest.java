package scenarios.webTests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.site.org.iana.MainPage;
import setup.TestHooks;

@Test(groups = "web", description = "Simple iana.org tests")
public class SimpleWebTest extends TestHooks {
    private static MainPage mainPage;

    @BeforeMethod(description = "Open test site")
    public void openSUT() {
        mainPage = new MainPage();
        mainPage.checkSiteIsAvailable();
        mainPage.openSite();
        mainPage.checkTitle();
    }

    @Test(description = "Test: Intro is displayed and contains expected text")
    public void testCheckIntro() {
        mainPage.checkIntro();
    }
}