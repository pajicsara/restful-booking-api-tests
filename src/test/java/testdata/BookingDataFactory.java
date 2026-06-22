package testdata;

import helper.Booking;
import testdata.enums.AdditionalNeeds;

public class BookingDataFactory {

    public static Booking createRandomBooking() {

        return new Booking(
                DataFaker.randomFirstName(),
                DataFaker.randomLastName(),
                DataFaker.randomPrice(),
                DataFaker.randomBoolean(),
                DataFaker.randomBookingDates(),
                AdditionalNeeds.randomAdditionalNeed()
        );
    }
}
