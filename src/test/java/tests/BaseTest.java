package tests;

import config.RequestSpecificationFactory;
import io.restassured.specification.RequestSpecification;
import services.BookingService;

public class BaseTest {

    protected static final RequestSpecification requestSpec =
            RequestSpecificationFactory.getRequestSpecification();

    protected static final BookingService service = new BookingService();
}
