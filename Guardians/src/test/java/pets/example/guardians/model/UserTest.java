package pets.example.guardians.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


class UserTest {

    @Test
void testGetterAndSetter() {
        long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String address = "123 Main St";
        String password = "password";
        Long phone = 1234567890L;
        Date birthdate = new Date();
        UserRole role = UserRole.USER;
        Set<Pet> adoptedPets = new HashSet<>();

        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setAddress(address);
        user.setPassword(password);
        user.setPhone(phone);
        user.setBirthdate(birthdate);
        user.setRole(role);
        user.setAdoptedPets(adoptedPets);

        Assertions.assertEquals(id, user.getId());
        Assertions.assertEquals(firstName, user.getFirstName());
        Assertions.assertEquals(lastName, user.getLastName());
        Assertions.assertEquals(username, user.getUsername());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(address, user.getAddress());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertEquals(phone, user.getPhone());
        Assertions.assertEquals(birthdate, user.getBirthdate());
        Assertions.assertEquals(role, user.getRole());
        Assertions.assertEquals(adoptedPets, user.getAdoptedPets());
    }

    @Test
    void testToString() {
        long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String address = "123 Main St";
        String password = "password";
        Long phone = 1234567890L;
        Date birthdate = new Date();
        UserRole role = UserRole.USER;
        Set<Pet> adoptedPets = new HashSet<>();

        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setAddress(address);
        user.setPassword(password);
        user.setPhone(phone);
        user.setBirthdate(birthdate);
        user.setRole(role);
        user.setAdoptedPets(adoptedPets);

        String expectedToString = "User(id=1, firstName=John, lastName=Doe, username=johndoe, email=johndoe@example.com, " +
                "address=123 Main St, password=password, phone=1234567890, birthdate=" + birthdate + ", role=USER, " +
                "adoptedPets=" + adoptedPets + ")";

        Assertions.assertEquals(expectedToString, user.toString());
    }
}