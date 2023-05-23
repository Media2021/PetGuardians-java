package pets.example.guardians.services.Mapper;


import org.hibernate.Hibernate;
import pets.example.guardians.model.Pet;
import pets.example.guardians.model.User;
import pets.example.guardians.repository.entity.PetEntity;
import pets.example.guardians.repository.entity.UserEntity;


import java.util.HashSet;
import java.util.Set;


public class UserMapper {
private UserMapper(){}
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
        Hibernate.initialize(entity.getAdoptedPets());
        if (entity.getAdoptedPets() != null) {
            Set<Pet> adoptedPets = new HashSet<>();
            for (PetEntity petEntity : entity.getAdoptedPets()) {
                Pet pet = PetMapper.toModel(petEntity);
                adoptedPets.add(pet);
            }
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
        Hibernate.initialize(model.getAdoptedPets());
        if (model.getAdoptedPets() != null) {
            Set<PetEntity> adoptedPets = new HashSet<>();
            for (Pet pet : model.getAdoptedPets()) {
                PetEntity petEntity = PetMapper.toEntity(pet);
                adoptedPets.add(petEntity);
            }
            entity.setAdoptedPets(adoptedPets);
        }
        return entity;
    }
}