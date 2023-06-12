package pets.example.guardians.services;

import pets.example.guardians.model.Pet;
import pets.example.guardians.model.PetType;
import pets.example.guardians.repository.AvailableListPetType;
import pets.example.guardians.repository.PetTypeCount;
import pets.example.guardians.repository.entity.PetEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface PetService {


    List<PetEntity> getListAvailablePets();

    List<PetTypeCount> countByStatusAdoptedAndType() throws Exception;


    List<AvailableListPetType> countByStatusAvailableAndType();

    long countAllPets()  throws NoSuchElementException;

    long countAvailablePetsByStatus()  throws NoSuchElementException;

    Pet createPet(Pet pet);
    List<Pet> getAllPets();


    void deletePet(Long id);
    Optional<Pet> getPetById(Long id);
    Pet updatePetById(Long id, Pet pet);


}
