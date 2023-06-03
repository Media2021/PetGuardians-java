package pets.example.guardians.services;

import pets.example.guardians.model.Pet;
import pets.example.guardians.model.PetType;

import java.util.List;
import java.util.Optional;

public interface PetService {
    Pet createPet(Pet pet);

    long countPets();

    List<Pet> getAllPets();

    long countAdoptedPets(PetType petType);

    List<Pet> getAvailablePets();

    void deletePet(Long id);
    Optional<Pet> getPetById(Long id);
    Pet updatePetById(Long id, Pet pet);

    long countAvailableCats();

    long countAdoptedCats();

    long countAdoptedDogs();

    long countAvailableDogs();
}
