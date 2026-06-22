package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static config.Config.CONTENT_TYPE;

import static config.Config.BASE_URL;

public class RequestSpecificationFactory { ;

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(CONTENT_TYPE)
                .build();
    }
 }
