package pets.example.guardians.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pets.example.guardians.Model.User;
import pets.example.guardians.Services.UserService;

import java.util.List;

@RestController
@RequestMapping("/allUsers")
@AllArgsConstructor
public class UserController {

    private final UserService userService;





    @PostMapping("/createUser")
    public User createUser(@RequestBody User user)
    {
       return userService.createUser(user);

    }
    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/getUsers/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id)
    {

         userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getUsers/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)
    {
        User user = null;

        user= userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/getUsers/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id , @RequestBody User user)
    {
        user = userService.updateUser(id, user);
        return ResponseEntity.ok(user);
    }











}
