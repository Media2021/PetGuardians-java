package pets.example.guardians.controller;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pets.example.guardians.model.User;
import pets.example.guardians.services.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getUsername().isEmpty()
                || user.getEmail().isEmpty() || user.getAddress().isEmpty() || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid user data: all user fields including password are required and cannot be empty");
        }

        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Invalid user data: " + e.getMessage());
        }

    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id)
    {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
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
        Optional<User> optionalUser = Optional.ofNullable(userService.getUserById(id));
        if (optionalUser.isPresent()) {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User loggedUser = userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(loggedUser);
    }












}
