package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static config.Config.BASE_URL;

public class RequestSpecificationFactory {

    private static final String APPLICATION_JSON = "application/json";

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(APPLICATION_JSON)
                .build();
    }
 }
