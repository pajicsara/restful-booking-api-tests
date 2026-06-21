package services;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseApi {

    protected Response post(RequestSpecification spec, String endpoint, Object body) {
        return given()
                .log().all()
                .spec(spec)
                .body(body)
                .when()
                .post(endpoint);
    }

    protected Response get(RequestSpecification spec, String endpoint) {
        return given()
                .log().all()
                .spec(spec)
                .when()
                .get(endpoint);
    }

    protected Response put(RequestSpecification spec, String endpoint, Object body, String token) {
        return given()
                .log().all()
                .spec(spec)
                .header("Cookie", "token=" + token)
                .body(body)
                .when()
                .put(endpoint);
    }

    protected Response delete(RequestSpecification spec, String endpoint, String token) {
        return given()
                .log().all()
                .spec(spec)
                .header("Cookie", "token=" + token)
                .when()
                .delete(endpoint);
    }
}
