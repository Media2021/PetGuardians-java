package pets.example.guardians.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import pets.example.guardians.model.LoginResponse;
import pets.example.guardians.model.User;

import pets.example.guardians.repository.UserRepo;
import pets.example.guardians.repository.entity.UserEntity;
import pets.example.guardians.services.Login;
import pets.example.guardians.services.UserService;

import pets.example.guardians.services.exception.UnauthorizedDataAccessException;


import java.text.SimpleDateFormat;
import java.util.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private Login loginInterface ;

    @MockBean
    private UserRepo userRepoMock;

    @Test
    void testGetAllUsernames() throws Exception {

        List<UserEntity> users = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        user1.setUsername("user1");
        users.add(user1);
        UserEntity user2 = new UserEntity();
        user2.setUsername("user2");
        users.add(user2);
        Mockito.when(userRepoMock.findAll()).thenReturn(users);


        mockMvc.perform(get("/users/users-usernames"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"user1\", \"user2\"]")); // Adjust the expected JSON response as needed
    }

    @Test
    void testGetUsersWithAdoptedPets() throws Exception {

        List<UserEntity> users = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        user1.setUsername("user1");
        users.add(user1);
        UserEntity user2 = new UserEntity();
        user2.setUsername("user2");
        users.add(user2);
        Mockito.when(userService.findAllUsersWithAdoptedPets()).thenReturn(users);


        mockMvc.perform(get("/users/users-with-pets"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"user1\", \"user2\"]")); // Adjust the expected JSON response as needed
    }
    @Test
    void testGetAllUsernames_Exception() throws Exception {
        Mockito.when(userRepoMock.findAll()).thenThrow(new RuntimeException("Error fetching usernames"));

        mockMvc.perform(get("/users/users-usernames"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetUsersWithAdoptedPets_Exception() throws Exception {
        Mockito.when(userService.findAllUsersWithAdoptedPets()).thenThrow(new ServiceException("Error fetching users with adopted pets"));

        mockMvc.perform(get("/users/users-with-pets"))
                .andExpect(status().isInternalServerError());
    }
    @Test
    void createUser_shouldReturn200_whenUserIsValid() throws Exception {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("password");

        when(userService.createUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.address").value(user.getAddress()))
                .andExpect(jsonPath("$.password").value(user.getPassword()));

        verify(userService).createUser(user);
    }

    @Test
    void createUser_shouldReturn400_whenUserIsInvalid() throws Exception {
        User user = new User();
        user.setFirstName("");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid user data: all user fields including password are required and cannot be empty"));

        verifyNoInteractions(userService);
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

        given(userService.createUser(any(User.class))).willReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid user data: all user fields including password are required and cannot be empty"));

        verify(userService, never()).createUser(any(User.class));
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

        given(userService.createUser(any(User.class))).willThrow(DataIntegrityViolationException.class);

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



        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setUsername("janesmith");
        user2.setEmail("janesmith@example.com");
        user2.setAddress("456 Oak St");
        user2.setPhone(9876543210L);



        List<User> userList = Arrays.asList(user1, user2);

        given(userService.getAllUsers()).willReturn(userList);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].username").value("johndoe"))
                .andExpect(jsonPath("$[0].email").value("johndoe@example.com"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"))
                .andExpect(jsonPath("$[0].phone").value(1234567890))

                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"))
                .andExpect(jsonPath("$[1].username").value("janesmith"))
                .andExpect(jsonPath("$[1].email").value("janesmith@example.com"))
                .andExpect(jsonPath("$[1].address").value("456 Oak St"))
                .andExpect(jsonPath("$[1].phone").value(9876543210L));


        verify(userService, times(1)).getAllUsers();
    }
    @Test
    void testGetAllUsersWithEmptyList() throws Exception {
        given(userService.getAllUsers()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void deleteUser_shouldReturn204_whenUserExists() throws Exception {

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("password");
        user.setId(1L);


        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                        .with(user("johndoe").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isNoContent());


        verify(userService).deleteUser(1L);
    }

    @Test
    void deleteUser_shouldReturn404_whenUserDoesNotExist() throws Exception {

        doThrow(new NoSuchElementException("User with ID 1 not found")).when(userService).deleteUser(1L);


        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                        .with(user("johndoe").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isNotFound());


        verify(userService).deleteUser(1L);
    }

    @Test
    //@WithMockUser(username = "admin@fontys.nl", roles = {"ADMIN"})
    void getUserById_shouldReturnUser_whenUserExists() throws Exception {
        // Create a user to retrieve
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("password");
        user.setId(1L);


        when(userService.getUserById(1L)).thenReturn(Optional.of(user));


        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                        .with(user("johndoe").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(user.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(user.getPassword()));


        verify(userService).getUserById(1L);
    }

    @Test
    void getUserById_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {

        when(userService.getUserById(1L)).thenReturn(Optional.empty());


        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                        .with(user("johndoe").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isNotFound());


        verify(userService).getUserById(1L);
    }



    @Test
    void updateUserById_shouldReturnUpdatedUser_whenUserExists() throws Exception {

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("password");
        user.setId(1L);


        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Doe");
        updatedUser.setUsername("janedoe");
        updatedUser.setEmail("janedoe@example.com");
        updatedUser.setAddress("456 Main St");
        updatedUser.setPassword("newpassword");
        when(userService.updateUser(1L, updatedUser)).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .with(user("johndoe").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedUser.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(updatedUser.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(updatedUser.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(updatedUser.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(updatedUser.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(updatedUser.getPassword()));

        verify(userService).updateUser(1L, updatedUser);
    }

    @Test
    void updateUserById_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setPassword("password");
        user.setId(1L);


        when(userService.updateUser(1L, user)).thenThrow(new NoSuchElementException());


        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .with(user("johndoe").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());


        verify(userService).updateUser(1L, user);
    }
    @Test
    void loginUser_shouldReturnAccessToken_whenCredentialsValid() throws Exception {

        User user = new User();
        user.setUsername("validuser");
        user.setPassword("validpassword");

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken("validaccesstoken");
        loginResponse.setUserId(1L);

        when(loginInterface.login(user)).thenReturn(loginResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value("validaccesstoken"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1));

        verify(loginInterface).login(user);
    }


    @Test
    void loginUser_shouldReturnUnauthorized_whenCredentialsInvalid() throws Exception {

        User user = new User();
        user.setUsername("invaliduser");
        user.setPassword("invalidpassword");

        when(loginInterface.login(user)).thenThrow(new UnauthorizedDataAccessException("Invalid credentials"));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        verify(loginInterface).login(user);
    }






}