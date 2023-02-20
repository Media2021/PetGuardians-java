package pets.example.guardians.Services;

import pets.example.guardians.Model.Pet;

import java.util.List;

public interface PetService {


    Pet createPet(Pet pet);

    List<Pet> getAllPets();

    void deletePet(Long id);

    Pet getPetById(Long id);

    Pet updatePetById(Long id, Pet pet);
}
