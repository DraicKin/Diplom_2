import clients.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Ingredient;
import models.IngredientsHash;
import models.UserGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;


public class OrderTests extends BaseBurgerTest {

    private OrderClient orderClient = new OrderClient();
    private String token;
    private Ingredient[] allIngredients;
    private IngredientsHash ingredientsForOrder;
    @Before
    public void setUpLocal() {
        testUser = UserGenerator.getRandom();
        token = authUserClient.registerNewUser(testUser).extract().body().path("accessToken");
        allIngredients = orderClient.getIngredients();
    }

    @Test
    @DisplayName("Creates order with authentication")
    public void createsOrderWithAuth() {
        ingredientsForOrder = new IngredientsHash(new String[] {
                allIngredients[0].get_id(),
                allIngredients[1].get_id()
        });
        ValidatableResponse createResponse = orderClient.createOrderWithAuth(token, ingredientsForOrder);
        Assert.assertEquals("Invalid Status Code", HTTP_OK, createResponse.extract().statusCode());
        Assert.assertTrue("Did not create an order", createResponse.extract().body().path("success"));
    }

    @Test
    @DisplayName("Creates order without authentication")
    public void createsOrderWithoutAuth() {
        ingredientsForOrder = new IngredientsHash(new String[] {
                allIngredients[0].get_id(),
                allIngredients[1].get_id()
        });
        ValidatableResponse createResponse = orderClient.createOrderWithoutAuth(ingredientsForOrder);
        Assert.assertEquals("Invalid Status Code", HTTP_OK, createResponse.extract().statusCode());
        Assert.assertTrue("Did not create an order", createResponse.extract().body().path("success"));
    }

    @Test
    @DisplayName("Fails to create order without ingredients with authentication")
    public void notCreatesOrderWithoutIngredientsWithAuth() {
        ingredientsForOrder = new IngredientsHash(new String[] {});
        ValidatableResponse createResponse = orderClient.createOrderWithAuth(token, ingredientsForOrder);
        Assert.assertEquals("Invalid Status Code", HTTP_BAD_REQUEST, createResponse.extract().statusCode());
        Assert.assertFalse("Did create an order", createResponse.extract().body().path("success"));
    }

    @Test
    @DisplayName("Fails to create order with wrong ingredient hash with authentication")
    public void notCreatesOrderWithWrongHash() {
        ingredientsForOrder = new IngredientsHash(new String[] {
                RandomStringUtils.randomAlphabetic(10)
        });
        ValidatableResponse createResponse = orderClient.createOrderWithAuth(token, ingredientsForOrder);
        Assert.assertEquals("Invalid Status Code", HTTP_INTERNAL_ERROR, createResponse.extract().statusCode());
    }

    @Test
    @DisplayName("Get user orders with authentication")
    public void getsUserOrdersWithAuth() {
        ValidatableResponse getOrdersResponse = orderClient.getUserOrdersWithAuth(token);
        Assert.assertEquals("Invalid Status Code", HTTP_OK, getOrdersResponse.extract().statusCode());
        Assert.assertTrue("Did not get an order", getOrdersResponse.extract().body().path("success"));
        Assert.assertNotNull("Did not get an order", getOrdersResponse.extract().body().path("orders"));
    }

    @Test
    @DisplayName("Fails to get user orders with authentication")
    public void notGetsUserOrdersWithoutAuth() {
        ValidatableResponse getOrdersResponse = orderClient.getUserOrdersWithoutAuth();
        Assert.assertEquals("Invalid Status Code", HTTP_UNAUTHORIZED, getOrdersResponse.extract().statusCode());
        Assert.assertFalse("Did get an order", getOrdersResponse.extract().body().path("success"));
    }


}
