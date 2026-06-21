package tests;

import base.BaseTest;
import helper.Booking;
import helper.BookingDates;
import org.junit.jupiter.api.Test;
import services.BookingService;
import testdata.BookingDataFactory;
import utils.AuthTokenManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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

   /* BookingDates bookingDates = new BookingDates("2026-07-07", "2026-08-08");

    Booking booking = BookingDataFactory.createRandomBooking();

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
                .body("firstname", equalTo(booking.getFirstname()));

        // UPDATE
        String token = AuthTokenManager.getToken();

        Booking updatedBooking = new Booking(
                booking.getFirstname(),
                booking.getLastname(),
                booking.getTotalprice() + 50,
                booking.isDepositpaid(),
                booking.getBookingdates(),
                "PARKING"
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
                .body("firstname", equalTo(booking.getFirstname()))
                .body("totalprice", equalTo(booking.getTotalprice() + 50))
                .body("additionalneeds", equalTo("PARKING"));

        // DELETE
        given()
                .spec(requestSpecification)
                .log().all()
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .log().all()
                .statusCode(201); */
        BookingService bookingService = new BookingService();

        Booking booking = BookingDataFactory.createRandomBooking();

        int id = bookingService.createBooking(requestSpecification, booking);

        bookingService.getBooking(requestSpecification, id)
                .then()
                .statusCode(200)
                .body("firstname", equalTo(booking.getFirstname()));

        Booking updated = new Booking(
                booking.getFirstname(),
                booking.getLastname(),
                booking.getTotalprice() + 50,
                booking.isDepositpaid(),
                booking.getBookingdates(),
                "PARKING"
        );

        bookingService.updateBooking(requestSpecification, id, updated)
                .then()
                .statusCode(200)
                .body("totalprice", equalTo(booking.getTotalprice() + 50));

        bookingService.deleteBooking(requestSpecification, id)
                .then()
                .statusCode(201);
    }
}
