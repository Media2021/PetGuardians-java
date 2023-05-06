package pets.example.guardians.services;

import pets.example.guardians.model.AdoptionRequest;

import pets.example.guardians.repository.entity.AdoptionRequestEntity;


import java.util.List;
import java.util.Optional;

public interface AdoptionService {


   AdoptionRequest createAdoptionRequest(AdoptionRequestEntity adoptionRequestEntity);

   List<AdoptionRequest> getAllAdoptionRequests();

   Optional<AdoptionRequest>getAdoptionRequestById(Long id);



   Optional<AdoptionRequest> updateAdoptionRequestById(Long id, AdoptionRequest updatedRequest);
}
