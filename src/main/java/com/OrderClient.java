package com;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "orders";

    @Step("Получить заказы конкретного пользователя c авторизацией")
    public ValidatableResponse getAuthorizedUserOrders(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .headers("Authorization", accessToken)
                .when()
                .get(BASE_URL + ORDER_PATH)
                .then();
    }

    @Step("Получить заказы конкретного пользователя без авторизации")
    public ValidatableResponse getUnauthorizedUserOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(BASE_URL + ORDER_PATH)
                .then();
    }

    @Step("Создать заказ с токеном")
    public ValidatableResponse createOrderWithToken1(IngredientsHashes ingredientsHashes, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .headers("Authorization", accessToken)
                .body(ingredientsHashes)
                .when()
                .post(BASE_URL + ORDER_PATH)
                .then();
    }

}
