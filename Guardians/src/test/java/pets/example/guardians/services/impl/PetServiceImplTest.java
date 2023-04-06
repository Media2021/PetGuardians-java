package pets.example.guardians.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pets.example.guardians.model.Pet;
import pets.example.guardians.model.PetType;
import pets.example.guardians.repository.PetRepo;
import pets.example.guardians.repository.entity.PetEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
class PetServiceImplTest {
    @Mock
    private PetRepo petRepo;
    @InjectMocks
    private PetServiceImpl petServiceImpl;

    @Test
     void testCreatePet() {

        Pet pet = new Pet();
        pet.setName("Fluffy");
        pet.setAge(2);
        pet.setDescription("A cute and cuddly Pet");
        pet.setType(PetType.DOG);
        pet.setStatus("Available");
        pet.setGender("Female");



        Pet result = petServiceImpl.createPet(pet);


        verify(petRepo).save(any(PetEntity.class));

        // Verify that the created Pet matches the original Pet
        Assertions.assertNotNull(result);
        Assertions.assertEquals(pet.getName(), result.getName());
        Assertions.assertEquals(pet.getAge(),result.getAge());
        Assertions.assertEquals(pet.getDescription(),result.getDescription());
        Assertions.assertEquals(pet.getType(),result.getType());
        Assertions.assertEquals(pet.getStatus(),result.getStatus());
        Assertions.assertEquals(pet.getGender(),result.getGender());

    }
    @Test
     void testCreatePet_PetNameExists() {
        // Create a new Pet
        Pet pet = new Pet();
        pet.setName("Fluffy");
        pet.setAge(2);
        pet.setDescription("A cute and cuddly Pet");
        pet.setType(PetType.DOG);
        pet.setStatus("Available");
        pet.setGender("Female");

        // Set up mock repository to return a PetEntity with the same name as the Pet being created
        doThrow(new DataIntegrityViolationException("Pet name is already exist")).when(petRepo).save(any(PetEntity.class));

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
     void testGetAllPets() {

        Pet pet1 = new Pet();
        pet1.setName("Fluffy");
        pet1.setAge(2);
        pet1.setDescription("A cute and cuddly Pet");
        pet1.setType(PetType.DOG);
        pet1.setStatus("Available");
        pet1.setGender("Female");

        Pet pet2 = new Pet();
        pet2.setName("Spike");
        pet2.setAge(4);
        pet2.setDescription("A friendly and loyal dog");
        pet2.setType(PetType.DOG);
        pet2.setStatus("Available");
        pet2.setGender("Male");



        List<PetEntity> petEntities = new ArrayList<>();
        petEntities.add(toEntity(pet1));
        petEntities.add(toEntity(pet2));

        // Mock the Pet repository and define its behavior when the findAll method is called
        when(petRepo.findAll()).thenReturn(petEntities);

        // Mock the Pet repository and define its behavior when the findAll method is called
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

        Long id = 1L;
        PetEntity petEntity = new PetEntity();
        petEntity.setId(id);
        when(petRepo.findById(id)).thenReturn(Optional.of(petEntity));
        petServiceImpl.deletePet(id);
        verify(petRepo).delete(petEntity);
    }
    @Test
    void testGetPetById() {
        // Arrange
        Long id = 1L;
        PetEntity petEntity = new PetEntity();
        petEntity.setId(id);
        petEntity.setName("Rex");
        petEntity.setAge(3);
        petEntity.setDescription("A friendly dog");
        petEntity.setType(PetType.DOG);
        petEntity.setStatus("AVAILABLE");
        petEntity.setGender("MALE");

        when(petRepo.findById(id)).thenReturn(Optional.of(petEntity));

        // Act
        Optional<Pet> result = petServiceImpl.getPetById(id);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Pet pet = result.get();
        Assertions.assertEquals((long) id, pet.getId());
        Assertions.assertEquals("Rex", pet.getName());
        Assertions.assertEquals(3, pet.getAge());
        Assertions.assertEquals("A friendly dog", pet.getDescription());
        Assertions.assertEquals(PetType.DOG, pet.getType());
        Assertions.assertEquals("AVAILABLE", pet.getStatus());
        Assertions.assertEquals("MALE", pet.getGender());
    }


    @Test
     void testUpdatePetById() {
        // Create a Pet with a specific ID
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fluffy");
        pet.setAge(2);
        pet.setDescription("A cute and cuddly Pet");
        pet.setType(PetType.DOG);
        pet.setStatus("Available");
        pet.setGender("Female");

        // Define the expected Pet entity
        PetEntity expectedPetEntity = new PetEntity();
        expectedPetEntity.setId(1L);
        expectedPetEntity.setName("Fluffy");
        expectedPetEntity.setAge(2);
        expectedPetEntity.setDescription("A cute and cuddly Pet");
        expectedPetEntity.setType(PetType.DOG);
        expectedPetEntity.setStatus("Available");
        expectedPetEntity.setGender("Female");

        // Mock the Pet repository to return the expected Pet entity when findById is called
        Mockito.when(petRepo.findById(1L)).thenReturn(Optional.of(expectedPetEntity));

        // Update the Pet's name and age
        pet.setName("Fido");
        pet.setAge(3);

        // Define the expected updated Pet entity
        PetEntity expectedUpdatedPetEntity = new PetEntity();
        expectedUpdatedPetEntity.setId(1L);
        expectedUpdatedPetEntity.setName("Fido");
        expectedUpdatedPetEntity.setAge(3);
        expectedUpdatedPetEntity.setDescription("A cute and cuddly Pet");
        expectedUpdatedPetEntity.setType(PetType.DOG);
        expectedUpdatedPetEntity.setStatus("Available");
        expectedUpdatedPetEntity.setGender("Female");

        // Mock the Pet repository to return the expected updated Pet entity when save is called
        Mockito.when(petRepo.save(any(PetEntity.class))).thenReturn(expectedUpdatedPetEntity);

        // Call the updatePetById method in the service
        Pet result = petServiceImpl.updatePetById(1L, pet);

        // Verify that the correct updated Pet was returned
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedUpdatedPetEntity.getId(), result.getId());
        Assertions.assertEquals(expectedUpdatedPetEntity.getName(), result.getName());
        Assertions.assertEquals(expectedUpdatedPetEntity.getAge(), result.getAge());
        Assertions.assertEquals(expectedUpdatedPetEntity.getDescription(), result.getDescription());
        Assertions.assertEquals(expectedUpdatedPetEntity.getType(), result.getType());
        Assertions.assertEquals(expectedUpdatedPetEntity.getStatus(), result.getStatus());
        Assertions.assertEquals(expectedUpdatedPetEntity.getGender(), result.getGender());

        // Verify that the Pet repository's findById and save methods were called with the correct ID and updated Pet entity
        verify(petRepo).findById(1L);
        verify(petRepo).save(any(PetEntity.class));
    }
    @Test
     void testUpdatePetByIdNotFound() {
        // Create a Pet with a specific ID
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fluffy");
        pet.setAge(2);
        pet.setDescription("A cute and cuddly Pet");
        pet.setType(PetType.DOG);
        pet.setStatus("Available");
        pet.setGender("Female");

        // Mock the Pet repository to return an empty Optional when findById is called
        Mockito.when(petRepo.findById(1L)).thenReturn(Optional.empty());

        // Call the updatePetById method in the service and catch the exception
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            petServiceImpl.updatePetById(1L, pet);
        });

        // Verify that the exception message contains the correct ID
        String expectedMessage = "Pet with ID 1 not found";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        // Verify that the Pet repository's findById method was called with the correct ID
        verify(petRepo).findById(1L);

        // Verify that the Pet repository's save method was not called
        verify(petRepo, never()).save(any(PetEntity.class));
    }




}