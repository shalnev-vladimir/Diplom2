package com;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UserLoginWithoutRequiredFieldNegativeTest {
    private static final UserClient userClient = new UserClient ();
    private static final User user = User.getRandom ();
    private final int expectedStatus;
    private final String expectedErrorMessage;
    private final UserCredentials userCredentials;
    private final static String errorMessage = "email or password are incorrect";


    public UserLoginWithoutRequiredFieldNegativeTest(UserCredentials userCredentials, int expectedStatus, String expectedErrorMessage) {
        this.userCredentials = userCredentials;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {UserCredentials.getUserWithEmail(user), 401, errorMessage},
                {UserCredentials.getUserWithPassword(user), 401, errorMessage},
                {UserCredentials.getUserWithRandomEmailAndPassword(), 401, errorMessage}
        };
    }

    @Test
    @Description("Проверка что пользователь не может авторизоваться " +
            "1. Только с логином " +
            "2. Только с паролем " +
            "3. С рандомным логином и паролем ")

    public void userLoginWithoutNecessaryField() {

        // Создаем пользователя
        userClient.create(user);
        // Логинимся под созданым пользователм
        userClient.login(UserCredentials.from(user));
        // Пытаемся авторизоваться с данными из условия
        ValidatableResponse login = new UserClient().login(userCredentials);
        // Получение статус кода из тела ответа
        int ActualStatusCode = login.extract().statusCode();
        // Получение сообщения об ошибке из тела ответа
        String errorMessage = login.extract().path("message");

        // Проверяем что статус код соответствует ожидаемому
        assertEquals ("Status code is incorrect", expectedStatus, ActualStatusCode);
        // Проверяем что сообщение об ошибке соответствует ожидаемому
        assertEquals ("Error message is incorrect", expectedErrorMessage, errorMessage);
    }

}

