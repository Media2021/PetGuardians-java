package pets.example.guardians.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pets.example.guardians.model.Pet;
import pets.example.guardians.model.User;
import pets.example.guardians.repository.PetRepo;
import pets.example.guardians.repository.entity.PetEntity;
import pets.example.guardians.repository.entity.UserEntity;
import pets.example.guardians.services.Mapper.PetMapper;
import pets.example.guardians.services.PetService;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetRepo petRepo;
    @Override
    public Pet createPet(Pet pet) {
        if (pet.getName() == null || pet.getName().isEmpty()) {
            throw new DataIntegrityViolationException("Invalid pet data: Pet name cannot be empty");
        }
        try {
            PetEntity petEntity = new PetEntity();
            BeanUtils.copyProperties(pet, petEntity);
            petRepo.save(petEntity);
            return pet;
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Failed to create pet", e);
        }
    }
    @Override
    public List<Pet> getAllPets() {
        List<PetEntity> petEntities = petRepo.findAll();
        List<Pet> pets = new ArrayList<>();

        for (PetEntity petEntity : petEntities) {

                Pet pet = new Pet();
                BeanUtils.copyProperties(petEntity, pet);
            UserEntity adopterEntity = petEntity.getAdopter();
            if (adopterEntity != null) {
                User adopter = new User();
                BeanUtils.copyProperties(adopterEntity, adopter);
                pet.setAdopter(adopter);
            } else {
                pet.setAdopter(null);
            }

            pets.add(pet);
        }

        return pets;
    }
    @Override
    public List<Pet> getAvailablePets() {
        List<PetEntity> petEntities = petRepo.findAll();
        List<Pet> pets = new ArrayList<>();

        for (PetEntity petEntity : petEntities) {
            if (!petEntity.getStatus() .equals("ADOPTED"))  {
                Pet pet = new Pet();
                BeanUtils.copyProperties(petEntity, pet);
                pets.add(pet);
            }
        }

        return pets;
    }
    @Override
    public void deletePet(Long id) {
        Optional<PetEntity> petEntityOptional = petRepo.findById(id);
        if (petEntityOptional.isPresent()) {
            PetEntity petEntity = petEntityOptional.get();
            petRepo.delete(petEntity);
        } else {
            throw new NoSuchElementException("Pet with ID " + id + " not found");
        }
    }
    @Override
    @Transactional
    public Optional<Pet> getPetById(Long id) {
        Optional<PetEntity> petEntityOpt = petRepo.findById(id);
        if (petEntityOpt.isPresent()) {
            PetEntity petEntity = petEntityOpt.get();
            return Optional.of(PetMapper.toModel(petEntity));
        } else {
            throw new NoSuchElementException(String.format("Pet with ID %d not found", id));
        }
    }


    @Override
    public Pet updatePetById(Long id, Pet pet) {
        Optional<PetEntity> optionalPetEntity = petRepo.findById(id);
        if (optionalPetEntity.isEmpty()) {
            throw new NoSuchElementException("Pet with ID " + id + " not found");
        }
        PetEntity petEntity = optionalPetEntity.get();
        petEntity.setName(pet.getName());
        petEntity.setAge(pet.getAge());
        petEntity.setDescription(pet.getDescription());
        petEntity.setType(pet.getType());
        petEntity.setStatus(pet.getStatus());
        petEntity.setGender(pet.getGender());


        petRepo.save(petEntity);
        return pet;
    }

}
