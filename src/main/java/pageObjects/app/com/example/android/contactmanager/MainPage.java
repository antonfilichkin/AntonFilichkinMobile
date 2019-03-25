package pageObjects.app.com.example.android.contactmanager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import testData.app.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static enums.app.PageNames.CONTACT_MANAGER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class MainPage extends BasePage {
    public MainPage() {
        super(CONTACT_MANAGER.name);
    }

    @FindBys({@FindBy(id = "contactList"),
            @FindBy(className = "android.widget.TextView")})
    private List<WebElement> contacts;

    @FindBy(id = "showInvisible")
    private WebElement showInvisibleCheck;

    @FindBy(id = "addContactButton")
    private WebElement addContactButton;

    public void openAddContactPage() {
        addContactButton.click();
    }

    /**
     * Check that displayed contacts has expected contact in them
     *
     * @param contact Contact to search for
     */
    public void checkListContains(Contact contact) {
        List<String> contactsDisplayed = new ArrayList<>(contacts.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList()));
        assertThat(contactsDisplayed, hasItem(contact.getName()));
    }
}