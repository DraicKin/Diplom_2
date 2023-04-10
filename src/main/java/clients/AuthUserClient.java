package clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.User;
import models.UserCredentials;

import static io.restassured.RestAssured.given;

public class AuthUserClient {
    private final String URI = "https://stellarburgers.nomoreparties.site/api/auth/";

    @Step ("Create new user")
    public ValidatableResponse registerNewUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(URI + "register")
                .then();
    }

    @Step ("Login user")
    public ValidatableResponse loginUser(UserCredentials credentials) {
        return given()
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post(URI + "login")
                .then();
    }

    @Step ("Get user data")
    public ValidatableResponse getUserData(String bearerToken) {
        return given()
                .header("Authorization", bearerToken)
                .when()
                .get(URI + "user")
                .then();
    }

    @Step ("Change user data")
    public ValidatableResponse changeUserData(String bearerToken, User user) {
        return given()
                .header("Authorization", bearerToken)
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .patch(URI + "user")
                .then();
    }

    @Step ("Change user data without authorization")
    public ValidatableResponse changeUserDataNoAuth(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .patch(URI + "user")
                .then();
    }

    @Step ("Delete user")
    public ValidatableResponse deleteUser(String bearerToken) {
        return given()
                .header("Authorization", bearerToken)
                .when()
                .delete(URI + "user")
                .then();
    }


}
