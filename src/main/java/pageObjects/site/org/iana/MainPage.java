package pageObjects.site.org.iana;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.http.HttpStatus;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static enums.site.ExpectedValues.*;
import static enums.site.URLs.MAIN_PAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static setup.Driver.driver;
import static setup.Driver.driverWait;
import static utils.HTTPResponceCode.getHttpResponseCode;

public class MainPage {
    private AppiumDriver appiumDriver;
    private WebDriverWait wait;

    public MainPage() {
        this.appiumDriver = driver();
        this.wait = driverWait();
        PageFactory.initElements(appiumDriver, this);
    }

    private String url = MAIN_PAGE.url;

    @FindBy(css = "#intro")
    @AndroidFindBy(id = "intro")
    private WebElement intro;
    //private RemoteWebElement intro; // TODO It's not working - Google it!

    /**
     * Selenium can't pass http response code by itself - this solution is to check the availability
     * of the site from test running instance (!!! it still may not be available from device !!!)
     */
    public void checkSiteIsAvailable() {
        assertThat(getHttpResponseCode(url), is(HttpStatus.SC_OK));
    }

    /**
     * Open page
     */
    public void openSite() {
        appiumDriver.get(url);
    }

    /**
     * Check site title
     */
    public void checkTitle() {
        wait.until(ExpectedConditions.urlToBe(url));
        assertThat(appiumDriver.getTitle(), is(MAIN_PAGE_TITLE.expectedValue));
    }

    /**
     * Check Intro
     */
    public void checkIntro() {
        assertThat(intro.isDisplayed(), is(true));
        assertThat(intro.getText(), is(MAIN_PAGE_INTRO.expectedValue));
    }
}
