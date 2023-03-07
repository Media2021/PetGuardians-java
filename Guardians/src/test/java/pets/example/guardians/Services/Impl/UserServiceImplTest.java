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

import java.util.Date;


import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

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
    void deleteUser() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void updateUser() {
    }
}