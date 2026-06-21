package services;

import helper.Booking;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.AuthTokenManager;

import io.qameta.allure.Step;

public class BookingService extends BaseApi {

    private static final String BASE_PATH = "/booking";

    @Step("Create booking with payload: {booking}")
    public int createBooking(RequestSpecification spec, Booking booking) {

        Response response = post(spec, BASE_PATH, booking);

        return response.then()
                .statusCode(200)
                .extract()
                .path("bookingid");
    }

    @Step("Get booking by id: {id}")
    public Response getBooking(RequestSpecification spec, int id) {
        return get(spec, BASE_PATH + "/" + id);
    }

    @Step("Update booking with payload: {booking}")
    public Response updateBooking(RequestSpecification spec, int id, Booking booking) {
        String token = AuthTokenManager.getToken();
        return put(spec, BASE_PATH + "/" + id, booking, token);
    }

    @Step("Delete booking with id: {id}")
    public Response deleteBooking(RequestSpecification spec, int id) {
        String token = AuthTokenManager.getToken();
        return delete(spec, BASE_PATH + "/" + id, token);
    }
}
