package pets.example.guardians.Services.Impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pets.example.guardians.Entity.UserEntity;
import pets.example.guardians.Model.User;
import pets.example.guardians.Repository.UserRepo;
import pets.example.guardians.Services.UserService;

import java.util.List;
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
      //  UserEntity userEntity = new UserEntity();
       // userEntity.setName(user.getName());
       // userEntity.setLastName(user.getLastName());
       // userEntity.setUserName(user.getUserName());
      //  userEntity.setEmail(user.getEmail());

      //  userRepo.save(userEntity);


        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities= userRepo.findAll();
        List<User> users = userEntities
                .stream()
                .map(user-> new User(
                user.getId(),
                user.getUser_name(),
                user.getLast_name(),
                user.getName(),
                user.getEmail().trim(),
                user.getAddress(),
                user.getPassword(),
                user.getPhone(),
                user.getBirthdate(),
                        user.getUserRole()))
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

        UserEntity userEntity = userRepo.findById(id).get();
        userEntity.setName(user.getName());
        userEntity.setLast_name(user.getLast_name());
        userEntity.setEmail(user.getEmail());
        userEntity.setAddress(user.getAddress());
        userEntity.setUser_name(user.getUser_name());
        userEntity.setBirthdate(user.getBirthdate());
        userEntity.setPassword(user.getPassword());
        userEntity.setPhone(user.getPhone());
        userEntity.setUserRole(user.getUserRole());
        userRepo.save(userEntity);
        return user;
    }
}
