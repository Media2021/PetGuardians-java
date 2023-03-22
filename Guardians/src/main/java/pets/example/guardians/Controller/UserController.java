package pets.example.guardians.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pets.example.guardians.Model.User;
import pets.example.guardians.Services.UserService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;





    @PostMapping()
    public User createUser(@RequestBody User user)
    {
       return userService.createUser(user);

    }
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId)
    {

         userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)
    {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @PutMapping("{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id , @RequestBody User user)
    {
        user = userService.updateUser(id, user);
        return ResponseEntity.ok(user);
    }











}
