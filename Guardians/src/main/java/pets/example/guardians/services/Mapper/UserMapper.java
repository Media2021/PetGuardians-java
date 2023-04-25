package pets.example.guardians.services.Mapper;

import pets.example.guardians.model.Pet;
import pets.example.guardians.model.User;
import pets.example.guardians.repository.entity.PetEntity;
import pets.example.guardians.repository.entity.UserEntity;
import pets.example.guardians.services.Mapper.PetMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toModel(UserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        model.setAddress(entity.getAddress());
        model.setPassword(entity.getPassword());
        model.setPhone(entity.getPhone());
        model.setBirthdate(entity.getBirthdate());
        model.setRole(entity.getRole());
        if (entity.getAdoptedPets() != null) {
            Set<Pet> adoptedPets = entity.getAdoptedPets().stream()
                    .map(PetMapper::toModel)
                    .collect(Collectors.toSet());
            model.setAdoptedPets(adoptedPets);
        }
        return model;
    }

    public static UserEntity toEntity(User model) {
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        entity.setAddress(model.getAddress());
        entity.setPassword(model.getPassword());
        entity.setPhone(model.getPhone());
        entity.setBirthdate(model.getBirthdate());
        entity.setRole(model.getRole());
        if (model.getAdoptedPets() != null) {
            Set<PetEntity> adoptedPets = model.getAdoptedPets().stream()
                    .map(PetMapper::toEntity)
                    .collect(Collectors.toSet());
            entity.setAdoptedPets(adoptedPets);
        }
        return entity;
    }
}