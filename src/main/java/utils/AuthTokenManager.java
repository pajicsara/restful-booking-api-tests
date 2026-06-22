package utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthTokenManager {

    private static final String AUTH_ENDPOINT = "https://restful-booker.herokuapp.com/auth";

    private static final String VALID_BODY = """
            {
              "username": "admin",
              "password": "password123"
            }
            """;

    public static Response loginWithValidCredentials() {
        return given()
                .contentType("application/json")
                .body(VALID_BODY)
                .when()
                .post(AUTH_ENDPOINT);
    }

    public static Response loginWithInvalidCredentials() {

        String invalidBody = """
                {
                  "username": "admin",
                  "password": "wrongpass"
                }
                """;

        return given()
                .contentType("application/json")
                .body(invalidBody)
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
