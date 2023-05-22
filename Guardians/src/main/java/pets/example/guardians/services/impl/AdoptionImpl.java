package pets.example.guardians.services.impl;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pets.example.guardians.model.AdoptionRequest;
import pets.example.guardians.model.Pet;
import pets.example.guardians.model.User;
import pets.example.guardians.repository.AdoptionRepo;
import pets.example.guardians.repository.PetRepo;
import pets.example.guardians.repository.UserRepo;
import pets.example.guardians.repository.entity.AdoptionRequestEntity;
import pets.example.guardians.repository.entity.PetEntity;
import pets.example.guardians.repository.entity.UserEntity;
import pets.example.guardians.services.AdoptionService;
import pets.example.guardians.services.Mapper.AdoptionRequestMapper;
import pets.example.guardians.services.Mapper.PetMapper;
import pets.example.guardians.services.Mapper.UserMapper;

import java.util.*;

@Service
@AllArgsConstructor
public class AdoptionImpl implements AdoptionService {

    private final AdoptionRepo adoptionRepo;
    private final UserRepo userRepo;
    private final PetRepo petRepo;


    @Override
    public AdoptionRequest createAdoptionRequest(AdoptionRequestEntity adoptionRequestEntity) {

        Optional<UserEntity> existingUser = userRepo.findById(adoptionRequestEntity.getUser().getId());
        if (existingUser.isEmpty()) {
            throw new NoSuchElementException("User not found with id " + adoptionRequestEntity.getId());
        }


        PetEntity pet = adoptionRequestEntity.getPet();
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        Optional<PetEntity> existingPet = petRepo.findById(pet.getId());
        if (existingPet.isEmpty()) {
            throw new NoSuchElementException("Pet not found with id " + pet.getId());
        }

        AdoptionRequestEntity savedEntity = new AdoptionRequestEntity();
        savedEntity.setUser(existingUser.get());
        savedEntity.setPet(existingPet.get());
        savedEntity.setNotes(adoptionRequestEntity.getNotes());
        savedEntity.setRequestDate(new Date());
        savedEntity.setStatus("PENDING");

        savedEntity = adoptionRepo.save(savedEntity);

        return AdoptionRequestMapper.toModel(savedEntity);
    }


    @Override
    public List<AdoptionRequest> getAllAdoptionRequests() {
        List<AdoptionRequestEntity> adoptionRequestEntities = adoptionRepo.findAll();
        List<AdoptionRequest> adoptionRequests = new ArrayList<>();

        for (AdoptionRequestEntity adoptionRequestEntity : adoptionRequestEntities) {
            adoptionRequests.add(AdoptionRequestMapper.toModel(adoptionRequestEntity));
        }

        return adoptionRequests;
    }
    @Transactional
    @Override
    public Optional<AdoptionRequest> getAdoptionRequestById(Long id) {
        Optional<AdoptionRequestEntity> adoptionRequestEntityOptional = adoptionRepo.findById(id);
        if (adoptionRequestEntityOptional.isPresent()) {
            AdoptionRequestEntity adoptionRequestEntity = adoptionRequestEntityOptional.get();
            if (adoptionRequestEntity.getUser() == null) {
                throw new IllegalArgumentException("Adoption request is missing a user");
            }
            if (adoptionRequestEntity.getPet() == null) {
                throw new IllegalArgumentException("Adoption request is missing a pet");
            }
            AdoptionRequest adoptionRequest = AdoptionRequestMapper.toModel(adoptionRequestEntity);
            UserEntity userEntity = adoptionRequestEntity.getUser();
            PetEntity petEntity = adoptionRequestEntity.getPet();
            User user = UserMapper.toModel(userEntity);
            Pet pet = PetMapper.toModel(petEntity);
            adoptionRequest.setUser(user);
            adoptionRequest.setPet(pet);
            return Optional.of(adoptionRequest);
        } else {
            return Optional.empty();
        }
    }


    @Override
    public Optional<AdoptionRequest> updateAdoptionRequestById(Long id, AdoptionRequest updatedRequest) {
        Optional<AdoptionRequestEntity> adoptionRequestEntityOptional = adoptionRepo.findById(id);
        if (adoptionRequestEntityOptional.isPresent()) {
            AdoptionRequestEntity adoptionRequestEntity = adoptionRequestEntityOptional.get();
            adoptionRequestEntity.setStatus(updatedRequest.getStatus());
            adoptionRequestEntity.setNotes(updatedRequest.getNotes());
            adoptionRequestEntity.setRequestDate(updatedRequest.getRequestDate());

            UserEntity userEntity = UserMapper.toEntity(updatedRequest.getUser());
            PetEntity petEntity = PetMapper.toEntity(updatedRequest.getPet());
            adoptionRequestEntity.setUser(userEntity);
            adoptionRequestEntity.setPet(petEntity);

            AdoptionRequestEntity savedEntity = adoptionRepo.save(adoptionRequestEntity);
            AdoptionRequest adoptionRequest = AdoptionRequestMapper.toModel(savedEntity);
            adoptionRequest.setUser(UserMapper.toModel(savedEntity.getUser()));
            adoptionRequest.setPet(PetMapper.toModel(savedEntity.getPet()));
            return Optional.of(adoptionRequest);
        } else {
            return Optional.empty();
        }
    }




    @Override
    public AdoptionRequest acceptAdoptionRequest(Long id) {
        Optional<AdoptionRequestEntity> adoptionRequestEntity = adoptionRepo.findById(id);
        if (adoptionRequestEntity.isEmpty()) {
            throw new NoSuchElementException("Adoption request not found with id " + id);
        }
        AdoptionRequestEntity savedEntity = adoptionRequestEntity.get();
        savedEntity.setStatus("ACCEPTED");
        savedEntity = adoptionRepo.save(savedEntity);

        UserEntity userEntity = savedEntity.getUser();
        PetEntity petEntity = savedEntity.getPet();



        petEntity.setAdopter(userEntity);
        petEntity.setStatus("ADOPTED");
        petRepo.saveAndFlush(petEntity);
        userEntity.adoptPet(petEntity);

        userRepo.saveAndFlush(userEntity);



        return AdoptionRequestMapper.toModel(savedEntity);
    }



    @Override
    public AdoptionRequest declineAdoptionRequest(Long id) {
        Optional<AdoptionRequestEntity> existingRequest = adoptionRepo.findById(id);
        if (existingRequest .isEmpty()) {
            throw new NoSuchElementException("Adoption request not found with id " + id);
        }
        AdoptionRequestEntity requestEntity = existingRequest.get();


        requestEntity.setStatus("DECLINED");


        AdoptionRequestEntity savedEntity = adoptionRepo.save(requestEntity);


        return AdoptionRequestMapper.toModel(savedEntity);
    }







}
