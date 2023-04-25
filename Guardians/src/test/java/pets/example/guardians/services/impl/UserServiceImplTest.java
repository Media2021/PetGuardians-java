package pets.example.guardians.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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
        user.setUsername("tester");


        when(userRepo.findByUsername("tester")).thenReturn(Optional.empty());


        UserEntity savedUserEntity = new UserEntity();
        savedUserEntity.setId(1L);
        savedUserEntity.setUsername("tester");

        when(userRepo.save(Mockito.any(UserEntity.class))).thenReturn(savedUserEntity);


        User createdUser = userServiceImpl.createUser(user);
        Assertions.assertEquals(user.getUsername(), createdUser.getUsername());



        ArgumentCaptor<UserEntity> argument = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepo).save(argument.capture());
        Assertions.assertEquals(user.getUsername(), argument.getValue().getUsername());

    }


    @Test
    void createUserThrowsUsernameAlreadyExistsExceptionTest() {
        User existingUser = new User();
        existingUser.setUsername("existing user");


        when(userRepo.findByUsername("existing user")).thenReturn(Optional.of(new UserEntity()));


        assertThrows(UserServiceImpl.UsernameAlreadyExistsException.class, () -> userServiceImpl.createUser(existingUser));


        verify(userRepo, never()).save(any(UserEntity.class));
    }

    @Test
    public void testGetAllUsers() {
        // create a mock list of user entities
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(new UserEntity());

        // mock the userRepo.findAll() method to return the mock list of user entities
        Mockito.when(userRepo.findAll()).thenReturn(userEntities);

        // call the getAllUsers() method
        List<User> users = userServiceImpl.getAllUsers();

        // assert that the returned list of users is not null and contains one user with the expected values
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(1L, users.get(0).getId());
        assertEquals("John", users.get(0).getFirstName());
        assertEquals("Doe", users.get(0).getLastName());
        assertEquals("johndoe", users.get(0).getUsername());
        assertEquals("johndoe@example.com", users.get(0).getEmail());
        assertEquals("123 Main St", users.get(0).getAddress());
        assertEquals("password", users.get(0).getPassword());
        assertEquals(Optional.of(5551234L), users.get(0).getPhone());
        assertEquals(UserRole.USER, users.get(0).getRole());
        assertNotNull(users.get(0).getBirthdate());
        assertNotNull(users.get(0).getAdoptedPets());
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
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setFirstName("John Doe");
        userEntity.setEmail("john.doe@example.com");
        when(userRepo.findById(userId)).thenReturn(Optional.of(userEntity));

        Optional<User> userOpt = userServiceImpl.getUserById(userId);
        assertTrue(userOpt.isPresent());

        User user = userOpt.get();
        assertEquals(Optional.of(userId), user.getId());
        assertEquals("John Doe", user.getFirstName());
        assertEquals("john.doe@example.com", user.getEmail());
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