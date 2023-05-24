package pets.example.guardians.services.Mapper;

import pets.example.guardians.model.AdoptionRequest;
import pets.example.guardians.model.Pet;
import pets.example.guardians.repository.entity.AdoptionRequestEntity;
import pets.example.guardians.repository.entity.PetEntity;

public class AdoptionRequestMapper {



        public static AdoptionRequest toModel(AdoptionRequestEntity entity) {
            AdoptionRequest request = new AdoptionRequest();
            request.setId(entity.getId());
            request.setUser(UserMapper.toModel(entity.getUser()));
            if (entity.getPet() != null) {
                Pet pet = new Pet();
                pet.setId(entity.getPet().getId());
                request.setPet(pet);
            }


            request.setStatus(entity.getStatus());
            request.setNotes(entity.getNotes());
            request.setRequestDate(entity.getRequestDate());
            return request;
        }

    public static AdoptionRequestEntity toEntity(AdoptionRequest model) {
        AdoptionRequestEntity entity = new AdoptionRequestEntity();
        entity.setId(model.getId());
        entity.setUser(UserMapper.toEntity(model.getUser()));
        if (model.getPet() != null) {
            PetEntity petEntity = new PetEntity();
            petEntity.setId(model.getPet().getId());
            entity.setPet(petEntity);
        }
        entity.setStatus(model.getStatus());
        entity.setNotes(model.getNotes());
        entity.setRequestDate(model.getRequestDate());
        return entity;
    }
}
