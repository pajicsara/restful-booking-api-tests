package testdata;

import com.github.javafaker.Faker;

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

    public static String randomFutureDate() {
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = LocalDate.now().plusMonths(6);

        long randomDay = ThreadLocalRandom.current()
                .nextLong(start.toEpochDay(), end.toEpochDay());

        return LocalDate.ofEpochDay(randomDay).toString();
    }
}
