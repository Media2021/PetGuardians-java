package pets.example.guardians.services.Mapper;

import pets.example.guardians.model.AdoptionRequest;
import pets.example.guardians.repository.entity.AdoptionRequestEntity;

public class AdoptionRequestMapper {



        public static AdoptionRequest toModel(AdoptionRequestEntity entity) {
            AdoptionRequest request = new AdoptionRequest();
            request.setId(entity.getId());
            request.setUser(UserMapper.toModel(entity.getUser()));
            request.setPet(PetMapper.toModel(entity.getPet())); // set the pet id instead of the whole PetEntity

            request.setStatus(entity.getStatus());
            request.setNotes(entity.getNotes());
            request.setRequestDate(entity.getRequestDate());
            return request;
        }

    public static AdoptionRequestEntity toEntity(AdoptionRequest model) {
        AdoptionRequestEntity entity = new AdoptionRequestEntity();
        entity.setId(model.getId());
        entity.setUser(UserMapper.toEntity(model.getUser()));
        entity.setPet(PetMapper.toEntity(model.getPet()));
        entity.setStatus(model.getStatus());
        entity.setNotes(model.getNotes());
        entity.setRequestDate(model.getRequestDate());
        return entity;
    }
}
