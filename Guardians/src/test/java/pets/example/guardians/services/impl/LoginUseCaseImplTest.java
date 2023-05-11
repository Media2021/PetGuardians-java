package pets.example.guardians.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import pets.example.guardians.model.User;
import pets.example.guardians.model.UserRole;
import pets.example.guardians.repository.UserRepo;
import pets.example.guardians.repository.entity.UserEntity;


import pets.example.guardians.services.exception.InvalidCredentialsException;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;



@SpringBootTest
class LoginUseCaseImplTest {

    @Mock
    private UserRepo userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
