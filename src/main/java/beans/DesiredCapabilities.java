
package beans;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public
class DesiredCapabilities {

    private String browserName;
    private String deviceName;
    private String platformName;
    private String platformVersion;
    private String udid;

}
