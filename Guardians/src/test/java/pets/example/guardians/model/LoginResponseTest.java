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

}