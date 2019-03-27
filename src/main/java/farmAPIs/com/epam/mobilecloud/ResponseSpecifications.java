package farmAPIs.com.epam.mobilecloud;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.TEXT;
import static org.hamcrest.Matchers.lessThan;

public class ResponseSpecifications {
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String CLOSE = "close";

    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(JSON)
                .expectHeader(HttpHeaders.CONNECTION, KEEP_ALIVE)
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification resourceNotFound() {
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, KEEP_ALIVE)
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .build();
    }

    public static ResponseSpecification badRequest() {
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, KEEP_ALIVE)
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .build();
    }

    public static ResponseSpecification unauthorised() {
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, KEEP_ALIVE)
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_UNAUTHORIZED)
                .build();
    }

    public static ResponseSpecification unauthorisedWrongToken() {
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, CLOSE)
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_UNAUTHORIZED)
                .build();
    }
}