import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.User;
import models.UserGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;

public class ChangeUserDataTests extends BaseBurgerTest {

    @Before
    public void setUpLocal() {
        testUser = UserGenerator.getRandom();
        token = authUserClient.registerNewUser(testUser).extract().body().path("accessToken");
    }

    @Test
    @DisplayName("Change user data with authentication")
    public void changesUserDataWithAuthorization() {
        testUser = UserGenerator.getRandom();
        ValidatableResponse changeResponse = authUserClient.changeUserData(token, testUser);
        Assert.assertEquals("Not valid status code" ,HTTP_OK, changeResponse.extract().statusCode());
        Assert.assertTrue("Change of user data failed", changeResponse.extract().body().path("success"));
        Assert.assertEquals("Not valid name", testUser.getName(),
                changeResponse.extract().path("user.name"));
        Assert.assertEquals("Not valid email", testUser.getEmail(),
                changeResponse.extract().path("user.email"));
    }

    @Test
    @DisplayName("Fails to change user data without authentication")
    public void notChangesUserDataWithoutAuthorization() {
        User testUser2 = UserGenerator.getRandom();
        ValidatableResponse changeResponse = authUserClient.changeUserDataNoAuth(testUser2);
        Assert.assertEquals("Not valid status code" ,HTTP_UNAUTHORIZED, changeResponse.extract().statusCode());
        Assert.assertFalse("Change of user data failed", changeResponse.extract().body().path("success"));
        // Проверяем, что не поменялось.
        ValidatableResponse getResponse = authUserClient.getUserData(token);
        Assert.assertEquals("Not valid name", testUser.getName(),
                getResponse.extract().body().path("user.name"));
        Assert.assertEquals("Not valid email", testUser.getEmail(),
                getResponse.extract().body().path("user.email"));
    }



}
