package farmAPIs.com.epam.mobilecloud;

import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

class RequestSpecifications {
    static RequestSpecification EPAMFarmRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setAccept(JSON)
                .setAuth(new PreemptiveOAuth2HeaderScheme())
                .setPort(80)
                .build();
    }
}