package setup;

import beans.DesiredCapabilities;
import beans.Device;
import enums.farmAPIs.QueryParams;
import exceptions.UnknownPlatformException;
import farmAPIs.com.epam.mobilecloud.restAssured.MobileFarmAPI;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.BrowserType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static enums.farmAPIs.QueryParams.*;
import static farmAPIs.com.epam.mobilecloud.restAssured.ResponseSpecifications.successAppInstall;
import static farmAPIs.com.epam.mobilecloud.restAssured.ResponseSpecifications.successResponse;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;

// throws HOST UNKNOWN
public class MobileFarmConnectionTEST {
    //    String url = "http://EPM-TSTF:a615a837-5a99-4321-a3bc-1f549fd53c45@mobilecloud.epam.com:8080/wd/h";
//    String token = "a615a837-5a99-4321-a3bc-1f549fd53c45";
    private static final String app = "src/main/resources/ContactManager.apk";

    private static void getAvailableDevices(String platform, Map<QueryParams, String> params) throws UnknownPlatformException {
        Map<String, String> paramMap = params.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().queryParam, Map.Entry::getValue));

        List<Device> availableDevices = MobileFarmAPI.with()
                .getAvailableDevices(platform, paramMap);
    }

    private static void getDeviceById(String id) {
        MobileFarmAPI.with().getDeviceByID(id);
    }

    private static void installApp(String id, String path) {
        MobileFarmAPI.with().installApp(id, path);
    }

    private static void dropDevice(String id) {
        MobileFarmAPI.with().dropDevice(id);
    }

    private static void getDeviceByCapabilities(DesiredCapabilities dc) throws UnknownPlatformException {
        dc.setDeviceName("Huawei P20 Pro");
        MobileFarmAPI.with().takeDeviceByCapabilities(ANDROID, dc);
    }

    public static void main(String[] args) throws UnknownPlatformException {
        Map<QueryParams, String> params = new HashMap<>();
        params.put(TYPE, "Phone");
        params.put(MANUFACTURER, "HMD GLOBAL");
        getAvailableDevices(ANDROID, params);
        getDeviceById("WCR7N18B06001636");
        installApp("WCR7N18B06001636", app);
        dropDevice("WCR7N18B06001636");
    }
}