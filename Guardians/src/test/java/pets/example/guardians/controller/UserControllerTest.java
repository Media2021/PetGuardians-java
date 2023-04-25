package pets.example.guardians.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import pets.example.guardians.model.User;
import pets.example.guardians.repository.entity.UserEntity;
import pets.example.guardians.services.UserService;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userServiceMock;



    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPhone(1234567890L);
        user.setPassword("hghhg5788");


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date birthdate = dateFormat.parse("15-04-2023");
        user.setBirthdate(birthdate);

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setAddress(user.getAddress());
        userEntity.setPhone(user.getPhone());
        userEntity.setBirthdate(user.getBirthdate());
        userEntity.setPassword(user.getPassword());

        given(userServiceMock.createUser(any(User.class))).willReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.username").value("johndoe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.phone").value(1234567890))
                .andExpect(jsonPath("$.birthdate").value("15-04-2023"));


        verify(userServiceMock, times(1)).createUser(any(User.class));
    }
    @Test
    void createUser_EmptyInput_ThrowsException() throws Exception {
        User user = new User();
        user.setFirstName("");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("");
        user.setPhone(1234567890L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date birthdate = dateFormat.parse("15-04-2023");
        user.setBirthdate(birthdate);

        given(userServiceMock.createUser(any(User.class))).willReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid user data: all user fields including password are required and cannot be empty"));

        verify(userServiceMock, never()).createUser(any(User.class));
    }


    @Test
    void testCreateUserWithDataIntegrityViolationException() throws Exception {

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("password");

        given(userServiceMock.createUser(any(User.class))).willThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))


                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid user data: " ));
    }

    @Test
    void getAllUsers()throws Exception {

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setUsername("johndoe");
        user1.setEmail("johndoe@example.com");
        user1.setAddress("123 Main St");
        user1.setPhone(1234567890L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date birthdate1 = dateFormat.parse("15-04-2023");
        user1.setBirthdate(birthdate1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setUsername("janesmith");
        user2.setEmail("janesmith@example.com");
        user2.setAddress("456 Oak St");
        user2.setPhone(9876543210L);
        Date birthdate2 = dateFormat.parse("20-05-1990");
        user2.setBirthdate(birthdate2);

        List<User> userList = Arrays.asList(user1, user2);

        given(userServiceMock.getAllUsers()).willReturn(userList);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].username").value("johndoe"))
                .andExpect(jsonPath("$[0].email").value("johndoe@example.com"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"))
                .andExpect(jsonPath("$[0].phone").value(1234567890))
                .andExpect(jsonPath("$[0].birthdate").value("15-04-2023"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"))
                .andExpect(jsonPath("$[1].username").value("janesmith"))
                .andExpect(jsonPath("$[1].email").value("janesmith@example.com"))
                .andExpect(jsonPath("$[1].address").value("456 Oak St"))
                .andExpect(jsonPath("$[1].phone").value(9876543210L))
                .andExpect(jsonPath("$[1].birthdate").value("20-05-1990"));

        verify(userServiceMock, times(1)).getAllUsers();
    }
    @Test
    void testGetAllUsersWithEmptyList() throws Exception {
        given(userServiceMock.getAllUsers()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userServiceMock, times(1)).getAllUsers();
    }

    @Test
    void deleteUser() throws Exception {
        Long userId =1L;
        mockMvc.perform(delete("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userServiceMock, times(1)).deleteUser(userId);
    }
    @Test
    void testDeleteUserThrowsException() throws Exception {
        Long userId = 1L;
        doThrow(new NoSuchElementException("no user found ")).when(userServiceMock).deleteUser(userId);

        mockMvc.perform(delete("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userServiceMock, times(1)).deleteUser(userId);
    }

    @Test
    void getUserById()throws Exception {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPhone(1234567890L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date birthdate = dateFormat.parse("15-04-2023");
        user.setBirthdate(birthdate);

        given(userServiceMock.getUserById(userId)).willReturn(Optional.of(user));

        mockMvc.perform(get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.username").value("johndoe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.phone").value(1234567890))
                .andExpect(jsonPath("$.birthdate").value("15-04-2023"));

        verify(userServiceMock, times(1)).getUserById(userId);
    }
    @Test
    void testGetUserByIdWithNoSuchElementException() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("password");

        given(userServiceMock.getUserById(1L)).willThrow(new NoSuchElementException());

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }


    @Test
    void testUpdateUserById()throws Exception {


        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPhone(1234567890L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date birthdate = dateFormat.parse("15-04-2023");
        user.setBirthdate(birthdate);
        user.setPassword("fhjdfh746575");

        UserEntity updatedUserEntity = new UserEntity();
        BeanUtils.copyProperties(user, updatedUserEntity);
        updatedUserEntity.setId(1L);

        given(userServiceMock.updateUser(1L, user)).willReturn(user);

        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());
        verify(userServiceMock, times(1)).updateUser(1L, user);
    }
    @Test
    void testUpdateUserByIdWithNoSuchElementException() throws Exception {

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("password");

        given(userServiceMock.updateUser(anyLong(), any(User.class))).willThrow(new NoSuchElementException());

        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testLoginUser() throws Exception {

        User user = new User();
        user.setUsername("johndoe");
        user.setPassword("password");


        given(userServiceMock.getUserByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .willReturn(user);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.password").value(user.getPassword()));
    }


}