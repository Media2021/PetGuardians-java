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
        Pet pet = new Pet();
        pet.setName("Fluffy");
        pet.setAge(2);
        pet.setDescription("A cute and cuddly Pet");
        pet.setType(PetType.DOG);
        pet.setStatus("Available");
        pet.setGender("Female");

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

        when(petRepo.findAll()).thenReturn(petEntities);
        when(petRepo.findAll()).thenReturn(petEntities);
        List<Pet> result = petServiceImpl.getAllPets();

        verify(petRepo).findAll();
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
    void testGetAllPets_NoPets() {
        List<PetEntity> petEntities = new ArrayList<>();
        when(petRepo.findAll()).thenReturn(petEntities);

        List<Pet> result = petServiceImpl.getAllPets();

        verify(petRepo).findAll();
        Assertions.assertTrue(result.isEmpty());
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
    void deletePet_ThrowsExceptionWhenPetNotFound() {
        Long id = 1L;
        when(petRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> petServiceImpl.deletePet(id));

        verify(petRepo, never()).delete(any(PetEntity.class));
    }
    @Test
    void testGetPetById() {
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
        Optional<Pet> result = petServiceImpl.getPetById(id);

        Assertions.assertTrue(result.isPresent());
        Pet pet = result.get();
        Assertions.assertEquals((long) id, pet.getId());
        Assertions.assertEquals("Rex", pet.getName());
        Assertions.assertEquals(3, pet.getAge());
        Assertions.assertEquals("A friendly dog", pet.getDescription());
        Assertions.assertEquals(PetType.DOG, pet.getType());
        Assertions.assertEquals("AVAILABLE", pet.getStatus());
        Assertions.assertEquals("MALE", pet.getGender());
        verify(petRepo).findById(id);
    }
    @Test
    void testGetPetById_InvalidId() {
        Long id = 1L;
        when(petRepo.findById(id)).thenReturn(Optional.empty());

        Optional<Pet> result = petServiceImpl.getPetById(id);
        Assertions.assertTrue(result.isEmpty(), "Expected an empty Optional");

        verify(petRepo).findById(id);
    }
    @Test
    void testGetPetById_IdNotFound() {
        Long id = 1L;
        when(petRepo.findById(id)).thenReturn(Optional.empty());

        Optional<Pet> result = petServiceImpl.getPetById(id);

        Assertions.assertFalse(result.isPresent());
    }
    @Test
     void testUpdatePetById() {

        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fluffy");
        pet.setAge(2);
        pet.setDescription("A cute and cuddly Pet");
        pet.setType(PetType.DOG);
        pet.setStatus("Available");
        pet.setGender("Female");

        PetEntity expectedPetEntity = new PetEntity();
        expectedPetEntity.setId(1L);
        expectedPetEntity.setName("Fluffy");
        expectedPetEntity.setAge(2);
        expectedPetEntity.setDescription("A cute and cuddly Pet");
        expectedPetEntity.setType(PetType.DOG);
        expectedPetEntity.setStatus("Available");
        expectedPetEntity.setGender("Female");

        Mockito.when(petRepo.findById(1L)).thenReturn(Optional.of(expectedPetEntity));
        pet.setName("Fido");
        pet.setAge(3);

        PetEntity expectedUpdatedPetEntity = new PetEntity();
        expectedUpdatedPetEntity.setId(1L);
        expectedUpdatedPetEntity.setName("Fido");
        expectedUpdatedPetEntity.setAge(3);
        expectedUpdatedPetEntity.setDescription("A cute and cuddly Pet");
        expectedUpdatedPetEntity.setType(PetType.DOG);
        expectedUpdatedPetEntity.setStatus("Available");
        expectedUpdatedPetEntity.setGender("Female");

        Mockito.when(petRepo.save(any(PetEntity.class))).thenReturn(expectedUpdatedPetEntity);
        Pet result = petServiceImpl.updatePetById(1L, pet);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedUpdatedPetEntity.getId(), result.getId());
        Assertions.assertEquals(expectedUpdatedPetEntity.getName(), result.getName());
        Assertions.assertEquals(expectedUpdatedPetEntity.getAge(), result.getAge());
        Assertions.assertEquals(expectedUpdatedPetEntity.getDescription(), result.getDescription());
        Assertions.assertEquals(expectedUpdatedPetEntity.getType(), result.getType());
        Assertions.assertEquals(expectedUpdatedPetEntity.getStatus(), result.getStatus());
        Assertions.assertEquals(expectedUpdatedPetEntity.getGender(), result.getGender());

        verify(petRepo).findById(1L);
        verify(petRepo).save(any(PetEntity.class));
    }
    @Test
     void testUpdatePetByIdNotFound() {

        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fluffy");
        pet.setAge(2);
        pet.setDescription("A cute and cuddly Pet");
        pet.setType(PetType.DOG);
        pet.setStatus("Available");
        pet.setGender("Female");

        Mockito.when(petRepo.findById(1L)).thenReturn(Optional.empty());
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> petServiceImpl.updatePetById(1L, pet));

        String expectedMessage = "Pet with ID 1 not found";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        verify(petRepo).findById(1L);
        verify(petRepo, never()).save(any(PetEntity.class));
    }
}