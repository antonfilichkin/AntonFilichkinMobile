package farmAPIs.com.epam.mobilecloud;

import enums.farmAPIs.QueryParams;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static farmAPIs.com.epam.mobilecloud.RequestSpecifications.EPAMFarmRequestSpecification;
import static java.lang.System.getProperty;

public class MobileFarmAPI {
    private static final String BASE_URI = getProperty("path");

    private Map<String, String> params = new HashMap<>();

    public static class QueryBuilder {
        MobileFarmAPI resource;

        private QueryBuilder(MobileFarmAPI resource) {
            this.resource = resource;
        }

        public MobileFarmAPI.QueryBuilder addParam(QueryParams param, String value) {
            this.resource.params.put(param.queryParam, value);
            return this;
        }

        public MobileFarmAPI.QueryBuilder addParams(Map<String, String> params) {
            this.resource.params.putAll(params);
            return this;
        }

        public Response getAvailableDevices() {
            return RestAssured
                    .given(EPAMFarmRequestSpecification())
                    .headers("Authorization", "Bearer " + "a615a837-5a99-4321-a3bc-1f549fd53c45")
                    .with()
                    .queryParams(this.resource.params)
                    .log().all()
                    .get("http://mobilecloud.epam.com/automation/api/device/android")
                    .prettyPeek();
        }

        public Response takeDeviceByID() {
            return RestAssured.get();
        }

        public Response takeDeviceByCapabilities() {
            return RestAssured.get();
        }

        public Response dropDevice() {
            return RestAssured.get();
        }

        public Response installApp() {
            return RestAssured.get();
        }

    }

    public static MobileFarmAPI.QueryBuilder with() {
        MobileFarmAPI api = new MobileFarmAPI();
        return new MobileFarmAPI.QueryBuilder(api);
    }

    // TODO Do we really need this ??? - add the exception to method call - so me can switch to other farm??
    //  get the exception from the reqest if host is unavialable
//    public static void ping() {
//        RestAssured
//                .get("http://mobilecloud.epam.com/automation/api/")
//                .prettyPeek();
//    }
}