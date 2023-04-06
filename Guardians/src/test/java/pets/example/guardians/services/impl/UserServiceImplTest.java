package pets.example.guardians.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pets.example.guardians.model.User;
import pets.example.guardians.model.UserRole;
import pets.example.guardians.repository.UserRepo;
import pets.example.guardians.repository.entity.UserEntity;
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
    void createUserTest() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("jdoe");
        user.setEmail("jdoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("4321a");
        user.setPhone(1234567890L);
        user.setBirthdate(new Date());
        user.setRole(UserRole.USER);

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
    void createUserThrowsExceptionWhenUserNameExistsTest() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("jdoe");
        user.setEmail("jdoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("4321a");
        user.setPhone(1234567890L);
        user.setBirthdate(new Date());
        user.setRole(UserRole.USER);

        doThrow(new DataIntegrityViolationException("username is already exist")).when(userRepo).save(any(UserEntity.class));

        assertThrows(DataIntegrityViolationException.class, () -> userServiceImpl.createUser(user));
        verify(userRepo).save(any(UserEntity.class));
    }
    @Test
    void testGetAllUsersTest() {
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
        userEntity1.setRole(UserRole.ADMIN);

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
        userEntity2.setRole(UserRole.USER);

        List<UserEntity> userEntities = Arrays.asList(userEntity1, userEntity2);
        when(userRepo.findAll()).thenReturn(userEntities);

        List<User> users = userServiceImpl.getAllUsers();
        assertThat(users).hasSize(2);
        assertThat(users.get(0)).isEqualToComparingFieldByField(new User(1L, "John", "Doe", "jdoe", "jdoe@example.com", "123 Main St", "4321a", 1234567890L, userEntity1.getBirthdate(), UserRole.ADMIN));

        assertThat(users.get(1)).isEqualToComparingFieldByField(new User(2L, "Jane", "Doe", "jane", "jane@example.com", "456 Main St", "4321ab", 456789012L, userEntity2.getBirthdate(), UserRole.USER));
    }
    @Test
    void deleteUserTest() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        when(userRepo.findById(id)).thenReturn(Optional.of(userEntity));

        userServiceImpl.deleteUser(id);

        verify(userRepo).delete(userEntity);
    }
    @Test
    void deleteUser_UserNotFoundTest() {
        Long id = 1L;
        when(userRepo.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () ->
                userServiceImpl.deleteUser(id));

        String expectedMessage = "User with id " + id + " not found";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void getUserByIdTest() {
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
        userEntity.setRole(UserRole.USER);

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

        Assertions.assertNotNull(user.getBirthdate());
        Assertions.assertEquals(UserRole.USER, user.getRole());
    }
    @Test
    void testGetUserById_ThrowsExceptionTest() {
        Long userId = 1L;
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () ->
                userServiceImpl.getUserById(userId));

        String expectedMessage = "User with id " + userId + " not found";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void testUpdateUserTest() {
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
        user.setRole(UserRole.USER);
        // Mock the User repository to return the User with the given ID when findById is called
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
        userEntity.setRole(UserRole.USER);
        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(userEntity));
        // Call the updateUser method in the service
        User updatedUser = userServiceImpl.updateUser(1L, user);
        // Verify that the User repository's findById method was called with the correct ID
        verify(userRepo).findById(1L);
        // Verify that the User repository's save method was called with the updated User entity
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepo).save(argumentCaptor.capture());
        UserEntity capturedUserEntity = argumentCaptor.getValue();
        Assertions.assertEquals(user.getId(), capturedUserEntity.getId());
        Assertions.assertEquals(user.getFirstName(), capturedUserEntity.getFirstName());
        Assertions.assertEquals(user.getLastName(), capturedUserEntity.getLastName());
        Assertions.assertEquals(user.getEmail(), capturedUserEntity.getEmail());
        Assertions.assertEquals(user.getAddress(), capturedUserEntity.getAddress());
        Assertions.assertEquals(user.getUsername(), capturedUserEntity.getUsername());
        Assertions.assertEquals(user.getBirthdate(), capturedUserEntity.getBirthdate());
        Assertions.assertEquals(user.getPassword(), capturedUserEntity.getPassword());
        Assertions.assertEquals(user.getPhone(), capturedUserEntity.getPhone());
        Assertions.assertEquals(user.getRole(), capturedUserEntity.getRole());
        // Verify that the returned User object is the same as the input User object
        Assertions.assertSame(updatedUser, user);
    }
    @Test
   void testUpdateUserNotFoundTest() {
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
        user.setRole(UserRole.USER);
        // Mock the User repository to return an empty Optional when findById is called
        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.empty());
        // Call the updateUser method in the service and catch the exception
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> userServiceImpl.updateUser(1L, user));
        // Verify that the exception message contains the correct ID
        String expectedMessage = "User with ID 1 not found";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
        // Verify that the User repository's findById method was called with the correct ID
        verify(userRepo).findById(1L);
        // Verify that the User repository's save method was not called
        verify(userRepo, never()).save(any(UserEntity.class));
    }
    @Test
    void testGetUserByUsernameAndPasswordTest() {
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
        userEntity.setRole(UserRole.USER);

        when(userRepo.findByUsernameAndPassword("jdoe", "4321a")).thenReturn(Optional.of(userEntity));

        User user = userServiceImpl.getUserByUsernameAndPassword("jdoe", "4321a");

        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("John", user.getFirstName());
        Assertions.assertEquals("Doe", user.getLastName());
        Assertions.assertEquals("jdoe", user.getUsername());
        Assertions.assertEquals("johndoe@example.com", user.getEmail());
        Assertions.assertEquals("123 Main St", user.getAddress());
        Assertions.assertEquals("4321a", user.getPassword());
        Assertions.assertEquals(Long.valueOf(1234567L), user.getPhone());
        Assertions.assertNotNull(user.getBirthdate());
        Assertions.assertEquals(UserRole.USER, user.getRole());
    }
    @Test
    void testGetUserByUsernameAndPasswordWhenUserNotFoundTest() {
        when(userRepo.findByUsernameAndPassword("jdoe", "4321a")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> userServiceImpl.getUserByUsernameAndPassword("jdoe", "4321a"));
    }
}