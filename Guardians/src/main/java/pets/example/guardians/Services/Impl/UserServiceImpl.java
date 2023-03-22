package pets.example.guardians.Services.Impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import pets.example.guardians.Model.User;
import pets.example.guardians.Repository.UserRepo;
import pets.example.guardians.Repository.Entity.UserEntity;
import pets.example.guardians.Services.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;




    @Override
    public User createUser(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user , userEntity);
        userRepo.save(userEntity);
      //  UserEntity UserEntity = new UserEntity();
       // UserEntity.setName(User.getName());
       // UserEntity.setLastName(User.getLastName());
       // UserEntity.setUserName(User.getUserName());
      //  UserEntity.setEmail(User.getEmail());

      //  UserRepo.save(UserEntity);


        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities= userRepo.findAll();
        List<User> users = userEntities
                .stream()
                .map(user-> new User(
              user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getEmail().trim(),
                user.getAddress(),
                user.getPassword(),
                user.getPhone(),
                user.getBirthdate(),
                        user.getRole()))
                .collect(Collectors.toList());



        return users;
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepo.findById(id).get();
        userRepo.delete(userEntity);
    }

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity =
                userRepo.findById(id).get();
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    @Override
    public User updateUser(Long id, User user) {

        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        if (!optionalUserEntity.isPresent()) {
            throw new NoSuchElementException("User with ID " + id + " not found");
        }
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setAddress(user.getAddress());
        userEntity.setUserName(user.getUserName());
        userEntity.setBirthdate(user.getBirthdate());
        userEntity.setPassword(user.getPassword());
        userEntity.setPhone(user.getPhone());
       userEntity.setRole(user.getRole());
        userRepo.save(userEntity);
        return user;
    }
}
