package pets.example.guardians.Services.Impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pets.example.guardians.Repository.Entity.UserEntity;
import pets.example.guardians.Model.User;
import pets.example.guardians.Model.UserRole;
import pets.example.guardians.Repository.UserRepo;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
        user.setName("John");
        user.setLast_name("Doe");
        user.setUser_name("jdoe");
        user.setEmail("jdoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword(4321);
        user.setPhone(1234567890);
        user.setBirthdate(new Date());
        user.setUserRole(UserRole.User);

        User result = userServiceImpl.createUser(user);

        verify(userRepo).save(any(UserEntity.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getName(), result.getName());
        Assertions.assertEquals(user.getLast_name(), result.getLast_name());
        Assertions.assertEquals(user.getUser_name(), result.getUser_name());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getAddress(), result.getAddress());
        Assertions.assertEquals(user.getPassword(), result.getPassword());
        Assertions.assertEquals(user.getPhone(), result.getPhone());
        Assertions.assertEquals(user.getBirthdate(), result.getBirthdate());
        Assertions.assertEquals(user.getUserRole(), result.getUserRole());
    }
    @Test
    void createUserThrowsExceptionWhenUserNameExists() {
        User user = new User();
        user.setName("John");
        user.setLast_name("Doe");
        user.setUser_name("jdoe");
        user.setEmail("jdoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword(4321);
        user.setPhone(1234567890);
        user.setBirthdate(new Date());
        user.setUserRole(UserRole.User);

        doThrow(new DataIntegrityViolationException("username is already exist")).when(userRepo).save(any(UserEntity.class));

        assertThrows(DataIntegrityViolationException.class, () -> userServiceImpl.createUser(user));
        verify(userRepo).save(any(UserEntity.class));
    }




    @Test
    public void testGetAllUsers() {

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(1L);
        userEntity1.setName("John");
        userEntity1.setLast_name("Doe");
        userEntity1.setUser_name("jdoe");
        userEntity1.setEmail("jdoe@example.com");
        userEntity1.setAddress("123 Main St");
        userEntity1.setPassword(123456);
        userEntity1.setPhone(1234567890);
        userEntity1.setBirthdate(new Date());
        userEntity1.setUserRole(UserRole.Admin);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2L);
        userEntity2.setName("Jane");
        userEntity2.setLast_name("Doe");
        userEntity2.setUser_name("jane");
        userEntity2.setEmail("jane@example.com");
        userEntity2.setAddress("456 Main St");
        userEntity2.setPassword(789012);
        userEntity2.setPhone(456789012);
        userEntity2.setBirthdate(new Date());
        userEntity2.setUserRole(UserRole.User);

        List<UserEntity> userEntities = Arrays.asList(userEntity1, userEntity2);
        when(userRepo.findAll()).thenReturn(userEntities);


        List<User> users = userServiceImpl.getAllUsers();
        assertThat(users).hasSize(2);
        assertThat(users.get(0)).isEqualToComparingFieldByField(new User(1L, "jdoe", "Doe", "John", "jdoe@example.com", "123 Main St", 123456, 1234567890, userEntity1.getBirthdate(), UserRole.Admin));
        assertThat(users.get(1)).isEqualToComparingFieldByField(new User(2L, "jane", "Doe", "Jane", "jane@example.com", "456 Main St", 789012, 456789012, userEntity2.getBirthdate(), UserRole.User));
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
        userEntity.setName("John");
        userEntity.setLast_name("Doe");
        userEntity.setUser_name("johndoe");
        userEntity.setEmail("johndoe@example.com");
        userEntity.setAddress("123 Main St");
        userEntity.setPassword(123456);
        userEntity.setPhone(1234567);
        userEntity.setBirthdate(new Date());
        userEntity.setUserRole(UserRole.User);


        when(userRepo.findById(1L)).thenReturn(Optional.of(userEntity));


        User user = userServiceImpl.getUserById(1L);


        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getLast_name());
        assertEquals("johndoe", user.getUser_name());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("123 Main St", user.getAddress());
        assertEquals(123456, user.getPassword());
        assertEquals(1234567, user.getPhone());
        assertNotNull(user.getBirthdate());
        assertEquals(UserRole.User, user.getUserRole());
    }

//    @Test
//    void updateUser() {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setName("John");
//        userEntity.setLast_name("Doe");
//        userEntity.setUser_name("jdoe");
//        userEntity.setEmail("jdoe@example.com");
//        userEntity.setAddress("123 Main St");
//        userEntity.setPassword(1234);
//        userEntity.setPhone(1234567890);
//        userEntity.setBirthdate(new Date());
//        userEntity.setUserRole(UserRole.User);
//        userRepo.save(userEntity);
//
//        // Create a user object with updated values
//        User updatedUser = new User();
//        updatedUser.setName("Jane");
//        updatedUser.setLast_name("Doe");
//        updatedUser.setUser_name("jdoe");
//        updatedUser.setEmail("jdoe@example.com");
//        updatedUser.setAddress("456 Oak St");
//        updatedUser.setPassword(5678);
//        updatedUser.setPhone(987654321);
//        updatedUser.setBirthdate(new Date());
//        updatedUser.setUserRole(UserRole.Admin);
//
//        // Call the updateUser method
//        User result = userServiceImpl.updateUser(userEntity.getId(), updatedUser);
//
//        // Verify that the user entity was updated and saved to the database
//        verify(userRepo).findById(userEntity.getId());
//        verify(userRepo).save(userEntity);
//
//        // Verify that the returned user object has the updated values
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(updatedUser.getName(), result.getName());
//        Assertions.assertEquals(updatedUser.getLast_name(), result.getLast_name());
//        Assertions.assertEquals(updatedUser.getUser_name(), result.getUser_name());
//        Assertions.assertEquals(updatedUser.getEmail(), result.getEmail());
//        Assertions.assertEquals(updatedUser.getAddress(), result.getAddress());
//        Assertions.assertEquals(updatedUser.getPassword(), result.getPassword());
//        Assertions.assertEquals(updatedUser.getPhone(), result.getPhone());
//        Assertions.assertEquals(updatedUser.getBirthdate(), result.getBirthdate());
//        Assertions.assertEquals(updatedUser.getUserRole(), result.getUserRole());
//    }
}