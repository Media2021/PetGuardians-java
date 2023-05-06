package pets.example.guardians.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import pets.example.guardians.model.User;
import pets.example.guardians.repository.UserRepo;
import pets.example.guardians.repository.entity.UserEntity;
import pets.example.guardians.services.UserService;

import java.util.*;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User createUser(User user) {
        Optional<UserEntity> existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new UsernameAlreadyExistsException("Username already taken");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        saveNewUser(userEntity, user.getPassword());
        return user;
    }

    public void saveNewUser(UserEntity user, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }

    public static class UsernameAlreadyExistsException extends RuntimeException {
        public UsernameAlreadyExistsException(String message) {
            super(message);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepo.findAll();
        List<User> users = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            users.add(user);
        }

        return users;
    }

    private static final String USER_NOT_FOUND_MESSAGE = "User with id %s not found";

    @Override
    public void deleteUser(Long id) {
        Optional<UserEntity> userEntityOpt = userRepo.findById(id);
        if (userEntityOpt.isPresent()) {
            UserEntity userEntity = userEntityOpt.get();
            userRepo.delete(userEntity);
        } else {
            throw new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, id));
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        Optional<UserEntity> userEntityOpt = userRepo.findById(id);
        if (userEntityOpt.isPresent()) {
            UserEntity userEntity = userEntityOpt.get();
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            return Optional.of(user);
        } else {
            throw new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, id));
        }
    }

    @Override
    public User updateUser(Long id, User user) {
//        if (!Objects.equals(requestAccessToken.getUserId(), id)) {
//            throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
//        }
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        if (optionalUserEntity.isEmpty()) {
            throw new NoSuchElementException(String.format("User with ID %d not found", id));
        }
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setAddress(user.getAddress());
        userEntity.setUsername(user.getUsername());


        userEntity.setPhone(user.getPhone());


        String newUsername = user.getUsername();
        if (!newUsername.equals(userEntity.getUsername())) {
            Optional<UserEntity> existingUser = userRepo.findByUsername(newUsername);
            if (existingUser.isPresent() && existingUser.get().getId() != id) {
                throw new UsernameAlreadyExistsException("Username already taken");
            }
            userEntity.setUsername(newUsername);
        }

        userRepo.save(userEntity);
        return user;
    }
    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        Optional<UserEntity> optionalUserEntity = userRepo.findByUsernameAndPassword(username, password);
        if (optionalUserEntity.isEmpty()) {
            throw new NoSuchElementException("Username " + username + " not found");
        }
        UserEntity userEntity = optionalUserEntity.get();
        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setAddress(userEntity.getAddress());
        user.setUsername(userEntity.getUsername());
        user.setBirthdate(userEntity.getBirthdate());
        user.setPassword(userEntity.getPassword());
        user.setPhone(userEntity.getPhone());
        user.setRole(userEntity.getRole());
        return user;
    }
    @Override
    public Optional<User> login(String username, String password) {
        return getAllUsers().stream()
                .filter(user -> user.getEmail().equals(username) && user.getPassword().equals(password))
                .findFirst();

    }
    }



