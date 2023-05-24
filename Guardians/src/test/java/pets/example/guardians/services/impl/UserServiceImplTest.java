package pets.example.guardians.services.impl;


import org.junit.Before;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.security.crypto.password.PasswordEncoder;
import pets.example.guardians.model.User;
import pets.example.guardians.model.UserRole;
import pets.example.guardians.repository.UserRepo;
import pets.example.guardians.repository.entity.UserEntity;
import pets.example.guardians.services.exception.UsernameAlreadyExistsException;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;



    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

       }
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void testCreateUser() {
        User newUser = new User();
        newUser.setUsername("john.doe");
        newUser.setPassword("password");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(newUser, userEntity);
        userEntity.setId(1L);

        when(userRepo.findByUsername("john.doe")).thenReturn(Optional.empty());
        when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);

        User createdUser = userServiceImpl.createUser(newUser);

        verify(userRepo, times(1)).findByUsername("john.doe");
        verify(userRepo, times(1)).save(any(UserEntity.class));
        Assertions.assertEquals(newUser.getUsername(), createdUser.getUsername());

        Assertions.assertNotNull("Created user should have an ID.", String.valueOf(createdUser.getId()));
    }

    @Test
    void testCreateUser_UsernameAlreadyExists() {

        User existingUser = new User();
        existingUser.setUsername("john.doe");
        existingUser.setPassword("password");

        when(userRepo.findByUsername("john.doe")).thenReturn(Optional.of(new UserEntity()));


        assertThrows(UsernameAlreadyExistsException.class, () -> userServiceImpl.createUser(existingUser));

        verify(userRepo, times(1)).findByUsername("john.doe");
        verify(userRepo, never()).save(any(UserEntity.class));
    }


    @Test
     void testSaveNewUser_PasswordHashed() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password");

        userServiceImpl.saveNewUser(user, user.getPassword());

        ArgumentCaptor<UserEntity> argument = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepo).save(argument.capture());

        UserEntity savedUser = argument.getValue();
        Assertions.assertNotEquals("password", savedUser.getPassword());
    }





    @Test
    void createUserThrowsUsernameAlreadyExistsExceptionTest() {
        User existingUser = new User();
        existingUser.setUsername("existing user");


        when(userRepo.findByUsername("existing user")).thenReturn(Optional.of(new UserEntity()));


        assertThrows(UsernameAlreadyExistsException.class, () -> userServiceImpl.createUser(existingUser));


        verify(userRepo, never()).save(any(UserEntity.class));
    }
    @Test
    void testGetAllUsers() {

        List<UserEntity> userEntities = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        user1.setUsername("john.doe");
        userEntities.add(user1);
        UserEntity user2 = new UserEntity();
        user2.setUsername("jane.doe");
        userEntities.add(user2);
        when(userRepo.findAll()).thenReturn(userEntities);

        List<User> users = userServiceImpl.getAllUsers();


        verify(userRepo, times(1)).findAll();
        Assertions.assertEquals(2, users.size());
        Assertions.assertEquals("john.doe", users.get(0).getUsername());
        Assertions.assertEquals("jane.doe", users.get(1).getUsername());
    }
    @Test
    void testGetAllUsers_EmptyList() {

        when(userRepo.findAll()).thenReturn(new ArrayList<>());


        List<User> users = userServiceImpl.getAllUsers();


        verify(userRepo, times(1)).findAll();
        Assertions.assertTrue(users.isEmpty());
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

        User updatedUser = userServiceImpl.updateUser(1L, user);

        verify(userRepo).findById(1L);

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

        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> userServiceImpl.updateUser(1L, user));

        String expectedMessage = "User with ID 1 not found";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        verify(userRepo).findById(1L);

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
    @Test
    void getUserById_UserExists_ReturnsOptionalUser() {

        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        when(userRepo.findById(userId)).thenReturn(Optional.of(userEntity));


        Optional<User> result = userServiceImpl.getUserById(userId);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(Optional.of(userId), Optional.of(result.get().getId()));
        verify(userRepo, times(1)).findById(userId);
    }




}