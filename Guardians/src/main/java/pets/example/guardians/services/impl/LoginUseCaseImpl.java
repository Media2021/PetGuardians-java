package pets.example.guardians.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pets.example.guardians.model.AccessToken;

import pets.example.guardians.model.LoginResponse;
import pets.example.guardians.model.User;
import pets.example.guardians.repository.UserRepo;
import pets.example.guardians.repository.entity.UserEntity;
import pets.example.guardians.services.AccessTokenEncoder;
import pets.example.guardians.services.Login;
import pets.example.guardians.services.exception.InvalidCredentialsException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements Login {
    private final UserRepo userRepository;
       private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponse login(User user) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(user.getUsername());
        if (userEntity.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        if (!matchesPassword(user.getPassword(), userEntity.get().getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(userEntity);

      // get the user id from the user entity


        return LoginResponse.builder()
                .accessToken(accessToken)

                .UserId(user.getId()) // set the user id in the LoginResponse
                .build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {

        return passwordEncoder.matches(rawPassword, encodedPassword);

    }

    private String generateAccessToken(Optional<UserEntity> user) {
        Long userId = null;
        List<String> roles = Collections.emptyList();
        if (user.isPresent()) {
            userId = userRepository.findByUsername(user.get().getUsername()).isPresent() ? user.get().getId() : null;
            roles = user.map(UserEntity::getRole)
                    .map(userRole -> Collections.singletonList(userRole.toString()))
                    .orElse(Collections.emptyList());
        }
        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .subject(user.map(UserEntity::getUsername).orElse(null))
                        .userId(userId)
                        .roles(roles)
                        .build());
    }



}
