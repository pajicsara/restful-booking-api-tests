package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BookingTests extends BaseTest {

    static int bookingId;

    @Test
    void createBooking() {
        String body = """
    {
        "firstname": "Sara",
        "lastname": "Test",
        "totalprice": 120,
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2026-07-07",
            "checkout": "2025-08-08"
        },
        "additionalneeds": "Breakfast"
    }
    """;

        bookingId =
                given()
                        .contentType("application/json")
                        .body(body)
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("bookingid");
    }

    @Test
    void getBookingWithId() {
        given()
                .contentType("application/json")
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200);
    }

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
