package tests;

import base.BaseTest;
import helper.Booking;
import org.junit.jupiter.api.Test;
import services.BookingService;
import testdata.BookingDataFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static tests.BaseTest.requestSpec;
import static tests.BaseTest.service;

public class BookingTests extends BaseTest {

    @Test
    void createBookingTest() {

        Booking booking = BookingDataFactory.createRandomBooking();

        int id = service.createBooking(requestSpec, booking);

        given()
                .spec(requestSpec)
                .when()
                .get("/booking/" + id)
                .then()
                .statusCode(200)
                .body("firstname", equalTo(booking.getFirstname()))
                .body("lastname", equalTo(booking.getLastname()));
    }

    @Test
    void updateBookingTest() {

        Booking booking = BookingDataFactory.createRandomBooking();
        int id = service.createBooking(requestSpec, booking);

        Booking updated = new Booking(
                booking.getFirstname(),
                booking.getLastname(),
                booking.getTotalprice() + 50,
                booking.isDepositpaid(),
                booking.getBookingdates(),
                "PARKING"
        );

        service.updateBooking(requestSpec, id, updated)
                .then()
                .statusCode(200)
                .body("totalprice", equalTo(booking.getTotalprice() + 50));
    }

    @Test
    void partialUpdateValidationTest() {

        Booking booking = BookingDataFactory.createRandomBooking();
        int id = service.createBooking(requestSpec, booking);

        Booking updated = new Booking(
                booking.getFirstname(),
                booking.getLastname(),
                booking.getTotalprice(),
                booking.isDepositpaid(),
                booking.getBookingdates(),
                "BREAKFAST"
        );

        service.updateBooking(requestSpec, id, updated)
                .then()
                .statusCode(200)
                .body("firstname", equalTo(booking.getFirstname()))
                .body("additionalneeds", equalTo("BREAKFAST"));
    }

    @Test
    void deleteBookingTest() {

        Booking booking = BookingDataFactory.createRandomBooking();
        int id = service.createBooking(requestSpec, booking);

        service.deleteBooking(requestSpec, id)
                .then()
                .statusCode(201);
    }

    @Test
    void verifyDeleteBookingTest() {

        Booking booking = BookingDataFactory.createRandomBooking();
        int id = service.createBooking(requestSpec, booking);

        service.deleteBooking(requestSpec, id);

        service.getBooking(requestSpec, id)
                .then()
                .statusCode(404);
    }

    @Test
    void createBookingNegativeTest() {

        String invalidBody = """
        {
            "firstname": "",
            "lastname": "",
            "totalprice": "abc"
        }
    """;

        given()
                .spec(requestSpec)
                .body(invalidBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(500);
    }

    @Test
    void fullBookingFlow() {

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
