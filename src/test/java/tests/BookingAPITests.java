package tests;

import base.BaseTest;
import helper.Booking;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import testdata.BookingDataFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static tests.BaseTest.requestSpec;
import static tests.BaseTest.service;

@Epic("Restful Booking API")
@Feature("Booking CRUD operations")
public class BookingAPITests extends BaseTest {

    @Story("Create booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Creates a new booking and verifies it can be fetched")
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

    @Story("Update booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Update existing booking and verifies if updated")
    @Test
    void updateBookingTest() {

        Booking booking = BookingDataFactory.createRandomBooking();
        int id = service.createBooking(requestSpec, booking);

        Booking updated = new Booking(
                booking.getFirstname(),
                booking.getLastname(),
                booking.getTotalprice() + 50,
                !booking.isDepositpaid(),
                booking.getBookingdates(),
                "BREAKFAST"
        );

        service.updateBooking(requestSpec, id, updated)
                .then()
                .statusCode(200)
                .body("totalprice", equalTo(booking.getTotalprice() + 50))
                //.body("depositpaid", equalTo(booking.isDepositpaid()))
                .body("additionalneeds", equalTo("BREAKFAST"));
    }

    @Story("Partial update booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Update additional needs existing booking and verifies if updated")
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

        service.partialUpdateBooking(requestSpec, id, updated)
                .then()
                .statusCode(200)
                .body("firstname", equalTo(booking.getFirstname()))
                .body("additionalneeds", equalTo("BREAKFAST"));
    }

    @Story("Delete booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deletes booking and verifies it cannot be retrieved anymore")
    @Test
    void deleteBookingSuccessfully() {

        Booking booking = BookingDataFactory.createRandomBooking();
        int id = service.createBooking(requestSpec, booking);

        service.deleteBooking(requestSpec, id)
                .then()
                .statusCode(201);

        service.getBooking(requestSpec, id)
                .then()
                .statusCode(404);
    }
}
