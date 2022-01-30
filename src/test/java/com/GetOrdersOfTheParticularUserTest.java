package com;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class GetOrdersOfTheParticularUserTest {

    private OrderClient orderClient;
    private UserClient userClient;
    private User user;
    String token;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        userClient = new UserClient();
        user = User.getRandom();
    }

    @After
    public void tearDown() {
        userClient.delete(token);
    }

    @Test
    @Description("Получение заказов пользователя с авторизацией")
    public void getAuthorizationUserOrdersTest() {
        // Создание, авторизация и получение токена пользователя
        userClient.create(user);
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        String token = login.extract().path("accessToken");

        // Получаем заказы пользователя
        ValidatableResponse response = orderClient.getAuthorizedUserOrders(token);

        int actualStatusCode = response.extract().statusCode();
        boolean isUserReceivedOrderList = response.extract().path("success");

        assertThat("Ожидаемый статус код 200. Фактический " + actualStatusCode, actualStatusCode, equalTo(200));
        assertTrue("Должно вернуться true, по факту возвращается false. " +
                "Автоизованный пользователь не может получить список заказов", isUserReceivedOrderList);
    }

    @Test
    @Description("Получение заказов пользователя без авторизации")
    public void getNotAuthorizationUserOrdersTest() {
        String expectedErrorMessage = "You should be authorised";

        ValidatableResponse response = orderClient.getUnauthorizedUserOrders();

        int actualStatusCode = response.extract().statusCode();
        boolean isSuccessFalse = response.extract().path("success");
        String actualErrorMessage = response.extract().path("message");

        assertThat(actualStatusCode, equalTo(401));
        assertFalse("Ожидается false, по факту true", isSuccessFalse);
        assertEquals("Ожидаемое сообщение об ошибке " + expectedErrorMessage + ". Фактическое " + actualErrorMessage,
                expectedErrorMessage, actualErrorMessage);
    }

}
