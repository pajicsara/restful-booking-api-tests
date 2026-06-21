package services;

import helper.Booking;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.AuthTokenManager;


public class BookingService extends BaseApi {

    private static final String BASE_PATH = "/booking";

    public int createBooking(RequestSpecification spec, Booking booking) {

        Response response = post(spec, BASE_PATH, booking);

        return response.then()
                .statusCode(200)
                .extract()
                .path("bookingid");
    }

    public Response getBooking(RequestSpecification spec, int id) {
        return get(spec, BASE_PATH + "/" + id);
    }

    public Response updateBooking(RequestSpecification spec, int id, Booking booking) {

        String token = AuthTokenManager.getToken();

        return put(spec, BASE_PATH + "/" + id, booking, token);
    }

    public Response deleteBooking(RequestSpecification spec, int id) {

        String token = AuthTokenManager.getToken();

        return delete(spec, BASE_PATH + "/" + id, token);
    }
}
