package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import utils.AuthTokenManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import helper.Booking;
import helper.BookingDates;

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

    BookingDates bookingDates = new BookingDates("2026-07-07", "2026-08-08");

    Booking booking = new Booking(
            "Sara",
            "Test",
            200,
            true,
            bookingDates,
            "Lunch"
    );

        //CREATE
        int bookingId =
                given()
                        .spec(requestSpecification)
                        .log().all()
                        .body(booking)
                        .when()
                        .post("/booking")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .path("bookingid");

        // GET
        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo("Sara"));

        // UPDATE
        String token = AuthTokenManager.getToken();

        Booking updatedBooking = new Booking(
                "SaraUpdated",
                "QA",
                200,
                false,
                bookingDates,
                "Lunch"
        );

        given()
                .spec(requestSpecification)
                .log().all()
                .header("Cookie", "token=" + token)
                .body(updatedBooking)
                .when()
                .put("/booking/" + bookingId)
                .then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo("SaraUpdated"));

        // DELETE
        given()
                .spec(requestSpecification)
                .log().all()
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .log().all()
                .statusCode(201);
    }
}
