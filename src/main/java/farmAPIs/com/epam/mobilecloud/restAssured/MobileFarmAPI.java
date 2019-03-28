package farmAPIs.com.epam.mobilecloud.restAssured;

import beans.DesiredCapabilities;
import beans.Device;
import com.google.gson.Gson;
import enums.farmAPIs.QueryParams;
import exceptions.UnknownPlatformException;
import io.restassured.RestAssured;
import io.restassured.authentication.OAuth2Scheme;
import io.restassured.authentication.OAuthScheme;
import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static enums.farmAPIs.Paths.*;
import static farmAPIs.com.epam.mobilecloud.restAssured.RequestSpecifications.request;
import static farmAPIs.com.epam.mobilecloud.restAssured.RequestSpecifications.sendApp;
import static farmAPIs.com.epam.mobilecloud.restAssured.ResponseSpecifications.successResponse;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.http.ContentType.BINARY;
import static utils.DeserializeResponse.deserializeResponse;
import static utils.DeserializeResponse.deserializeResponseList;


// Using REST Assured - as
public class MobileFarmAPI {
    private static final String base_url = "http://mobilecloud.epam.com/" + API.path;
    private static final String token = "a615a837-5a99-4321-a3bc-1f549fd53c45";
    private final Map<String, String> params = new HashMap<>();

    /**
     * Query Builder for use with EPAM Mobile cloud*
     */
    public static class QueryBuilder {
        final MobileFarmAPI resource;

        private QueryBuilder(MobileFarmAPI resource) {
            this.resource = resource;
        }

        public MobileFarmAPI.QueryBuilder addParam(QueryParams param, String value) {
            this.resource.params.put(param.queryParam, value);
            return this;
        }

        public MobileFarmAPI.QueryBuilder addParam(QueryParams param, int value) {
            this.resource.params.put(param.queryParam, String.valueOf(value));
            return this;
        }

        public MobileFarmAPI.QueryBuilder addParams(Map<String, String> params) {
            this.resource.params.putAll(params);
            return this;
        }

        /**
         * Find available device additional parameters via addParam possible
         *
         * @param platform - Android or iOS
         * @return List of available devices (with Desired Capabilites) from Farm
         */
        public List<Device> getAvailableDevices(String platform, Map<String,String> params) throws UnknownPlatformException {
            if (!platform.equals(ANDROID) && !platform.equals(IOS)) {
                throw new UnknownPlatformException();
            }
            Response response = RestAssured
                    .given(request(token))
                    .with()
                    .queryParams(this.resource.params)
                    .queryParams(params)
                    .log().all()
                    .get(base_url + "/" + DEVICE.path + "/" + platform)
                    .prettyPeek();
            // Check server answer
            response.then().specification(successResponse());

            return deserializeResponseList(response, Device.class);
        }

        /**
         * Take available device in use by unique id
         *
         * @param udid - device udid
         */
        public void getDeviceByID(String udid) {
            RestAssured.given(request(token))
                    .log().all()
                    .post(base_url + "/" + DEVICE.path + "/" + udid)
                    .prettyPeek()
                    .then()
                    .specification(successResponse());
        }

        /**
         * Install application on the device
         *
         * @param udid      - device udid
         * @param pathToApp - path to app
         */
        public void installApp(String udid, String pathToApp) {
            File file = new File(pathToApp);
            RestAssured
                    .given(sendApp(token))
                    .multiPart("file", file)
                    .formParam("file", file.getAbsolutePath())
                    .with()
                    .log().all()
                    .post(base_url + "/" + APP.path + "/" + udid)
                    .prettyPeek();
        }

        /**
         * Take available device in use by desiredCapabilities
         *
         * @param platform - Android or iOS
         * @return udid of taken device
         */
        public String takeDeviceByCapabilities(String platform, DesiredCapabilities capabilities) throws UnknownPlatformException {
            if (!platform.equals(ANDROID) && !platform.equals(IOS)) {
                throw new UnknownPlatformException();
            }
            String params = new Gson().toJson(capabilities);

            Response response = RestAssured
                    .given(request(token))
                    .body(params)
                    .queryParams(this.resource.params)
                    .log().all()
                    .post(base_url + "/" + DEVICE.path)
                    .prettyPeek();

            // Check server answer
            response.then().specification(successResponse());

            return deserializeResponse(response, DesiredCapabilities.class).getUdid();
        }

        /**
         * Stop using device
         *
         * @param udid device udid
         */
        public void dropDevice(String udid) {
            RestAssured
                    .given(request(token))
                    .log().all()
                    .delete(base_url + "/" + DEVICE.path + "/" + udid)
                    .prettyPeek()
                    .then()
                    .specification(successResponse());
        }
    }

    public static MobileFarmAPI.QueryBuilder with() {
        MobileFarmAPI api = new MobileFarmAPI();
        return new MobileFarmAPI.QueryBuilder(api);
    }
}