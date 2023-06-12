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
import pets.example.guardians.repository.PetTypeCount;
import pets.example.guardians.repository.AvailableListPetType;
@SpringBootTest
class PetServiceImplTest {
    @Mock
    private PetRepo petRepo;
    @InjectMocks
    private PetServiceImpl petServiceImpl;

    @Test
     void testGetListAvailablePets_WithAvailablePets_ReturnsList() {

        List<PetEntity> availablePets = new ArrayList<>();
        availablePets.add(new PetEntity());
        Mockito.when(petRepo.findAvailablePets()).thenReturn(availablePets);


        List<PetEntity> result = petServiceImpl.getListAvailablePets();


        Assertions.assertEquals(availablePets, result);
    }

    @Test
     void testGetListAvailablePets_WithNoAvailablePets_ThrowsRuntimeException() {

        Mockito.when(petRepo.findAvailablePets()).thenReturn(new ArrayList<>());


        Assertions.assertThrows(RuntimeException.class, () -> petServiceImpl.getListAvailablePets());
    }

    @Test
     void testCountByStatusAdoptedAndType_ReturnsPetTypeCountList() {

        List<PetTypeCount> petTypeCounts = new ArrayList<>();
        // Create mock instances of PetTypeCount
        PetTypeCount petTypeCount1 = Mockito.mock(PetTypeCount.class);
        PetTypeCount petTypeCount2 = Mockito.mock(PetTypeCount.class);
        petTypeCounts.add(petTypeCount1);
        petTypeCounts.add(petTypeCount2);
        Mockito.when(petRepo.countByStatusAdoptedAndType()).thenReturn(petTypeCounts);


        List<PetTypeCount> result = petServiceImpl.countByStatusAdoptedAndType();


        Assertions.assertEquals(petTypeCounts, result);
    }


    @Test
     void testCountByStatusAvailableAndType_ReturnsEmptyList() {

        List<AvailableListPetType> petTypes = new ArrayList<>();
        Mockito.when(petRepo.countByStatusAvailableAndType()).thenReturn(petTypes);


        List<AvailableListPetType> result = petServiceImpl.countByStatusAvailableAndType();


        Assertions.assertEquals(petTypes, result);
    }

    @Test
     void testCountAllPets_ReturnsCount() {

        int count = 5;
        Mockito.when(petRepo.countAllPets()).thenReturn(count);


        long result = petServiceImpl.countAllPets();


        Assertions.assertEquals(count, result);
    }

    @Test
     void testCountAllPets_ThrowsNoSuchElementException() {

        Mockito.when(petRepo.countAllPets()).thenThrow(NoSuchElementException.class);


        Assertions.assertThrows(NoSuchElementException.class, () -> petServiceImpl.countAllPets());
    }

    @Test
    void testCountAvailablePetsByStatus_ReturnsCount() {

        int count = 3;
        Mockito.when(petRepo.countByStatusAvailable()).thenReturn(count);


        long result = petServiceImpl.countAvailablePetsByStatus();


        Assertions.assertEquals(count, result);
    }

    @Test
    void testCountAvailablePetsByStatus_ThrowsNoSuchElementException() {

        Mockito.when(petRepo.countByStatusAvailable()).thenThrow(NoSuchElementException.class);


        Assertions.assertThrows(NoSuchElementException.class, () -> petServiceImpl.countAvailablePetsByStatus());
    }








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
    @Test
    void testCreatePetWithEmptyName() {
        Pet pet = new Pet();
        pet.setName("");

        assertThrows(DataIntegrityViolationException.class, () -> petServiceImpl.createPet(pet));
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
    void testGetPetById_PetFound() {
        Long id = 1L;


        PetEntity petEntity = new PetEntity();
        petEntity.setId(id);
        petEntity.setName("Buddy");


        Mockito.when(petRepo.findById(id)).thenReturn(Optional.of(petEntity));


        PetServiceImpl petService = new PetServiceImpl(petRepo);
        Optional<Pet> result = petService.getPetById(id);


        Assertions.assertTrue(result.isPresent());
        Pet pet = result.get();
        Assertions.assertEquals(id, pet.getId());
        Assertions.assertEquals("Buddy", pet.getName());


        Mockito.verify(petRepo).findById(id);
    }


    @Test
    void testGetPetById_PetNotFound() {
        Long id = 1L;


        Mockito.when(petRepo.findById(id)).thenReturn(Optional.empty());


        PetServiceImpl petService = new PetServiceImpl(petRepo);


        Assertions.assertThrows(NoSuchElementException.class, () -> {
            petService.getPetById(id);
        });


        Mockito.verify(petRepo).findById(id);
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