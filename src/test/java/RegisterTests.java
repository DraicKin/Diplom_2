import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.UserGenerator;
import org.junit.Assert;
import org.junit.Test;

import static java.net.HttpURLConnection.*;

public class RegisterTests extends BaseBurgerTest {

    @Test
    @DisplayName("Creates a unique user with all expected fields")
    public void createsUniqueUser() {
        testUser = UserGenerator.getRandom();
        ValidatableResponse createResponse = authUserClient.registerNewUser(testUser);
        Assert.assertEquals("Not valid status code" ,HTTP_OK ,createResponse.extract().statusCode());
        Assert.assertTrue("Creation of user failed", createResponse.extract().body().path("success"));
        token = createResponse.extract().body().path("accessToken");
    }

    @Test
    @DisplayName("Fails to create a non-unique user")
    public void failsToCreateNonUniqueUser() {
        testUser = UserGenerator.getRandom();
        token = authUserClient.registerNewUser(testUser).extract().body().path("accessToken");
        ValidatableResponse createResponse = authUserClient.registerNewUser(testUser);
        Assert.assertEquals("Not valid status code" ,HTTP_FORBIDDEN,createResponse.extract().statusCode());
        Assert.assertFalse("Created a non-unique user", createResponse.extract().body().path("success"));
        Assert.assertEquals("Not valid message" , "User already exists",
                createResponse.extract().body().path("message"));

    }

    @Test
    @DisplayName("Fails to create user without email")
    public void failsToCreateUserWithoutEmail() {
        testUser = UserGenerator.getRandomWithoutEmail();
        ValidatableResponse createResponse = authUserClient.registerNewUser(testUser);
        Assert.assertEquals("Not valid status code" ,HTTP_FORBIDDEN ,createResponse.extract().statusCode());
        Assert.assertFalse("Created a user without email", createResponse.extract().body().path("success"));
        Assert.assertEquals("Not valid message" , "Email, password and name are required fields",
                createResponse.extract().body().path("message"));
    }

    @Test
    @DisplayName("Fails to create user without password")
    public void failsToCreateUserWithoutPassword() {
        testUser = UserGenerator.getRandomWithoutPassword();
        ValidatableResponse createResponse = authUserClient.registerNewUser(testUser);
        Assert.assertEquals("Not valid status code" ,HTTP_FORBIDDEN ,createResponse.extract().statusCode());
        Assert.assertFalse("Created a user without email", createResponse.extract().body().path("success"));
        Assert.assertEquals("Not valid message" , "Email, password and name are required fields",
                createResponse.extract().body().path("message"));
    }

    @Test
    @DisplayName("Fails to create user without name")
    public void failsToCreateUserWithoutName() {
        testUser = UserGenerator.getRandomWithoutName();
        ValidatableResponse createResponse = authUserClient.registerNewUser(testUser);
        Assert.assertEquals("Not valid status code" ,HTTP_FORBIDDEN ,createResponse.extract().statusCode());
        Assert.assertFalse("Created a user without email", createResponse.extract().body().path("success"));
        Assert.assertEquals("Not valid message" , "Email, password and name are required fields",
                createResponse.extract().body().path("message"));
    }
}
