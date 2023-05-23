package pets.example.guardians.services.Mapper;

import pets.example.guardians.model.Pet;
import pets.example.guardians.model.User;
import pets.example.guardians.repository.entity.PetEntity;
import pets.example.guardians.repository.entity.UserEntity;

public class PetMapper {
    private PetMapper() {}
    public static Pet toModel(PetEntity entity) {
        Pet model = new Pet();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setAge(entity.getAge());

        model.setType(entity.getType());
        model.setStatus(entity.getStatus());
        model.setGender(entity.getGender());
        if(entity.getAdopter() != null) {
            User adopter = new User();
            adopter.setId(entity.getAdopter().getId());
            model.setAdopter(adopter);
        }
        return model;
    }

    public static PetEntity toEntity(Pet model) {
        PetEntity entity = new PetEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setAge(model.getAge());

        entity.setType(model.getType());
        entity.setStatus(model.getStatus());
        entity.setGender(model.getGender());
        if (model.getAdopter() != null) {
            UserEntity adopterEntity = new UserEntity();
            adopterEntity.setId(model.getAdopter().getId());
            entity.setAdopter(adopterEntity);
        }
        return entity;
    }
}
