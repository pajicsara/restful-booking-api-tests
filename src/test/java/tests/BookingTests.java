package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BookingTests extends BaseTest {

    @Test
    void getBooking() {
        given()
                .contentType("application/json")
                .when()
                .get("/booking")
                .then()
                .statusCode(200);
    }
}
