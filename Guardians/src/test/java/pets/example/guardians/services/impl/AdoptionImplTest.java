package pets.example.guardians.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pets.example.guardians.model.AdoptionRequest;
import pets.example.guardians.model.Pet;
import pets.example.guardians.model.User;
import pets.example.guardians.repository.AdoptionRepo;
import pets.example.guardians.repository.PetRepo;
import pets.example.guardians.repository.UserRepo;
import pets.example.guardians.repository.entity.AdoptionRequestEntity;
import pets.example.guardians.repository.entity.PetEntity;
import pets.example.guardians.repository.entity.UserEntity;


import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class AdoptionImplTest {
    @Mock
    private AdoptionRepo adoptionRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private PetRepo petRepo;

    @InjectMocks
    private AdoptionImpl adoptionService;
    @Test
    void testGetAdoptionRequestsByUserId() {

        Long userId = 1L;
        List<AdoptionRequestEntity> adoptionRequestEntities = new ArrayList<>();
        adoptionRequestEntities.add(createAdoptionRequestEntity(userId, 1L, 1L));
        adoptionRequestEntities.add(createAdoptionRequestEntity(userId, 2L, 2L));


        when(adoptionRepo.findByUserId(userId)).thenReturn(adoptionRequestEntities);
        when(userRepo.findById(1L)).thenReturn(Optional.of(createUserEntity(1L)));
        when(userRepo.findById(2L)).thenReturn(Optional.of(createUserEntity(2L)));
        when(petRepo.findById(1L)).thenReturn(Optional.of(createPetEntity(1L)));
        when(petRepo.findById(2L)).thenReturn(Optional.of(createPetEntity(2L)));
        List<AdoptionRequest> adoptionRequests = adoptionService.getAdoptionRequestsByUserId(userId);

        Assertions.assertEquals(2, adoptionRequests.size());

    }
    @Test
    void testGetAdoptionRequestsByUserIdMissingUser() {

        Long userId = 1L;
        List<AdoptionRequestEntity> adoptionRequestEntities = new ArrayList<>();
        adoptionRequestEntities.add(createAdoptionRequestEntity(userId, 1L, 1L));


        when(adoptionRepo.findByUserId(userId)).thenReturn(adoptionRequestEntities);
        adoptionRequestEntities.get(0).setUser(null);

        assertThrows(IllegalArgumentException.class, () -> adoptionService.getAdoptionRequestsByUserId(userId));
    }

    @Test
    void testGetAdoptionRequestsByUserIdMissingPet() {

        Long userId = 1L;
        List<AdoptionRequestEntity> adoptionRequestEntities = new ArrayList<>();
        adoptionRequestEntities.add(createAdoptionRequestEntity(userId, 1L, 1L));

        when(adoptionRepo.findByUserId(userId)).thenReturn(adoptionRequestEntities);
        adoptionRequestEntities.get(0).setPet(null);
        assertThrows(IllegalArgumentException.class, () -> adoptionService.getAdoptionRequestsByUserId(userId));
    }


    private AdoptionRequestEntity createAdoptionRequestEntity(Long userId, Long requestId, Long petId) {
        AdoptionRequestEntity adoptionRequestEntity = new AdoptionRequestEntity();
        adoptionRequestEntity.setId(requestId);
        adoptionRequestEntity.setUser(createUserEntity(userId));
        adoptionRequestEntity.setPet(createPetEntity(petId));
        return adoptionRequestEntity;
    }

    private UserEntity createUserEntity(Long userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return userEntity;
    }

    private PetEntity createPetEntity(Long petId) {
        PetEntity petEntity = new PetEntity();
        petEntity.setId(petId);
        return petEntity;
    }
    @Test
    void createAdoptionRequest() {

        AdoptionRequestEntity adoptionRequestEntity = new AdoptionRequestEntity();
        UserEntity existingUser = new UserEntity();
        existingUser.setId(1L);
        adoptionRequestEntity.setUser(existingUser);
        PetEntity existingPet = new PetEntity();
        existingPet.setId(1L);
        adoptionRequestEntity.setPet(existingPet);
        adoptionRequestEntity.setNotes("Test notes");
        adoptionRequestEntity.setRequestDate(new Date());
        adoptionRequestEntity.setStatus("PENDING");

        when(userRepo.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        when(petRepo.findById(existingPet.getId())).thenReturn(Optional.of(existingPet));
        when(adoptionRepo.save(Mockito.any(AdoptionRequestEntity.class))).thenReturn(adoptionRequestEntity);


        AdoptionRequest result = adoptionService.createAdoptionRequest(adoptionRequestEntity);


        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingUser.getId(), result.getUser().getId());
        Assertions.assertEquals(existingPet.getId(), result.getPet().getId());
        Assertions.assertEquals("Test notes", result.getNotes());
        Assertions.assertEquals("PENDING", result.getStatus());
        verify(userRepo, times(1)).findById(existingUser.getId());
        verify(petRepo, times(1)).findById(existingPet.getId());
        verify(adoptionRepo, times(1)).save(Mockito.any(AdoptionRequestEntity.class));
    }


    @Test
    void createAdoptionRequest_NonExistingUser_ThrowsNoSuchElementException() {

        AdoptionRequestEntity adoptionRequestEntity = new AdoptionRequestEntity();
        UserEntity nonExistingUser = new UserEntity();
        nonExistingUser.setId(1L);
        adoptionRequestEntity.setUser(nonExistingUser);

        when(userRepo.findById(nonExistingUser.getId())).thenReturn(Optional.empty());


        assertThrows(NoSuchElementException.class, () -> adoptionService.createAdoptionRequest(adoptionRequestEntity));
        verify(userRepo, times(1)).findById(nonExistingUser.getId());
        verify(petRepo, never()).findById(anyLong());
        verify(adoptionRepo, never()).save(any(AdoptionRequestEntity.class));
    }


    @Test
    void getAllAdoptionRequests() {

        AdoptionRequestEntity adoptionRequestEntity1 = new AdoptionRequestEntity();
        adoptionRequestEntity1.setId(1L);
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(1L);
        adoptionRequestEntity1.setUser(userEntity1);


        AdoptionRequestEntity adoptionRequestEntity2 = new AdoptionRequestEntity();
        adoptionRequestEntity2.setId(2L);
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2L);
        adoptionRequestEntity2.setUser(userEntity2);


        List<AdoptionRequestEntity> adoptionRequestEntities = Arrays.asList(adoptionRequestEntity1, adoptionRequestEntity2);
        when(adoptionRepo.findAll()).thenReturn(adoptionRequestEntities);


        List<AdoptionRequest> result = adoptionService.getAllAdoptionRequests();


        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        verify(adoptionRepo, times(1)).findAll();
    }
    @Test
    void getAllAdoptionRequests_Exception() {

        when(adoptionRepo.findAll()).thenThrow(new RuntimeException("Failed to retrieve adoption requests"));


        assertThrows(RuntimeException.class, () -> adoptionService.getAllAdoptionRequests());

        verify(adoptionRepo, times(1)).findAll();
    }

    @Test
    void getAdoptionRequestById_ExistingId_ReturnsAdoptionRequest() {

        Long requestId = 1L;
        AdoptionRequestEntity adoptionRequestEntity = new AdoptionRequestEntity();
        adoptionRequestEntity.setId(requestId);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        adoptionRequestEntity.setUser(userEntity);

        PetEntity petEntity = new PetEntity();
        petEntity.setId(1L);
        adoptionRequestEntity.setPet(petEntity);

        Optional<AdoptionRequestEntity> adoptionRequestEntityOptional = Optional.of(adoptionRequestEntity);
        when(adoptionRepo.findById(requestId)).thenReturn(adoptionRequestEntityOptional);


        Optional<AdoptionRequest> result = adoptionService.getAdoptionRequestById(requestId);


        Assertions.assertTrue(result.isPresent());
        AdoptionRequest adoptionRequest = result.get();
        Assertions.assertEquals(Optional.of(requestId), Optional.of(adoptionRequest.getId()));

        verify(adoptionRepo, times(1)).findById(requestId);
    }



    @Test
    void getAdoptionRequestById_NonExistingId_ReturnsEmptyOptional() {

        Long requestId = 1L;
        when(adoptionRepo.findById(requestId)).thenReturn(Optional.empty());


        Optional<AdoptionRequest> result = adoptionService.getAdoptionRequestById(requestId);


        Assertions.assertFalse(result.isPresent());
        verify(adoptionRepo, times(1)).findById(requestId);
    }


    @Test
    void updateAdoptionRequestById() {
        Long requestId = 1L;
        AdoptionRequest updatedRequest = new AdoptionRequest();
        updatedRequest.setStatus("UPDATED");
        updatedRequest.setNotes("Updated notes");
        updatedRequest.setRequestDate(new Date());
        User user = new User();
        user.setId(1L);
        updatedRequest.setUser(user);
        Pet pet = new Pet();
        pet.setId(1L);
        updatedRequest.setPet(pet);

        AdoptionRequestEntity adoptionRequestEntity = new AdoptionRequestEntity();
        adoptionRequestEntity.setId(requestId);

        when(adoptionRepo.findById(requestId)).thenReturn(Optional.of(adoptionRequestEntity));
        when(adoptionRepo.save(adoptionRequestEntity)).thenReturn(adoptionRequestEntity);


        Optional<AdoptionRequest> result = adoptionService.updateAdoptionRequestById(requestId, updatedRequest);


        Assertions.assertTrue(result.isPresent());
        AdoptionRequest adoptionRequest = result.get();
        Assertions.assertEquals(updatedRequest.getStatus(), adoptionRequest.getStatus());
        Assertions.assertEquals(updatedRequest.getNotes(), adoptionRequest.getNotes());

        verify(adoptionRepo, times(1)).findById(requestId);
        verify(adoptionRepo, times(1)).save(adoptionRequestEntity);
    }

    @Test
    void updateAdoptionRequestById_NonExistingId_ReturnsEmptyOptional() {

        Long requestId = 1L;
        AdoptionRequest updatedRequest = new AdoptionRequest();


        when(adoptionRepo.findById(requestId)).thenReturn(Optional.empty());


        Optional<AdoptionRequest> result = adoptionService.updateAdoptionRequestById(requestId, updatedRequest);


        Assertions.assertFalse(result.isPresent());
        verify(adoptionRepo, times(1)).findById(requestId);
        verify(adoptionRepo, never()).save(any());
    }


    @Test
    void testAcceptAdoptionRequest() {
        Long requestId = 1L;


        AdoptionRequestEntity adoptionRequestEntity = new AdoptionRequestEntity();
        adoptionRequestEntity.setId(requestId);
        adoptionRequestEntity.setStatus("PENDING");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("john_doe");

        PetEntity petEntity = new PetEntity();
        petEntity.setId(1L);
        petEntity.setName("Rex");
        petEntity.setStatus("AVAILABLE");

        adoptionRequestEntity.setUser(userEntity);
        adoptionRequestEntity.setPet(petEntity);


        Mockito.when(adoptionRepo.findById(requestId)).thenReturn(Optional.of(adoptionRequestEntity));

        Mockito.when(adoptionRepo.save(Mockito.any(AdoptionRequestEntity.class))).thenReturn(null);


        AdoptionImpl adoptionService = new AdoptionImpl(adoptionRepo,  userRepo,petRepo);


        Assertions.assertThrows(NullPointerException.class, () -> {
            adoptionService.acceptAdoptionRequest(requestId);
        });


        Mockito.verify(adoptionRepo).findById(requestId);


        Mockito.verify(adoptionRepo).save(adoptionRequestEntity);
    }

    @Test
    void acceptAdoptionRequest_InvalidId_ThrowsNoSuchElementException() {
        Long requestId = 1L;

        when(adoptionRepo.findById(requestId)).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> adoptionService.acceptAdoptionRequest(requestId)
        );

        verify(adoptionRepo, times(1)).findById(requestId);
        verify(adoptionRepo, never()).save(any());
        verify(userRepo, never()).save(any());
        verify(petRepo, never()).save(any());
    }

    @Test
    void declineAdoptionRequest_ValidId_ReturnsAdoptionRequest() {
        Long requestId = 1L;
        AdoptionRequestEntity adoptionRequestEntity = new AdoptionRequestEntity();
        adoptionRequestEntity.setId(requestId);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        adoptionRequestEntity.setUser(userEntity);

        PetEntity petEntity = new PetEntity();
        petEntity.setId(1L);
        adoptionRequestEntity.setPet(petEntity);

        when(adoptionRepo.findById(requestId)).thenReturn(Optional.of(adoptionRequestEntity));
        when(adoptionRepo.save(adoptionRequestEntity)).thenReturn(adoptionRequestEntity);

        AdoptionRequest result = adoptionService.declineAdoptionRequest(requestId);

        Assertions.assertEquals("DECLINED", adoptionRequestEntity.getStatus());
        verify(adoptionRepo, times(1)).findById(requestId);
        verify(adoptionRepo, times(1)).save(adoptionRequestEntity);
        Assertions.assertNotNull(result);
    }

    @Test
    void declineAdoptionRequest_InvalidId_ThrowsNoSuchElementException() {
        Long requestId = 1L;

        when(adoptionRepo.findById(requestId)).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> adoptionService.declineAdoptionRequest(requestId)
        );

        verify(adoptionRepo, times(1)).findById(requestId);
        verify(adoptionRepo, never()).save(any());
    }

}