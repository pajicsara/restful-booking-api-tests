package utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static config.Config.CONTENT_TYPE;

public class AuthTokenManager {

    private static final String AUTH_ENDPOINT = "https://restful-booker.herokuapp.com/auth";

    private static final String VALID_CREDENTIALS = """
            {
              "username": "admin",
              "password": "password123"
            }
            """;

    private static final String INVALID_CREDENTIALS = """
            {
              "username": "admin",
              "password": "wrongpass"
            }
            """;

    public static Response loginWithValidCredentials() {
        return given()
                .contentType(CONTENT_TYPE)
                .body(VALID_CREDENTIALS)
                .when()
                .post(AUTH_ENDPOINT);
    }

    public static Response loginWithInvalidCredentials() {
        return given()
                .contentType(CONTENT_TYPE)
                .body(INVALID_CREDENTIALS)
                .when()
                .post(AUTH_ENDPOINT);
    }

    public static String getValidToken() {
        return loginWithValidCredentials()
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}
