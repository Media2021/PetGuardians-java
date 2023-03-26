package pets.example.guardians.Services;

import pets.example.guardians.Model.User;

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
