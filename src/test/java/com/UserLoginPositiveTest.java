package com;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserLoginPositiveTest {

    private UserClient userClient;
    private User user;
    String token;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
    }

    @After
    public void tearDown() {
        userClient.delete(token);
    }

    @Test
    @Description("Проверка что существующий пользователь может авторизоваться")
    public void userCanLogInUsingValidData() {

        int expectedStatusCode = 200;

        // Создание пользователя
        userClient.create(user);
        // Авторизация созданного пользователя
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        // Получение статус кода из тела авторизации пользователя
        int actualStatusCode = login.extract().statusCode();
        // Получение тела ответа при авторизации пользователя
        boolean userLoggedInSuccessfully = login.extract().path("success");
        // Получение токена авторизированого пользователя
        String token = login.extract().path("accessToken");

        // Проверка что статус код соответсвует ожиданиям
        assertEquals("Expected status cod is " + expectedStatusCode + ". But actual is " + actualStatusCode,
                expectedStatusCode, actualStatusCode);
        // Проверка что пользователь авторизовался
        assertTrue ("Authorization attempt failed", userLoggedInSuccessfully);
        // Проверка что токен пользователя не пустой
        assertThat("User access token is null", token, notNullValue());
    }

}

