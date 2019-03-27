package setup;

import farmAPIs.com.epam.mobilecloud.MobileFarmAPI;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.net.UnknownHostException;

// throws HOST UNKNOWN
public class MobileFarmConnection {
    String url = "http://EPM-TSTF:a615a837-5a99-4321-a3bc-1f549fd53c45@mobilecloud.epam.com:8080/wd/h";
    String token = "a615a837-5a99-4321-a3bc-1f549fd53c45";

    //curl -H "Content-Type: application/json" -H "Authorization: Bearer a615a837-5a99-4321-a3bc-1f549fd53c45" -X GET 'http://mobilecloud.epam.com/automation/api/device/android?web=chrome&version=7'
    public static int getHttpResponseCode(String url) {
        return RestAssured.get(url).statusCode();
    }

//    public static void testwe(){
//        MobileFarmAPI.ping();


//        Response response = null;
//        try {
//            response = MobileFarmAPI.with()
//                    .addParam("web", "chrome")
//                    .addParam("version", "7")
//                    .getAvailableDevices();
//        } catch (UnknownHostException e) {
//            //TODO swth here, or even disable this
//            System.out.println("Have You connected to SWITCH VPN ON !!!");
//            e.printStackTrace();
//        }

//        response.then().specification(successResponse());
//
//        System.out.println();
//        //Assert response status is OK
//        //response.then().specification(successResponse());
//    }

//    public static void main(String[] args) {
//        testwe();
//    }

}

//aut=src/main/resources/ContactManager.apk
//        device=HUAWEI CLT-L29
//        platform=Android
//