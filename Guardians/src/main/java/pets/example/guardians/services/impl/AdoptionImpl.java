package pets.example.guardians.services.impl;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
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
        Optional<PetEntity> existingPet = petRepo.findById(adoptionRequestEntity.getPet().getId());

        UserEntity userEntity = existingUser.orElseThrow(() -> new NoSuchElementException("User not found with id " + adoptionRequestEntity.getUser().getId()));
        PetEntity petEntity = existingPet.orElseThrow(() -> new NoSuchElementException("Pet not found with id " + adoptionRequestEntity.getPet().getId()));

        AdoptionRequestEntity savedEntity = new AdoptionRequestEntity();
        savedEntity.setUser(userEntity);
        savedEntity.setPet(petEntity);
        savedEntity.setNotes(adoptionRequestEntity.getNotes());
        savedEntity.setRequestDate(new Date());
        savedEntity.setStatus("PENDING");

        savedEntity = adoptionRepo.save(savedEntity);

        AdoptionRequest adoptionRequest = AdoptionRequestMapper.toModel(savedEntity);

        return adoptionRequest;
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
    @Override
    public Optional<AdoptionRequest> getAdoptionRequestById(Long id) {
        Optional<AdoptionRequestEntity> adoptionRequestEntityOptional = adoptionRepo.findById(id);
        if (adoptionRequestEntityOptional.isPresent()) {
            AdoptionRequestEntity adoptionRequestEntity = adoptionRequestEntityOptional.get();
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










}
