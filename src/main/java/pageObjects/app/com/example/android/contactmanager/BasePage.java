package pageObjects.app.com.example.android.contactmanager;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static setup.Driver.driver;
import static setup.Driver.driverWait;

abstract class BasePage {
    final AppiumDriver appiumDriver;
    private final WebDriverWait wait; //Do we really need this?

    private final String expectedTitle;

    BasePage(String expectedTitle) {
        this.appiumDriver = driver();
        this.wait = driverWait();
        PageFactory.initElements(appiumDriver, this);
        this.expectedTitle = expectedTitle;
    }

    @FindBy(id = "title")
    private WebElement title;

    public void checkTitle() {
        assertThat(title.getText(), is(expectedTitle));
    }
}
