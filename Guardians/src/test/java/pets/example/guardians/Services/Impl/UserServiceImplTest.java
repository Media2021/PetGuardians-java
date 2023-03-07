package pets.example.guardians.Services.Impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pets.example.guardians.Entity.UserEntity;
import pets.example.guardians.Model.User;
import pets.example.guardians.Model.UserRole;
import pets.example.guardians.Repository.UserRepo;


import java.util.Arrays;
import java.util.Date;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
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

        doThrow(new DataIntegrityViolationException("duplicate key value violates unique constraint")).when(userRepo).save(any(UserEntity.class));

        assertThrows(DataIntegrityViolationException.class, () -> userServiceImpl.createUser(user));
        verify(userRepo).save(any(UserEntity.class));
    }


//    @Test
//    void getAllUsers() {
//
//        User user1 = new User();
//
//        user1.setName("John");
//        user1.setLast_name("Doe");
//        user1.setUser_name("jdoe");
//        user1.setEmail("jdiue@example.com");
//        user1.setAddress("123 Main St");
//        user1.setPassword(4321);
//        user1.setPhone(1267890);
//        user1.setBirthdate(new Date());
//        user1.setUserRole(UserRole.User);
//        userServiceImpl.createUser(user1);
//
//        User user2 = new User();
//
//        user2.setName("Jane");
//        user2.setLast_name("Doe");
//        user2.setUser_name("jane");
//        user2.setEmail("januiuie@example.com");
//        user2.setAddress("456 Main St");
//        user2.setPassword(1234);
//        user2.setPhone(98754321);
//        user2.setBirthdate(new Date());
//        user2.setUserRole(UserRole.Admin);
//        userServiceImpl.createUser(user2);
//
//        List<User> userList = userServiceImpl.getAllUsers();
//        Assertions.assertNotNull(userList);
//        Assertions.assertEquals(2, userList.size());
//    }

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
    }

    @Test
    void getUserById() {
    }

    @Test
    void updateUser() {
    }
}