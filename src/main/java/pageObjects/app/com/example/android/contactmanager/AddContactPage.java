package pageObjects.app.com.example.android.contactmanager;

import enums.app.ContactTypes;
import exceptions.NoAccountIsSet;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import testData.Contact;

import java.util.List;

import static utils.ContactGenerator.generateRandomContact;

public class AddContactPage extends BasePage {
    public AddContactPage() {
        super("Add Contact");
    }

    @FindBy(id = "title")
    private WebElement title;

    @FindBy(id = "accountSpinner")
    private WebElement accountSpinner;

    @FindBy(id = "contactNameEditText")
    private WebElement contactNameEdit;

    @FindBy(id = "contactPhoneEditText")
    private WebElement contactPhoneEdit;

    @FindBy(id = "contactPhoneTypeSpinner")
    private WebElement contactPhoneType;

    @FindBy(id = "contactEmailEditText")
    private WebElement contactEmailEdit;

    @FindBy(id = "contactEmailTypeSpinner")
    private WebElement contactEmailType;

    @FindBy(id = "contactSaveButton")
    private WebElement contactSaveButton;

    @FindBy(id = "text1")
    private List<WebElement> typeDialog;

    /**
     * Add contact to Contacts List
     * @param contact - Contact POJO
     */
    public void addContact(Contact contact) {
        // TODO When account is not set on device - app crashes on adding new contact
//        if (!isAccountSelected()){
//            try {
//                throw new NoAccountIsSet();
//            } catch (NoAccountIsSet noAccountIsSet) {
//                noAccountIsSet.printStackTrace();
//            }
//        }

        // Fill Name Phone Email
        contactNameEdit.sendKeys(contact.getName());
        contactPhoneEdit.sendKeys(contact.getPhone());
        contactEmailEdit.sendKeys(contact.getEmail());

        // Select Phone And Email Type
        contactPhoneType.click();
        selectContactType(contact.getPhoneType());

        contactEmailType.click();
        selectContactType(contact.getEmailType());

        // Submit
        appiumDriver.hideKeyboard();
        contactSaveButton.click();
    }

    /**
     * Add random contact to Contacts List
     */
    public void addRandomContact() {
        addContact(generateRandomContact());
    }

    private boolean isAccountSelected(){
        return !accountSpinner.getText().isEmpty();
    }

    // Select type of contact in spinner
    private void selectContactType(ContactTypes contactType) {
        boolean typeIsPresent = false;
        String select = contactType.type;
        for (WebElement element : typeDialog) {
            if (element.getText().equals(select)) {
                element.click();
                typeIsPresent = true;
                break;
            }
        }
        if (!typeIsPresent) {
            throw new IllegalArgumentException(contactType.type);
        }
    }
}