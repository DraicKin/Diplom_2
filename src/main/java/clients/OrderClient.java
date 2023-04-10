package clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.AllIngredientsResponse;
import models.Ingredient;
import models.IngredientsHash;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private final String URI_INGREDIENTS = "https://stellarburgers.nomoreparties.site/api/ingredients";
    private final String URI_ORDERS = "https://stellarburgers.nomoreparties.site/api/orders";

    @Step ("Get all ingredients available")
    public Ingredient[] getIngredients() {
        return given()
                .when()
                .get(URI_INGREDIENTS)
                .body()
                .as(AllIngredientsResponse.class)
                .getIngredients();
    }

    @Step("Create order with authentication")
    public ValidatableResponse createOrderWithAuth(String bearerToken, IngredientsHash ingredients) {
        return  given()
                .header("Authorization", bearerToken)
                .header("Content-type", "application/json")
                .body(ingredients)
                .when()
                .post(URI_ORDERS)
                .then();
    }

    @Step ("Create order without authentication")
    public ValidatableResponse createOrderWithoutAuth(IngredientsHash ingredients) {
        return  given()
                .header("Content-type", "application/json")
                .body(ingredients)
                .when()
                .post(URI_ORDERS)
                .then();
    }

    @Step ("Get user orders with authentication")
    public ValidatableResponse getUserOrdersWithAuth(String bearerToken) {
        return given()
                .header("Authorization", bearerToken)
                .when()
                .get(URI_ORDERS)
                .then();
    }

    @Step("Get user orders without authentication")
    public ValidatableResponse getUserOrdersWithoutAuth() {
        return given()
                .when()
                .get(URI_ORDERS)
                .then();
    }
}
