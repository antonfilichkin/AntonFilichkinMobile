package farmAPIs.com.epam.mobilecloud.restAssured;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.lessThan;

public class ResponseSpecifications {
    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(JSON)
                .expectStatusCode(HttpStatus.SC_OK)
                .expectResponseTime(lessThan(20000L))
                .build();
    }

    public static ResponseSpecification successAppInstall() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_CREATED)
                .expectResponseTime(lessThan(20000L))
                .build();
    }

    public static ResponseSpecification cancelBookings() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_NO_CONTENT)
                .expectResponseTime(lessThan(20000L))
                .build();
    }
}