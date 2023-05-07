//package pets.example.guardians.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.junit.Before;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.MediaType;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import pets.example.guardians.model.User;
//import pets.example.guardians.model.UserRole;
//import pets.example.guardians.repository.UserRepo;
//import pets.example.guardians.repository.entity.UserEntity;
//import pets.example.guardians.services.AccessTokenDecoder;
//import pets.example.guardians.services.AccessTokenEncoder;
//import pets.example.guardians.services.Login;
//import pets.example.guardians.services.UserService;
//import pets.example.guardians.services.impl.AccessTokenEncoderDecoderImpl;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.util.AssertionErrors.assertTrue;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
//
//class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @MockBean
//    private UserService userService;
//    @MockBean
//    private UserController userController;
//
//    @MockBean
//    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private WebApplicationContext wac;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//    @Before
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(wac)
//                .apply(springSecurity()) // add Spring Security support
//                .build();
//    }
//
//
//    @Configuration
//    public static class TestConfig2 {
//        @Bean
//        public AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder() {
//            return Mockito.mock(AccessTokenEncoderDecoderImpl.class);
//        }
//    }
//
//    @Test
//    public void testCreateUser() throws Exception {
//        // Log in as an existing user
//        mockMvc.perform(MockMvcRequestBuilders.post("/login")
//                        .param("username", "admin1")
//                        .param("password", "password123"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // Create a new user
//        User user = new User();
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setUsername("johndoe");
//        user.setEmail("johndoe@example.com");
//        user.setAddress("123 Main St");
//        user.setPassword("password");
//
//        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(user))
//                        .with(user("admin1").password("password123").roles("ADMIN"))
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(user.getAddress()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.password").doesNotExist());
//    }
//
//
//
//    @Test
//    void createUser_EmptyInput_ThrowsException() throws Exception {
//        User user = new User();
//        user.setFirstName("");
//        user.setLastName("Doe");
//        user.setUsername("johndoe");
//        user.setEmail("johndoe@example.com");
//        user.setAddress("");
//        user.setPhone(1234567890L);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        Date birthdate = dateFormat.parse("15-04-2023");
//        user.setBirthdate(birthdate);
//
//        given(userService.createUser(any(User.class))).willReturn(user);
//
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(user)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Invalid user data: all user fields including password are required and cannot be empty"));
//
//        verify(userService, never()).createUser(any(User.class));
//    }
//
//
//    @Test
//    void testCreateUserWithDataIntegrityViolationException() throws Exception {
//
//        User user = new User();
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setUsername("johndoe");
//        user.setEmail("johndoe@example.com");
//        user.setAddress("123 Main St");
//        user.setPassword("password");
//
//        given(userService.createUser(any(User.class))).willThrow(DataIntegrityViolationException.class);
//
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(user)))
//
//
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Invalid user data: " ));
//    }
//
//    @Test
//    void getAllUsers()throws Exception {
//
//        User user1 = new User();
//        user1.setFirstName("John");
//        user1.setLastName("Doe");
//        user1.setUsername("johndoe");
//        user1.setEmail("johndoe@example.com");
//        user1.setAddress("123 Main St");
//        user1.setPhone(1234567890L);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        Date birthdate1 = dateFormat.parse("15-04-2023");
//        user1.setBirthdate(birthdate1);
//
//        User user2 = new User();
//        user2.setFirstName("Jane");
//        user2.setLastName("Smith");
//        user2.setUsername("janesmith");
//        user2.setEmail("janesmith@example.com");
//        user2.setAddress("456 Oak St");
//        user2.setPhone(9876543210L);
//        Date birthdate2 = dateFormat.parse("20-05-1990");
//        user2.setBirthdate(birthdate2);
//
//        List<User> userList = Arrays.asList(user1, user2);
//
//        given(userService.getAllUsers()).willReturn(userList);
//
//        mockMvc.perform(get("/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].firstName").value("John"))
//                .andExpect(jsonPath("$[0].lastName").value("Doe"))
//                .andExpect(jsonPath("$[0].username").value("johndoe"))
//                .andExpect(jsonPath("$[0].email").value("johndoe@example.com"))
//                .andExpect(jsonPath("$[0].address").value("123 Main St"))
//                .andExpect(jsonPath("$[0].phone").value(1234567890))
//                .andExpect(jsonPath("$[0].birthdate").value("15-04-2023"))
//                .andExpect(jsonPath("$[1].firstName").value("Jane"))
//                .andExpect(jsonPath("$[1].lastName").value("Smith"))
//                .andExpect(jsonPath("$[1].username").value("janesmith"))
//                .andExpect(jsonPath("$[1].email").value("janesmith@example.com"))
//                .andExpect(jsonPath("$[1].address").value("456 Oak St"))
//                .andExpect(jsonPath("$[1].phone").value(9876543210L))
//                .andExpect(jsonPath("$[1].birthdate").value("20-05-1990"));
//
//        verify(userService, times(1)).getAllUsers();
//    }
//    @Test
//    void testGetAllUsersWithEmptyList() throws Exception {
//        given(userService.getAllUsers()).willReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        verify(userService, times(1)).getAllUsers();
//    }
//
//    @Test
//    void deleteUser() throws Exception {
//        Long userId =1L;
//        mockMvc.perform(delete("/users/{id}", userId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        verify(userService, times(1)).deleteUser(userId);
//    }
//    @Test
//    void testDeleteUserThrowsException() throws Exception {
//        Long userId = 1L;
//        doThrow(new NoSuchElementException("no user found ")).when(userService).deleteUser(userId);
//
//        mockMvc.perform(delete("/users/{id}", userId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//
//        verify(userService, times(1)).deleteUser(userId);
//    }
//
//
//    @Test
//    void getUserById()throws Exception {
//        Long userId = 1L;
//
//        User user = new User();
//        user.setId(userId);
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setUsername("johndoe");
//        user.setEmail("johndoe@example.com");
//        user.setAddress("123 Main St");
//        user.setPhone(1234567890L);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        Date birthdate = dateFormat.parse("15-04-2023");
//        user.setBirthdate(birthdate);
//
//        given(userService.getUserById(userId)).willReturn(Optional.of(user));
//
//        mockMvc.perform(get("/users/{id}", userId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(userId))
//                .andExpect(jsonPath("$.firstName").value("John"))
//                .andExpect(jsonPath("$.lastName").value("Doe"))
//                .andExpect(jsonPath("$.username").value("johndoe"))
//                .andExpect(jsonPath("$.email").value("johndoe@example.com"))
//                .andExpect(jsonPath("$.address").value("123 Main St"))
//                .andExpect(jsonPath("$.phone").value(1234567890))
//                .andExpect(jsonPath("$.birthdate").value("15-04-2023"));
//
//        verify(userService, times(1)).getUserById(userId);
//    }
//    @Test
//    void testGetUserByIdWithNoSuchElementException() throws Exception {
//
//        User user = new User();
//        user.setId(1L);
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setUsername("johndoe");
//        user.setEmail("johndoe@example.com");
//        user.setAddress("123 Main St");
//        user.setPassword("password");
//
//        given(userService.getUserById(1L)).willThrow(new NoSuchElementException());
//
//        mockMvc.perform(get("/users/{id}", 1L))
//                .andExpect(status().isNotFound());
//    }
//
//
//    @Test
//    void testUpdateUserById()throws Exception {
//
//
//        User user = new User();
//        user.setId(1L);
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setUsername("johndoe");
//        user.setEmail("johndoe@example.com");
//        user.setAddress("123 Main St");
//        user.setPhone(1234567890L);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        Date birthdate = dateFormat.parse("15-04-2023");
//        user.setBirthdate(birthdate);
//        user.setPassword("fhjdfh746575");
//
//        UserEntity updatedUserEntity = new UserEntity();
//        BeanUtils.copyProperties(user, updatedUserEntity);
//        updatedUserEntity.setId(1L);
//
//        given(userService.updateUser(1L, user)).willReturn(user);
//
//        mockMvc.perform(put("/users/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(user)))
//                .andExpect(status().isOk());
//        verify(userService, times(1)).updateUser(1L, user);
//    }
//    @Test
//    void testUpdateUserByIdWithNoSuchElementException() throws Exception {
//
//        User user = new User();
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setUsername("johndoe");
//        user.setEmail("johndoe@example.com");
//        user.setAddress("123 Main St");
//        user.setPassword("password");
//
//        given(userService.updateUser(anyLong(), any(User.class))).willThrow(new NoSuchElementException());
//
//        mockMvc.perform(put("/users/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(user)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testLoginUser() throws Exception {
//
//        User user = new User();
//        user.setUsername("johndoe");
//        user.setPassword("password");
//
//
//        given(userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword()))
//                .willReturn(user);
//
//        mockMvc.perform(post("/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(user)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.username").value(user.getUsername()))
//                .andExpect(jsonPath("$.password").value(user.getPassword()));
//    }
//
//
//}