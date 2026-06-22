package tests;

import helper.Booking;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import testdata.BookingDataFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utils.AuthTokenManager.loginWithInvalidCredentials;
import static utils.AuthTokenManager.loginWithValidCredentials;

public class AuthTests extends BaseTest {

    @Story("Valid authentication")
    @Test
    void createTokenWithValidCredentials() {
        loginWithValidCredentials()
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Story("Invalid authentication")
    @Test
    void createTokenWithInvalidCredentials() {
        loginWithInvalidCredentials()
                .then()
                .statusCode(200)
                .body("token", nullValue())
                .body("reason", equalTo("Bad credentials"));
    }

    @Story("Update without authorization cookie should fail")
    @Test
    void updateBookingWithoutTokenShouldFail() {

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

        given()
                .spec(requestSpec)
                .body(updated)
                .when()
                .put("/booking/" + id)
                .then()
                .statusCode(403)
                .body(equalTo("Forbidden"));
    }
}
