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
