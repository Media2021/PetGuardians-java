package pets.example.guardians.model;

import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class AccessTokenTest {

    @Test
    void hasRole() {

        AccessToken yourObject = new AccessToken ();

        yourObject.setRoles(List.of("ADMIN", "USER"));


        assertTrue(yourObject.hasRole("ADMIN"));


        assertFalse(yourObject.hasRole("moderator"));


        yourObject.setRoles(null);
        assertFalse(yourObject.hasRole("ADMIN"));
    }
}