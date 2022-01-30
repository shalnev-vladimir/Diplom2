package com;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserClient extends RestAssuredClient {

    private static final String USER_PATH = "auth";

    @Step("Создание пользователя")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then();
    }

    @Step ("Авторизация пользователя")
    public ValidatableResponse login(UserCredentials user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH + "/login")
                .then();
    }

    @Step
    public ValidatableResponse editInfo(Object object, String token){

        return
                given()
                        .header("Authorization", token)
                        .spec(getBaseSpec())
                        .body(object)
                        .when()
                        .patch(USER_PATH + "/user")
                        .then();

    }

    @Step
    public ValidatableResponse editInfoWithoutToken(Object object) {
        return given()
                .spec(getBaseSpec())
                .body(object)
                .when()
                .patch(USER_PATH + "/user")
                .then();

    }

    @Step ("Изменение информации о пользователе")
    public ValidatableResponse userInfoChange(String token, User user) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(user)
                .when()
                .patch(USER_PATH + "/user")
                .then();
    }

    public void delete(String token) {
        if (token == null) {
            return;
        }
        given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .delete(USER_PATH + "/user")
                .then()
                .statusCode(202);
    }

}

