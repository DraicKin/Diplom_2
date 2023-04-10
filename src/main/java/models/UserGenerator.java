package models;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {


    public static String getRandomUserEmail() {
        return String.format(RandomStringUtils.randomAlphabetic(8) + "@" +
                RandomStringUtils.randomAlphabetic(5) + ".ru").toLowerCase();
    }
    public static User getRandom() {
        return new User(getRandomUserEmail(),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10));
    }

    public static User getRandomWithoutEmail() {
        return new User(null,
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10));
    }

    public static User getRandomWithoutPassword() {
        return new User(getRandomUserEmail(),
                null,
                RandomStringUtils.randomAlphabetic(10));
    }

    public static User getRandomWithoutName() {
        return new User(getRandomUserEmail(),
                RandomStringUtils.randomAlphabetic(10),
                null);
    }
}
