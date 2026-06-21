package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import utils.AuthTokenManager;

import static io.restassured.RestAssured.given;
import static utils.AuthTokenManager.getToken;

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

    @Test
    void fullBookingFlow() {

        // 1. CREATE
        String body = """
    {
        "firstname": "Sara",
        "lastname": "QA",
        "totalprice": 100,
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2025-01-01",
            "checkout": "2025-01-05"
        },
        "additionalneeds": "Breakfast"
    }
    """;

        int bookingId =
                given()
                        .log().all()
                        .contentType("application/json")
                        .body(body)
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("bookingid");

        // 2. GET
        given()
                .log().all()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200);

        // 3. UPDATE
        String token = AuthTokenManager.getToken();

        String updateBody = """
    {
        "firstname": "SaraUpdated",
        "lastname": "QA",
        "totalprice": 200,
        "depositpaid": false,
        "bookingdates": {
            "checkin": "2025-01-01",
            "checkout": "2025-01-06"
        },
        "additionalneeds": "Lunch"
    }
    """;

        given()
                .log().all()
                .contentType("application/json")
                .header("Cookie", "token=" + token)
                .body(updateBody)
                .when()
                .put("/booking/" + bookingId)
                .then()
                .statusCode(200);

        // 4. DELETE
        given()
                .log().all()
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .statusCode(201);
    }
}
