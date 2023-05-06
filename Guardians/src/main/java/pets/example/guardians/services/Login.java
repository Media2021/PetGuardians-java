package pets.example.guardians.services;


import pets.example.guardians.model.LoginResponse;
import pets.example.guardians.model.User;

public interface Login {
    LoginResponse login(User user);


}
