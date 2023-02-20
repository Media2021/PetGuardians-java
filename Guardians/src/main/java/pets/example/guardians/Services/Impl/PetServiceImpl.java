package pets.example.guardians.Services.Impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pets.example.guardians.Entity.PetEntity;
import pets.example.guardians.Model.Pet;
import pets.example.guardians.Repository.PetRepo;
import pets.example.guardians.Services.PetService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetRepo petRepo;




    @Override
    public Pet createPet(Pet pet) {
        PetEntity petEntity= new PetEntity();
        BeanUtils.copyProperties(pet, petEntity);
        petRepo.save(petEntity);

        return pet;
    }

    @Override
    public List<Pet> getAllPets() {
        List< PetEntity> petEntities= petRepo.findAll();
        List<Pet> pets = petEntities
                .stream()
                .map(pet-> new Pet(
                        pet.getId(),
                        pet.getName(),
                        pet.getAge(),
                        pet.getDescription(),
                        pet.getType(),
                pet.getStatus(),
                pet.getGender()))
                        .collect(Collectors
                        .toList());
        return pets;
    }
    @Override
    public void deletePet(Long id) {
        PetEntity petEntity = petRepo.findById(id).get();
        petRepo.delete(petEntity);
    }
    @Override
    public Pet getPetById(Long id) {
        PetEntity petEntity =
                petRepo.findById(id).get();
        Pet pet = new Pet();
        BeanUtils.copyProperties(petEntity,pet);
        return pet;
    }
    @Override
    public  Pet updatePetById(Long id, Pet pet) {

        PetEntity petEntity = petRepo.findById(id).get();
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
