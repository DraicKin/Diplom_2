import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.UserCredentials;
import models.UserGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;

public class LoginTests  extends BaseBurgerTest {
    private UserCredentials userCredentials;

    @Before
    public void setUpLocal() {
        testUser = UserGenerator.getRandom();
        token = authUserClient.registerNewUser(testUser).extract().body().path("accessToken");
        userCredentials = new UserCredentials(testUser.getEmail(), testUser.getPassword());
    }

    @Test
    @DisplayName("Login existing user")
    public void loginExistingUser() {
        ValidatableResponse loginResponse = authUserClient.loginUser(userCredentials);
        Assert.assertEquals("Invalid statusCode", HTTP_OK, loginResponse.extract().statusCode());
        Assert.assertTrue("Login of user failed", loginResponse.extract().body().path("success"));
    }

    @Test
    @DisplayName("Fails to login a user that does not exist")
    public void loginNotExistingUser() {
        userCredentials.setEmail(UserGenerator.getRandomUserEmail());
        ValidatableResponse loginResponse = authUserClient.loginUser(userCredentials);
        Assert.assertEquals("Invalid statusCode", HTTP_UNAUTHORIZED, loginResponse.extract().statusCode());
        Assert.assertFalse("Login not existing user", loginResponse.extract().body().path("success"));
        Assert.assertEquals("Not valid message" , "email or password are incorrect",
                loginResponse.extract().body().path("message"));
    }
}
