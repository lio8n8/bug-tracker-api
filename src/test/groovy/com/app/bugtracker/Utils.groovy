package com.app.bugtracker

import com.app.bugtracker.configs.ApplicationConfigs
import com.app.bugtracker.users.dto.UserCreateRequest
import com.app.bugtracker.users.models.User
import com.github.javafaker.Faker

class Utils {
    public static Faker faker = new Faker()
    public static Random random = new Random()

    public static User getUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .psw(faker.internet().password())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .locked(false)
                .build()
    }

    public static List<User> getUsers() {
        int x = getRandomInteger(42)
        List<User> users = new ArrayList<>(x)

        for (int i = 0; i < x; i++) {
            users.add(getUser())
        }

        return users
    }

    public static getCreateUserRequest() {
        String psw = faker.internet().password()

        return UserCreateRequest.builder()
                .email(faker.internet().emailAddress())
                .username(faker.name().username())
                .password(psw)
                .confirmPassword(psw)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build()
    }

    public static int getRandomInteger(Integer x) {
        return random.nextInt(x)
    }

    public static String getRandomWord() {
        return faker.lorem().word()
    }

    public static ApplicationConfigs getApplicationConfigs() {
        return new ApplicationConfigs().with {
            tokenConfigs = new ApplicationConfigs.TokenConfigs().builder()
                    .secretKey('secretKey')
                    .expiredIn(3600000)
                    .build()

            it
        }
    }
}
