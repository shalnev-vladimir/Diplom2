package com;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class UserCredentials {

    public String email;
    public String password;
    public String name;

    public UserCredentials() {

    }

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user.email, user.password);
    }

    public UserCredentials setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserCredentials setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserCredentials setName(String name) {
        this.name = name;
        return this;
    }

    public static UserCredentials getUserWithEmail(User user) {
        return new UserCredentials().setEmail(user.email);
    }

    public static UserCredentials getUserWithPassword(User user) {
        return new UserCredentials().setPassword(user.password);
    }

    public static UserCredentials getUserWithPasswordEmailAndName(User user) {
        return new UserCredentials().setPassword(user.password).setEmail(user.email).setName(user.name);
    }

    public static UserCredentials getUserWithRandomEmailAndPassword() {
        return new UserCredentials().setEmail(randomAlphabetic(10))
                .setPassword(randomAlphabetic(10));
    }

    public static String getUserEmail() {
        return randomAlphabetic(10).toLowerCase() + "@yandex.ru";
    }

    public static String getUserPassword() {
        return randomAlphabetic(10).toLowerCase();
    }

    public static String getUserName() {
        return randomAlphabetic(10).toLowerCase();
    }

}

