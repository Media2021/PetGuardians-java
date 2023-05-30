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
        Assertions.assertNotNull(user.getAdoptedPets());
        Assertions.assertTrue(user.getAdoptedPets().isEmpty());
    }
    @Test
    void testDataAnnotation() {
        long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String address = "123 Main St";
        String password = "password";
        Long phone = 1234567890L;
        Date birthdate = new Date();
        UserRole role = UserRole.ADMIN;

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
    @Test
    void testEqualsAndHashCode() {
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

        User user1 = new User();
        user1.setId(id);
        user1.setFirstName(firstName);
        user1.setLastName(lastName);
        user1.setUsername(username);
        user1.setEmail(email);
        user1.setAddress(address);
        user1.setPassword(password);
        user1.setPhone(phone);
        user1.setBirthdate(birthdate);
        user1.setRole(role);
        user1.setAdoptedPets(adoptedPets);

        User user2 = new User();
        user2.setId(id);
        user2.setFirstName(firstName);
        user2.setLastName(lastName);
        user2.setUsername(username);
        user2.setEmail(email);
        user2.setAddress(address);
        user2.setPassword(password);
        user2.setPhone(phone);
        user2.setBirthdate(birthdate);
        user2.setRole(role);
        user2.setAdoptedPets(adoptedPets);

        // Self-equality
        Assertions.assertEquals(user1, user1);

        // Equality of two instances with the same properties
        Assertions.assertEquals(user1, user2);
        Assertions.assertEquals(user1.hashCode(), user2.hashCode());

        // Inequality with null
        Assertions.assertNotEquals(user1, null);

        // Inequality with different class
        Assertions.assertNotEquals(user1, new Object());

        // Inequality with different properties
        User user3 = new User();
        user3.setId(2L);
        Assertions.assertNotEquals(user1, user3);
        Assertions.assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testConstructor() {
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

        User user = new User(id, firstName, lastName, username, email, address, password, phone, birthdate, role, adoptedPets);

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
}