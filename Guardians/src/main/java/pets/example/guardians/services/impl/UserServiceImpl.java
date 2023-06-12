package pets.example.guardians.services.impl;

import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import pets.example.guardians.model.User;
import pets.example.guardians.repository.UserRepo;
import pets.example.guardians.repository.entity.UserEntity;
import pets.example.guardians.services.Mapper.UserMapper;
import pets.example.guardians.services.UserService;
import pets.example.guardians.services.exception.UsernameAlreadyExistsException;

import java.util.*;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    @Override
    public List<UserEntity> findAllUsersWithAdoptedPets() {
        try {
            return userRepo.findAllWithAdoptedPets();
        } catch (Exception ex) {
            throw new ServiceException("Error occurred while fetching users with adopted pets", ex);
        }
    }

    @Transactional
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

    protected void saveNewUser(UserEntity user, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }


    @Transactional
    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepo.findAll();
        List<User> users = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            User user = UserMapper.toModel(userEntity);
            users.add(user);
        }
        return users;
    }

    private static final String USER_NOT_FOUND_MESSAGE = "User with id %s not found";
    @Transactional
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
    @Transactional
    public Optional<User> getUserById(Long id) {
        Optional<UserEntity> userEntityOpt = userRepo.findById(id);
        if (userEntityOpt.isPresent()) {
            UserEntity userEntity = userEntityOpt.get();
            return Optional.of(UserMapper.toModel(userEntity));
        } else {
            throw new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, id));
        }
    }


    @Transactional
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

    }



