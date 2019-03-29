package scenarios.nativeTests;

import org.testng.annotations.Test;
import pageObjects.app.com.example.android.contactmanager.AddContactPage;
import pageObjects.app.com.example.android.contactmanager.MainPage;
import setup.TestHooks;
import testData.app.Contact;

import static utils.ContactGenerator.generateRandomContact;

@Test(groups = "native", description = "Simple Contact Manager app tests")
public class SimpleNativeTest extends TestHooks {
    private static MainPage mainPage;
    private static AddContactPage addContactPage;

    // Contact remains on device! It's impossible to remove created Contact by means of app. (((
    @Test(description = "Test: It is possible to add new contact")
    public void testAddContact() throws Exception {
        mainPage = new MainPage();
        addContactPage = new AddContactPage();

        // Open Add Contact page
        mainPage.openAddContactPage();
        addContactPage.checkTitle();

        // Generating random contact
        Contact contact = generateRandomContact();

        // Add Contact
        addContactPage.addContact(contact);

        // Check list contains expected contact
        mainPage.checkListContains(contact);
    }
}