package pets.example.guardians.services;

import pets.example.guardians.model.User;

import java.util.List;

public interface UserService
{


    User createUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long id);

    User getUserById(Long id);

    User updateUser(Long id, User user);

    User getUserByUsernameAndPassword(String username, String password);


}
