package com;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestAssuredClient {

    private static final String INGREDIENTS_PATH = "ingredients";

    @Step
    public ValidatableResponse getIngredients() {

        return given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .then();
    }

}
