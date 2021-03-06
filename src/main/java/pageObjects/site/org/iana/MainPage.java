package pageObjects.site.org.iana;

import io.appium.java_client.AppiumDriver;
import org.apache.http.HttpStatus;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static enums.site.ExpectedValues.MAIN_PAGE_INTRO;
import static enums.site.ExpectedValues.MAIN_PAGE_TITLE;
import static enums.site.URLs.MAIN_PAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static setup.Driver.driver;
import static setup.Driver.driverWait;

public class MainPage {
    private final AppiumDriver appiumDriver;
    private final WebDriverWait wait;

    public MainPage() throws Exception {
        this.appiumDriver = driver();
        this.wait = driverWait();
        PageFactory.initElements(appiumDriver, this);
    }

    private final String url = MAIN_PAGE.url;

    @FindBy(css = "#intro")
    private WebElement intro;

    /**
     * Selenium can't pass http response code by itself - this solution is to check the availability
     * of the site from test running instance (!!! it still may not be available from device !!!)
     */
    public void checkSiteIsAvailable() throws IOException {
        URL siteURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
        connection.connect();
        int responseCode = connection.getResponseCode();

        assertThat(responseCode, is(HttpStatus.SC_OK));
    }

    /**
     * Open page
     */
    public void openSite() {
        appiumDriver.get(url);
    }

    /**
     * Check page title is as expected
     */
    public void checkTitle() {
        wait.until(ExpectedConditions.urlToBe(url));
        assertThat(appiumDriver.getTitle(), is(MAIN_PAGE_TITLE.expectedValue));
    }

    /**
     * Check Intro is as expected
     */
    public void checkIntro() {
        assertThat(intro.isDisplayed(), is(true));
        assertThat(intro.getText(), is(MAIN_PAGE_INTRO.expectedValue));
    }
}
