package pets.example.guardians.services;


import pets.example.guardians.model.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
