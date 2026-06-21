package testdata;

import helper.Booking;
import helper.BookingDates;
import testdata.enums.AdditionalNeeds;

import javax.xml.crypto.Data;

public class BookingDataFactory {

    public static Booking createRandomBooking() {

        //see could checkOut date could be always after checkIn date
        BookingDates dates = new BookingDates(
                DataFaker.randomFutureDate(),
                DataFaker.randomFutureDate()
        );

        return new Booking(
                DataFaker.randomFirstName(),
                DataFaker.randomLastName(),
                DataFaker.randomPrice(),
                DataFaker.randomBoolean(),
                dates,
                AdditionalNeeds.randomAdditionalNeed()
        );
    }
}
