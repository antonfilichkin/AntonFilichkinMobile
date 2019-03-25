package testData;

import enums.app.ContactTypes;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Contact {
        String name;
        String phone;
        ContactTypes phoneType;
        String email;
        ContactTypes emailType;
}
