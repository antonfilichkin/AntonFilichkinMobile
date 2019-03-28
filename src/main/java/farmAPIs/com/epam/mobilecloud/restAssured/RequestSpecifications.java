package farmAPIs.com.epam.mobilecloud.restAssured;

import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.BINARY;
import static io.restassured.http.ContentType.JSON;

class RequestSpecifications {
    // request specification for API responses
    static RequestSpecification request(String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(basic(token))
                .setContentType(JSON)
                .build();
    }

    // request specification for app upload
    static RequestSpecification sendApp(String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(basic(token))
                .setContentType("multipart/form-data")
                .build();
    }

    // basic request specification
    private static RequestSpecification basic(String token) {
        PreemptiveOAuth2HeaderScheme authScheme = new PreemptiveOAuth2HeaderScheme();
        authScheme.setAccessToken(token);
        return new RequestSpecBuilder()
                .setAccept(JSON)
                .setAuth(authScheme)
                .setPort(80)
                .build();
    }
}