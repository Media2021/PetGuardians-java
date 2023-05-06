package pets.example.guardians.services;


import pets.example.guardians.model.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
