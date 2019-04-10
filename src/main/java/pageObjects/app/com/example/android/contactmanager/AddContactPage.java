package pageObjects.app.com.example.android.contactmanager;

import enums.app.ContactTypes;
import exceptions.NoAccountIsSetException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import testData.app.Contact;

import java.util.List;

import static enums.app.PageNames.ADD_CONTACT;
import static utils.ContactGenerator.generateRandomContact;

public class AddContactPage extends BasePage {
    public AddContactPage() {
        super(ADD_CONTACT.name);
    }

    @FindBy(id = "accountSpinner")
    private WebElement accountSpinner;

//    @FindBys({@FindBy(id = "accountSpinner"),
//            @FindBy(id = "text1")})
//    private WebElement accountSpinnerText;

    @FindBy(id = "contactNameEditText")
    private WebElement nameEdit;

    @FindBy(id = "contactPhoneEditText")
    private WebElement phoneEdit;

    @FindBy(id = "contactPhoneTypeSpinner")
    private WebElement phoneType;

    @FindBy(id = "contactEmailEditText")
    private WebElement emailEdit;

    @FindBy(id = "contactEmailTypeSpinner")
    private WebElement emailType;

    @FindBy(id = "contactSaveButton")
    private WebElement saveButton;

    @FindBy(id = "text1")
    private List<WebElement> typeDialog;

    /**
     * Add contact to Contacts List
     *
     * @param contact - Contact to add
     */
    public void addContact(Contact contact) throws NoAccountIsSetException {
        // Check if account spinner shows account => account is set on device.
        // When account is not set on device - app crashes on adding new contact
        if (!isAccountSelected()) {
            throw new NoAccountIsSetException();
        }

        fillContact(contact);

        // Submit
        saveButton.click();
    }

    public void fillContact(Contact contact) {
        // Fill Email Name Phone
        appiumDriver.hideKeyboard();
        emailEdit.sendKeys(contact.getEmail());
        nameEdit.sendKeys(contact.getName());
        phoneEdit.sendKeys(contact.getPhone());

        // Select Phone And Email Type
        phoneType.click();
        selectContactType(contact.getPhoneType());

        emailType.click();
        selectContactType(contact.getEmailType());

        appiumDriver.hideKeyboard();
    }

    /**
     * Add random contact to Contacts List
     */
    public void addRandomContact() throws NoAccountIsSetException {
        addContact(generateRandomContact());
    }

    // Check if AccountSpinner shows some Account
    private boolean isAccountSelected() {
        return !accountSpinner.getText().isEmpty();
    }

    /**
     * Select type of contact in spinner
     * Only EN and RU locales supported yet
     */
    private void selectContactType(ContactTypes contactType) {
        boolean typeIsPresent = false;
        String selectEN = contactType.typeEN;
        String selectRU = contactType.typeRU;
        for (WebElement element : typeDialog) {
            String elementText = element.getText();
            if (elementText.equals(selectEN) || elementText.equals(selectRU)) {
                element.click();
                typeIsPresent = true;
                break;
            }
        }
        if (!typeIsPresent) {
            throw new IllegalArgumentException("Possible locale issue");
        }
    }
}