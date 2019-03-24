package utils;

import io.restassured.RestAssured;

public class HTTPResponceCode {
    public static int getHttpResponseCode(String url) {
        return RestAssured.get(url).statusCode();
    }
}

