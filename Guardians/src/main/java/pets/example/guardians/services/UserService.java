package pets.example.guardians.services;

import pets.example.guardians.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService
{
    User createUser(User user);
    List<User> getAllUsers();
    void deleteUser(Long id);
    Optional<User> getUserById(Long id);
    User updateUser(Long id, User user);
    User getUserByUsernameAndPassword(String username, String password);


}
