package pets.example.guardians.controller;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pets.example.guardians.configuration.isauthenticated.IsAuthenticated;
import pets.example.guardians.model.LoginResponse;
import pets.example.guardians.model.User;

import pets.example.guardians.services.Login;
import pets.example.guardians.services.UserService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@AllArgsConstructor

public class UserController {

    private final UserService userService;
    private final Login login;


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
            return ResponseEntity.badRequest().body("Invalid user data: " );
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
    @IsAuthenticated
    @RolesAllowed({ "ROLE_USER"})
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
    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)
    {
        try{
            Optional<User> user = userService.getUserById(id);
            return ResponseEntity.ok(user.orElseThrow());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();

        }

    }
   @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @PutMapping("{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id , @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();

        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid User user) {
        LoginResponse loginResponse = login.login(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);


    }












}
