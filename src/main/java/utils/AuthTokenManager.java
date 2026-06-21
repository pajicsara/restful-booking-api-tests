package utils;

import static io.restassured.RestAssured.given;

public class AuthTokenManager {

    public static String getToken() {
        String body = """
                {
                "username": "admin",
                "password": "password123"
                }
                """;

        return given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}
