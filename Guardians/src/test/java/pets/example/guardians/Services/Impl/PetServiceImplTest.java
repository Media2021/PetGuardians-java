package pets.example.guardians.Services.Impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pets.example.guardians.Model.Pet;
import pets.example.guardians.Model.PetType;
import pets.example.guardians.Repository.Entity.PetEntity;

import pets.example.guardians.Repository.PetRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
class PetServiceImplTest {
    @Mock
    private PetRepo petRepo;
    @InjectMocks
    private PetServiceImpl petServiceImpl;

    @Test
    public void testCreatePet() {
        // Create a new pet
        Pet pet = new Pet();
        pet.setName("Fluffy");
        pet.setAge(2);
        pet.setDescription("A cute and cuddly pet");
        pet.setType(PetType.Dog);
        pet.setStatus("Available");
        pet.setGender("Female");

        // Call the createPet method in the service

        Pet result = petServiceImpl.createPet(pet);


        verify(petRepo).save(any(PetEntity.class));

        // Verify that the created pet matches the original pet
        Assertions.assertNotNull(result);
        Assertions.assertEquals(pet.getName(), result.getName());
        Assertions.assertEquals(pet.getAge(),result.getAge());
        Assertions.assertEquals(pet.getDescription(),result.getDescription());
        Assertions.assertEquals(pet.getType(),result.getType());
        Assertions.assertEquals(pet.getStatus(),result.getStatus());
        Assertions.assertEquals(pet.getGender(),result.getGender());

    }
    @Test
    public void testCreatePet_PetNameExists() {
        // Create a new pet
        Pet pet = new Pet();
        pet.setName("Fluffy");
        pet.setAge(2);
        pet.setDescription("A cute and cuddly pet");
        pet.setType(PetType.Dog);
        pet.setStatus("Available");
        pet.setGender("Female");

        // Set up mock repository to return a PetEntity with the same name as the pet being created
        doThrow(new DataIntegrityViolationException("pet name is already exist")).when(petRepo).save(any(PetEntity.class));

        assertThrows(DataIntegrityViolationException.class, () -> petServiceImpl.createPet(pet));
        verify(petRepo).save(any(PetEntity.class));
    }
    private PetEntity toEntity(Pet pet) {
        PetEntity entity = new PetEntity();
        entity.setId(pet.getId());
        entity.setName(pet.getName());
        entity.setAge(pet.getAge());
        entity.setDescription(pet.getDescription());
        entity.setType(pet.getType());
        entity.setStatus(pet.getStatus());
        entity.setGender(pet.getGender());
        return entity;
    }

    @Test
    public void testGetAllPets() {
        // Create some sample pets
        Pet pet1 = new Pet();
        pet1.setName("Fluffy");
        pet1.setAge(2);
        pet1.setDescription("A cute and cuddly pet");
        pet1.setType(PetType.Dog);
        pet1.setStatus("Available");
        pet1.setGender("Female");

        Pet pet2 = new Pet();
        pet2.setName("Spike");
        pet2.setAge(4);
        pet2.setDescription("A friendly and loyal dog");
        pet2.setType(PetType.Dog);
        pet2.setStatus("Available");
        pet2.setGender("Male");


        // Create a list of pet entities and add the sample pets to it
        List<PetEntity> petEntities = new ArrayList<>();
        petEntities.add(toEntity(pet1));
        petEntities.add(toEntity(pet2));

        // Mock the pet repository and define its behavior when the findAll method is called
        when(petRepo.findAll()).thenReturn(petEntities);

        // Mock the pet repository and define its behavior when the findAll method is called
        when(petRepo.findAll()).thenReturn(petEntities);

        // Call the getAllPets method in the service
        List<Pet> result = petServiceImpl.getAllPets();

        // Verify that the repository's findAll method was called once
        verify(petRepo).findAll();

        // Verify that the returned list of pets matches the original list of pets
        Assertions.assertEquals(petEntities.size(), result.size());
        for (int i = 0; i < petEntities.size(); i++) {
            Assertions.assertEquals(petEntities.get(i).getName(), result.get(i).getName());
            Assertions.assertEquals(petEntities.get(i).getAge(), result.get(i).getAge());
            Assertions.assertEquals(petEntities.get(i).getDescription(), result.get(i).getDescription());
            Assertions.assertEquals(petEntities.get(i).getType(), result.get(i).getType());
            Assertions.assertEquals(petEntities.get(i).getStatus(), result.get(i).getStatus());
            Assertions.assertEquals(petEntities.get(i).getGender(), result.get(i).getGender());
        }
    }


    @Test
    void deletePet() {
    }

    @Test
    void getPetById() {
    }

    @Test
    void updatePetById() {
    }
}