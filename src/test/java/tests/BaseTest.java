package tests;

import config.RequestSpecificationFactory;
import io.restassured.specification.RequestSpecification;

public class BaseTest {

    protected RequestSpecification requestSpec =
            RequestSpecificationFactory.getRequestSpecification();
}
