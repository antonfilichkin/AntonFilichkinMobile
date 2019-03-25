package utils;

import enums.app.ContactTypes;
import testData.Contact;

import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.random;

/**
 * Random Test data generator for Contact
 */
public class ContactGenerator {
    public static Contact generateRandomContact() {
        final String PREFIX = "@@@"; // for simplicity - to get contacts at first positions

        // Generating random strings for name and phone
        String randomName5 = random(5, true, false).toUpperCase();
        String randomNumber11 = random(11, false, true);

        // Initiating random for phone and emailType
        Random random = new Random();
        // Setting max random value
        int bound = ContactTypes.values().length - 1;

        // Creating new Contact
        String name = PREFIX + " " + "Name" + randomName5 + " " + "Surname" + randomName5;
        String phone = "+" + randomNumber11;
        String email = "name" + randomName5 + "@foo.foo";
        ContactTypes phoneType = ContactTypes.values()[random.nextInt(bound)];
        ContactTypes emailType = ContactTypes.values()[random.nextInt(bound)];

        return new Contact(name, phone, phoneType, email, emailType);
    }
}