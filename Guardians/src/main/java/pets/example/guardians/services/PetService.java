package pets.example.guardians.services;

import pets.example.guardians.model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {
    Pet createPet(Pet pet);

    List<Pet> getAllPets();


    void deletePet(Long id);
    Optional<Pet> getPetById(Long id);
    Pet updatePetById(Long id, Pet pet);
}
