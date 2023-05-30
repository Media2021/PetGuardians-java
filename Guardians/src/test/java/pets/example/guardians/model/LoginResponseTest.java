package pets.example.guardians.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class LoginResponseTest {

    @Test
void testGetterSetter() {
        String accessToken = "testAccessToken";
        long userId = 123;

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setUserId(userId);

        Assertions.assertEquals(accessToken, loginResponse.getAccessToken());
        Assertions.assertEquals(userId, loginResponse.getUserId());
    }

    @Test
void testBuilder() {
        String accessToken = "testAccessToken";
        long userId = 123;

        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(accessToken)
                .UserId(userId)
                .build();

        Assertions.assertEquals(accessToken, loginResponse.getAccessToken());
        Assertions.assertEquals(userId, loginResponse.getUserId());
    }
    @Test
    void testDataAnnotation() {
        String accessToken = "abc123";
        long userId = 123456;

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setUserId(userId);

        Assertions.assertEquals(accessToken, loginResponse.getAccessToken());
        Assertions.assertEquals(userId, loginResponse.getUserId());
    }

    @Test
    void testConstructor() {
        String accessToken = "testAccessToken";
        long userId = 123;

        LoginResponse loginResponse = new LoginResponse(accessToken, userId);

        Assertions.assertEquals(accessToken, loginResponse.getAccessToken());
        Assertions.assertEquals(userId, loginResponse.getUserId());
    }

    @Test
    void testEqualsAndHashCode() {
        LoginResponse response1 = new LoginResponse("abc123", 123456);
        LoginResponse response2 = new LoginResponse("abc123", 123456);
        LoginResponse response3 = new LoginResponse("xyz789", 789012);

        Assertions.assertEquals(response1, response2);
        Assertions.assertNotEquals(response1, response3);
        Assertions.assertEquals(response1.hashCode(), response2.hashCode());
        Assertions.assertNotEquals(response1.hashCode(), response3.hashCode());
    }

    @Test
    void testToString() {
        LoginResponse loginResponse = new LoginResponse("testAccessToken", 123);

        String expectedToString = "LoginResponse(accessToken=testAccessToken, UserId=123)";

        Assertions.assertEquals(expectedToString, loginResponse.toString());
    }

}