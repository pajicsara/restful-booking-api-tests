package base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static RequestSpecification requestSpecification;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        requestSpecification = given()
                .contentType("application/json");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
