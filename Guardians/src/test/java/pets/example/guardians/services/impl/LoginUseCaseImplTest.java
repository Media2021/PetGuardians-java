package pets.example.guardians.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pets.example.guardians.model.AccessToken;
import pets.example.guardians.model.LoginResponse;
import pets.example.guardians.model.User;
import pets.example.guardians.model.UserRole;
import pets.example.guardians.repository.UserRepo;
import pets.example.guardians.repository.entity.UserEntity;
import pets.example.guardians.services.AccessTokenEncoder;
import pets.example.guardians.services.exception.InvalidCredentialsException;


import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class LoginUseCaseImplTest {

    @Mock
    private UserRepo userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @BeforeEach
    void setUp() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

    }
    @Test
    void testGenerateAccessToken() {
        Long userId = 1L;
        String username = "testuser";
        String encodedAccessToken = "encoded_access_token";
        UserRole role = (UserRole.ADMIN);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUsername(username);
        userEntity.setRole(role);

        AccessToken expectedAccessToken = AccessToken.builder()
                .subject(username)
                .userId(userId)
                .roles(Collections.singletonList(String.valueOf(role)))
                .build();

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        Mockito.when(accessTokenEncoder.encode(expectedAccessToken)).thenReturn(encodedAccessToken);

        String accessToken = loginUseCase.generateAccessToken(Optional.of(userEntity));

        Assertions.assertEquals(encodedAccessToken, accessToken);
    }

    @Test
  void testLogin_Success() {
        UserRepo userRepository = mock(UserRepo.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        AccessTokenEncoder accessTokenEncoder = mock(AccessTokenEncoder.class);

        LoginUseCaseImpl loginUseCase = new LoginUseCaseImpl(userRepository, passwordEncoder, accessTokenEncoder);

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setPassword("encodedpassword");

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class))).thenReturn(true);
        when(accessTokenEncoder.encode(any(AccessToken.class))).thenReturn("encodedaccesstoken");

        LoginResponse response = loginUseCase.login(user);

        assertEquals("encodedaccesstoken", response.getAccessToken());
        assertEquals(user.getId(), response.getUserId());
    }


    @Test
    void testPasswordEncoder_Encode() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "testpassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
    @Test
     void testLogin_UserNotFound() {
        UserRepo userRepository = mock(UserRepo.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        AccessTokenEncoder accessTokenEncoder = mock(AccessTokenEncoder.class);

        LoginUseCaseImpl loginUseCase = new LoginUseCaseImpl(userRepository, passwordEncoder, accessTokenEncoder);

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(user));
    }

    @Test
     void testPasswordEncoder_Matches() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "testpassword";
        String encodedPassword = "$2a$10$AtAToNKE1JlqSFA/j4EcnuBL3Mg3bCDa.hDdsXuWbEhliC2/YHcvW";

        Assertions.assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
    @Test
     void testLogin_PasswordMismatch() {
        UserRepo userRepository = mock(UserRepo.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        AccessTokenEncoder accessTokenEncoder = mock(AccessTokenEncoder.class);

        LoginUseCaseImpl loginUseCase = new LoginUseCaseImpl(userRepository, passwordEncoder, accessTokenEncoder);

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setPassword("encodedpassword");

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class))).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(user));
    }
    @Test
    void matchesPassword_InvalidPassword_ReturnsFalse() {
        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode("wrongPassword");

        boolean result = loginUseCase.matchesPassword(rawPassword, encodedPassword);

        Assertions.assertFalse(result);
    }

    @Test
    void testLoginInvalidUsername() {

        User user = User.builder()
                .username("nonExistingUser")
                .password("testPassword")
                .build();


        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());


        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(user));
    }

    @Test
    void testLoginInvalidPassword() {

        User user = User.builder()
                .username("testUser")
                .password("testPassword")
                .build();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");
        userEntity.setPassword("encodedPassword");
        userEntity.setRole(UserRole.USER);


        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(userEntity));


        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);


        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(user));
    }





}
