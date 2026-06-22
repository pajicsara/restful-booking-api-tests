package testdata;

import com.github.javafaker.Faker;
import helper.BookingDates;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class DataFaker {

    private static final Faker faker = new Faker();

    public static String randomFirstName() {
        return faker.name().firstName();
    }

    public static String randomLastName() {
        return faker.name().lastName();
    }

    public static int randomPrice() {
        return ThreadLocalRandom.current()
                .nextInt(100, 1500);
    }

    public static boolean randomBoolean() {
        return faker.bool().bool();
    }

    public static BookingDates randomBookingDates() {

        LocalDate checkin = LocalDate.now()
                .plusDays(ThreadLocalRandom.current().nextInt(1, 90));

        LocalDate checkout = checkin
                .plusDays(ThreadLocalRandom.current().nextInt(1, 14));

        return new BookingDates(
                checkin.toString(),
                checkout.toString()
        );
    }
}
