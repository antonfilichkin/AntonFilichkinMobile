package pageObjects.app.com.example.android.contactmanager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import testData.Contact;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MainPage extends BasePage {
    public MainPage() {
        super("Contact Manager");
    }

    @FindBys({@FindBy(id = "contactList"),
            @FindBy(className = "android.widget.LinearLayout")})
    private List<WebElement> contacts;

    @FindBy(id = "showInvisible")
    private WebElement showInvisibleCheck;

    @FindBy(id = "addContactButton")
    private WebElement addContactButton;

    public void openAddContactPage() {
        addContactButton.click();
    }

    public void checkListContains(Contact contact) {

        boolean anyMatch = contacts.stream()
                .map(WebElement::getText)
                .anyMatch(name -> contact.getName().equals(name));
        assertThat(anyMatch, is(true));
    }
}