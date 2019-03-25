package testData.app;

import enums.app.ContactTypes;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * POJO to manage contacts
 */
@Data
@AllArgsConstructor
public class Contact {
    String name;
    String phone;
    ContactTypes phoneType;
    String email;
    ContactTypes emailType;
}
