package pets.example.guardians.repository.entity;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pets.example.guardians.model.UserRole;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {


    @Test
    void testDataAnnotation() {
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("password");
        user.setPhone(1234567890L);
        user.setBirthdate(new Date());
        user.setRole(UserRole.USER);

        assertEquals(1, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("johndoe", user.getUsername());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("password", user.getPassword());
        assertEquals(1234567890L, user.getPhone());
        assertNotNull(user.getBirthdate());
        assertEquals(UserRole.USER, user.getRole());
    }
    @Test
  void testEqualsAndHashCode() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(1L);
        userEntity1.setFirstName("John");
        userEntity1.setLastName("Doe");
        userEntity1.setUsername("johndoe");
        userEntity1.setEmail("johndoe@example.com");
        userEntity1.setAddress("123 Main St");
        userEntity1.setPassword("password");
        userEntity1.setPhone(1234567890L);
        userEntity1.setBirthdate(new Date());
        userEntity1.setRole(UserRole.USER);
        userEntity1.setAdoptedPets(new HashSet<>());

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setFirstName("John");
        userEntity2.setLastName("Doe");
        userEntity2.setUsername("johndoe");
        userEntity2.setEmail("johndoe@example.com");
        userEntity2.setAddress("123 Main St");
        userEntity2.setPassword("password");
        userEntity2.setPhone(1234567890L);
        userEntity2.setBirthdate(new Date());
        userEntity2.setRole(UserRole.USER);
        userEntity2.setAdoptedPets(new HashSet<>());

        Assertions.assertEquals(userEntity1, userEntity2);
        Assertions.assertEquals(userEntity1.hashCode(), userEntity2.hashCode());
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
        Set<PetEntity> adoptedPets = new HashSet<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setUsername(username);
        userEntity.setEmail(email);
        userEntity.setAddress(address);
        userEntity.setPassword(password);
        userEntity.setPhone(phone);
        userEntity.setBirthdate(birthdate);
        userEntity.setRole(role);
        userEntity.setAdoptedPets(adoptedPets);

        String expectedToString = "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                ", birthdate=" + birthdate +
                ", role=" + role +
                ", adoptedPets=" + adoptedPets +
                '}';

        Assertions.assertEquals(expectedToString, userEntity.toString());
    }

}