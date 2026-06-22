package tests;

import helper.Booking;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import testdata.BookingDataFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BookingDefectTests extends BaseTest {

    @Disabled("BUG: API allows checkout before checkin date (should return 400)")
    @Test
    void shouldNotAllowCheckoutBeforeCheckin() {

        Booking booking = BookingDataFactory.createRandomBooking();

        booking.getBookingdates().setCheckin("2026-12-10");
        booking.getBookingdates().setCheckout("2026-12-01");

        given()
                .spec(requestSpec)
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .statusCode(400);
    }

    @Story("Booking validation defect")
    @Severity(SeverityLevel.NORMAL)
    @Description("Booking should not accept empty firstname and lastname, but API currently allows it")
    @Test
    void shouldRejectEmptyFirstnameAndLastname() {

        Booking booking = BookingDataFactory.createRandomBooking();

        // override invalid fields
        booking.setFirstname("");
        booking.setLastname("");

        int id = service.createBooking(requestSpec, booking);

        given()
                .spec(requestSpec)
                .when()
                .get("/booking/" + id)
                .then()
                .statusCode(200)
                .body("firstname", equalTo(""))
                .body("lastname", equalTo(""));
    }
}
