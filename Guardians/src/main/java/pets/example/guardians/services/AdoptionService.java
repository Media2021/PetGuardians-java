package pets.example.guardians.services;

import pets.example.guardians.model.AdoptionRequest;

import pets.example.guardians.model.AdoptionStatistics;
import pets.example.guardians.model.PetType;
import pets.example.guardians.repository.entity.AdoptionRequestEntity;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AdoptionService {




    long countAdoptedPetsByType(PetType petType);

    AdoptionRequest createAdoptionRequest(AdoptionRequestEntity adoptionRequestEntity);



    List<AdoptionRequest> getAllAdoptionRequests();

   Optional<AdoptionRequest>getAdoptionRequestById(Long id);



   Optional<AdoptionRequest> updateAdoptionRequestById(Long id, AdoptionRequest updatedRequest);

   AdoptionRequest acceptAdoptionRequest(Long requestId);

   AdoptionRequest declineAdoptionRequest(Long id);

   List<AdoptionRequest> getAdoptionRequestsByUserId(Long userId);
}
