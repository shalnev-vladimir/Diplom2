package com;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class UnsuccessfulCreatingUserWithInvalidDataNegativeTest {

    private final User user;
    private final int expectedStatus;
    private final String expectedErrorMessage;
    private static final String errorMessage = "Email, password and name are required fields";

    // Метод для параметризации
    public UnsuccessfulCreatingUserWithInvalidDataNegativeTest(User user, int expectedStatus, String expectedErrorMessage) {
        this.user = user;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    // Параметризация условий авторизации
    @Parameterized.Parameters
    public static Object[][] getTestData () {
        return new Object[][] {
                {User.getUserWithEmailOnly(), 403, errorMessage},
                {User.getUserWithPasswordOnly(), 403, errorMessage},
                {User.getUserWithNameOnly(), 403, errorMessage},
                {User.getUserWithoutEmail(), 403, errorMessage},
                {User.getUserWithoutPassword(), 403, errorMessage},
                {User.getUserWithoutName(), 403, errorMessage}
        };
    }

    @Test
    @Description("Проверка что пользователя нельзя создать " +
            "1. Только с полем емаил " +
            "2. Только с полем пароль " +
            "3. Только с полем имя" +
            "4. Без поля емаил" +
            "5. Без поля пароль" +
            "6. Без поля имя")

    public void courierNotCreatedWithoutNecessaryField() {

        // Создание курьера
        ValidatableResponse response = new UserClient().create(user);
        // Получение статус кода запроса
        int actualStatusCode = response.extract().statusCode();
        // Получение тела ответа при создании клиента
        boolean isUserNotCreated = response.extract().path("success");
        // Получение значения ключа "Message"
        String actualErrorMessage = response.extract().path("message");

        // Проверка что статус код соответвует ожиданиям
        assertEquals("Ожидаемый статус код " + expectedStatus + ". Фактический " + actualStatusCode,
                expectedStatus, actualStatusCode);
        // Проверка что курьер создался
        assertFalse ("Пользователь создался. Но не должен был.", isUserNotCreated);
        // Проверка что сообщение об ошибке соответвует ожиданиям
        assertEquals("Ожидаемое сообщение об ошибке " + expectedErrorMessage + ". Фактическое" + actualErrorMessage,
                expectedErrorMessage, actualErrorMessage);
    }
}
