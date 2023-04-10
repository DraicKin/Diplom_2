import clients.AuthUserClient;
import io.restassured.response.ValidatableResponse;
import models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;

public class BaseBurgerTest {
    protected AuthUserClient authUserClient;
    protected User testUser;
    protected String token;

    @Before
    public void setUp() {
        authUserClient = new AuthUserClient();
    }

    @After
    public void cleanUp() {
        if (token != null) {
            ValidatableResponse deleteResponse = authUserClient.deleteUser(token);
            Assert.assertEquals("User not deleted", HTTP_ACCEPTED, deleteResponse.extract().statusCode());
        }
    }
}
