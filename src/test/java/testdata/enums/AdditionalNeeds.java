package testdata.enums;

import java.util.Random;

public enum AdditionalNeeds {

    BREAKFAST,
    LUNCH,
    DINNER,
    ALL_INCLUSIVE,
    HALF_BOARD,
    PARKING,
    LATE_CHECKOUT;

    private static final Random random = new Random();

    public static String randomAdditionalNeed() {
        AdditionalNeeds[] values = values();
        return values[random.nextInt(values.length)].name();
    }

}
