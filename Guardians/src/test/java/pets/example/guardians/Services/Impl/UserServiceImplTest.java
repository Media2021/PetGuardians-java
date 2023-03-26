package pets.example.guardians.Services.Impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pets.example.guardians.Model.User;
import pets.example.guardians.Model.UserRole;
import pets.example.guardians.Repository.UserRepo;
import pets.example.guardians.Repository.Entity.UserEntity;


import java.util.*;


import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void createUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("jdoe");
        user.setEmail("jdoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("4321a");
        user.setPhone(1234567890L);
        user.setBirthdate(new Date());
        user.setRole(UserRole.User);

        User result = userServiceImpl.createUser(user);

        verify(userRepo).save(any(UserEntity.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getFirstName(), result.getFirstName());
        Assertions.assertEquals(user.getLastName(), result.getLastName());
        Assertions.assertEquals(user.getUsername(), result.getUsername());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getAddress(), result.getAddress());
        Assertions.assertEquals(user.getPassword(), result.getPassword());
        Assertions.assertEquals(user.getPhone(), result.getPhone());
        Assertions.assertEquals(user.getBirthdate(), result.getBirthdate());
        Assertions.assertEquals(user.getRole(), result.getRole());
    }
    @Test
    void createUserThrowsExceptionWhenUserNameExists() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("jdoe");
        user.setEmail("jdoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("4321a");
        user.setPhone(1234567890L);
        user.setBirthdate(new Date());
        user.setRole(UserRole.User);

        doThrow(new DataIntegrityViolationException("username is already exist")).when(userRepo).save(any(UserEntity.class));

        assertThrows(DataIntegrityViolationException.class, () -> userServiceImpl.createUser(user));
        verify(userRepo).save(any(UserEntity.class));
    }




    @Test
    public void testGetAllUsers() {

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(1L);
        userEntity1.setFirstName("John");
        userEntity1.setLastName("Doe");
        userEntity1.setUsername("jdoe");
        userEntity1.setEmail("jdoe@example.com");
        userEntity1.setAddress("123 Main St");
        userEntity1.setPassword("4321a");
        userEntity1.setPhone(1234567890L);
        userEntity1.setBirthdate(new Date());
        userEntity1.setRole(UserRole.Admin);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2L);
        userEntity2.setFirstName("Jane");
        userEntity2.setLastName("Doe");
        userEntity2.setUsername("jane");
        userEntity2.setEmail("jane@example.com");
        userEntity2.setAddress("456 Main St");
        userEntity2.setPassword("4321ab");
        userEntity2.setPhone(456789012L);
        userEntity2.setBirthdate(new Date());
        userEntity2.setRole(UserRole.User);

        List<UserEntity> userEntities = Arrays.asList(userEntity1, userEntity2);
        when(userRepo.findAll()).thenReturn(userEntities);


        List<User> users = userServiceImpl.getAllUsers();
        assertThat(users).hasSize(2);
        assertThat(users.get(0)).isEqualToComparingFieldByField(new User(1L, "John", "Doe", "jdoe", "jdoe@example.com", "123 Main St", "4321a", 1234567890L, userEntity1.getBirthdate(), UserRole.Admin));

        assertThat(users.get(1)).isEqualToComparingFieldByField(new User(2L, "Jane", "Doe", "jane", "jane@example.com", "456 Main St", "4321ab", 456789012L, userEntity2.getBirthdate(), UserRole.User));
    }
    @Test
    void deleteUser() {
        // Arrange
        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        when(userRepo.findById(id)).thenReturn(Optional.of(userEntity));

        userServiceImpl.deleteUser(id);

        verify(userRepo).delete(userEntity);
    }

    @Test
    void getUserById() {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setUsername("jdoe");
        userEntity.setEmail("johndoe@example.com");
        userEntity.setAddress("123 Main St");
        userEntity.setPassword("4321fdfg");
        userEntity.setPhone(4567890L);
        userEntity.setBirthdate(new Date());
        userEntity.setRole(UserRole.User);


        when(userRepo.findById(1L)).thenReturn(Optional.of(userEntity));


        User user = userServiceImpl.getUserById(1L);


        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("John", user.getFirstName());
        Assertions.assertEquals("Doe", user.getLastName());
        Assertions.assertEquals("jdoe", user.getUsername());
        Assertions.assertEquals("johndoe@example.com", user.getEmail());
        Assertions.assertEquals("123 Main St", user.getAddress());
        Assertions.assertEquals("4321fdfg", user.getPassword());
        Assertions.assertEquals(Long.valueOf(4567890L), user.getPhone());

        assertNotNull(user.getBirthdate());
        Assertions.assertEquals(UserRole.User, user.getRole());

    }
    @Test
    public void testUpdateUser() {
        // Create a User with a specific ID
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("jdoe");
        user.setEmail("jdoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("4321a");
        user.setPhone(1234567890L);
        user.setBirthdate(new Date());
        user.setRole(UserRole.User);

        // Mock the User Repository to return the User with the given ID when findById is called
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setUsername("jdoe");
        userEntity.setEmail("johndoe@example.com");
        userEntity.setAddress("123 Main St");
        userEntity.setPassword("4321a");
        userEntity.setPhone(1234567L);
        userEntity.setBirthdate(new Date());
        userEntity.setRole(UserRole.User);

        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(userEntity));

        // Call the updateUser method in the service
        User updatedUser = userServiceImpl.updateUser(1L, user);

        // Verify that the User Repository's findById method was called with the correct ID
        verify(userRepo).findById(1L);

        // Verify that the User Repository's save method was called with the updated User Entity
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepo).save(argumentCaptor.capture());
        UserEntity capturedUserEntity = argumentCaptor.getValue();
        assertEquals(user.getId(), capturedUserEntity.getId());
        assertEquals(user.getFirstName(), capturedUserEntity.getFirstName());
        assertEquals(user.getLastName(), capturedUserEntity.getLastName());
        assertEquals(user.getEmail(), capturedUserEntity.getEmail());
        assertEquals(user.getAddress(), capturedUserEntity.getAddress());
        assertEquals(user.getUsername(), capturedUserEntity.getUsername());
        assertEquals(user.getBirthdate(), capturedUserEntity.getBirthdate());
        assertEquals(user.getPassword(), capturedUserEntity.getPassword());
        assertEquals(user.getPhone(), capturedUserEntity.getPhone());
        assertEquals(user.getRole(), capturedUserEntity.getRole());

        // Verify that the returned User object is the same as the input User object
        assertSame(updatedUser, user);
    }

    @Test
    public void testUpdateUserNotFound() {
        // Create a User with a specific ID
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("jdoe");
        user.setEmail("jdoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("4321a");
        user.setPhone(1234567890L);
        user.setBirthdate(new Date());
        user.setRole(UserRole.User);

        // Mock the User Repository to return an empty Optional when findById is called
        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.empty());

        // Call the updateUser method in the service and catch the exception
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            userServiceImpl.updateUser(1L, user);
        });

        // Verify that the exception message contains the correct ID
        String expectedMessage = "User with ID 1 not found";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        // Verify that the User Repository's findById method was called with the correct ID
        verify(userRepo).findById(1L);

        // Verify that the User Repository's save method was not called
        verify(userRepo, never()).save(any(UserEntity.class));
    }
    @Test
    void testGetUserByUsernameAndPassword() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setUsername("jdoe");
        userEntity.setEmail("johndoe@example.com");
        userEntity.setAddress("123 Main St");
        userEntity.setPassword("4321a");
        userEntity.setPhone(1234567L);
        userEntity.setBirthdate(new Date());
        userEntity.setRole(UserRole.User);

        when(userRepo.findByUsernameAndPassword("jdoe", "4321a")).thenReturn(Optional.of(userEntity));

        User user = userServiceImpl.getUserByUsernameAndPassword("jdoe", "4321a");

        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("jdoe", user.getUsername());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("4321a", user.getPassword());
        Assertions.assertEquals(Long.valueOf(1234567L), user.getPhone());
        assertNotNull(user.getBirthdate());
        assertEquals(UserRole.User, user.getRole());
    }


}