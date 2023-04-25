package pets.example.guardians.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pets.example.guardians.model.AdoptionRequest;
import pets.example.guardians.model.Pet;
import pets.example.guardians.model.User;

import pets.example.guardians.repository.entity.AdoptionRequestEntity;
import pets.example.guardians.services.AdoptionService;
import pets.example.guardians.services.Mapper.AdoptionRequestMapper;
import pets.example.guardians.services.Mapper.PetMapper;
import pets.example.guardians.services.Mapper.UserMapper;
import pets.example.guardians.services.PetService;
import pets.example.guardians.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/requests")
@AllArgsConstructor
public class AdoptionController {
    private final AdoptionService adoptionService;
    private final PetService petService;
    private final UserService userService;
    @PostMapping
    public ResponseEntity<AdoptionRequest> createAdoptionRequest(@RequestBody AdoptionRequest request) {
        Optional<Long> optionalUserId = Optional.of(request.getUser().getId());
        Optional<Long> optionalPetId = Optional.of(request.getPet().getId());

        Long userId = optionalUserId.get();
        Long petId = optionalPetId.get();

        User user = userService.getUserById(userId).orElse(null);
        Pet pet = petService.getPetById(petId).orElse(null);

        if (user == null || pet == null) {
            return ResponseEntity.badRequest().build();
        }

        AdoptionRequestEntity adoptionRequestEntity = AdoptionRequestMapper.toEntity(request);
        adoptionRequestEntity.setUser(UserMapper.toEntity(user));
        adoptionRequestEntity.setPet(PetMapper.toEntity(pet));
        adoptionRequestEntity.setNotes(request.getNotes());
        adoptionRequestEntity.setRequestDate(request.getRequestDate());
        adoptionRequestEntity.setStatus(request.getStatus());

        AdoptionRequest adoptionRequest = adoptionService.createAdoptionRequest(adoptionRequestEntity);
        adoptionRequest.setUser(user);
        adoptionRequest.setPet(pet);
        adoptionRequest.setNotes(adoptionRequestEntity.getNotes());
        adoptionRequest.setRequestDate(adoptionRequestEntity.getRequestDate());
        adoptionRequest.setStatus(adoptionRequestEntity.getStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body(adoptionRequest);
    }


    @GetMapping
    public ResponseEntity<List<AdoptionRequest>> getAllAdoptionRequests() {
        List<AdoptionRequest> adoptionRequests = adoptionService.getAllAdoptionRequests();
        if (adoptionRequests.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (AdoptionRequest adoptionRequest : adoptionRequests) {
                adoptionRequest.setUser(userService.getUserById(adoptionRequest.getUser().getId()).orElse(null));
                adoptionRequest.setPet(petService.getPetById(adoptionRequest.getPet().getId()).orElse(null));
            }
            return ResponseEntity.ok(adoptionRequests);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<AdoptionRequest> getAdoptionRequestById(@PathVariable Long id) {
        Optional<AdoptionRequest> optionalAdoptionRequest = adoptionService.getAdoptionRequestById(id);
        if (optionalAdoptionRequest.isPresent()) {
            AdoptionRequest adoptionRequest = optionalAdoptionRequest.get();
            adoptionRequest.setUser(userService.getUserById(adoptionRequest.getUser().getId()).orElse(null));
            adoptionRequest.setPet(petService.getPetById(adoptionRequest.getPet().getId()).orElse(null));
            return ResponseEntity.ok(adoptionRequest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdoptionRequest> updateAdoptionRequestById(@PathVariable Long id, @RequestBody AdoptionRequest updatedRequest) {
        Optional<AdoptionRequest> optionalAdoptionRequest = adoptionService.updateAdoptionRequestById(id, updatedRequest);
        if (optionalAdoptionRequest.isPresent()) {
            AdoptionRequest adoptionRequest = optionalAdoptionRequest.get();
            return ResponseEntity.ok(adoptionRequest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }





//    @PostMapping()
//    public ResponseEntity<Void> adoptPet(@PathVariable Long userId, @PathVariable Long petId) {
//        User user = userService.getUserById(userId)
//                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
//        Pet pet = petService.getPetById(petId)
//                .orElseThrow(() -> new NoSuchElementException("Pet not found with id " + petId));
//
//        user.adoptPet(pet);
//        adoptionService.adoptPet(user,pet);
//
//        return ResponseEntity.ok().build();
//    }
//    @DeleteMapping("{userId}/pets/{petId}")
//    public ResponseEntity<Void> deletePet(@PathVariable Long userId, @PathVariable Long petId) {
//        User user = userService.getUserById(userId)
//                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
//
//        Pet pet = petService.getPetById(petId)
//                .orElseThrow(() -> new NoSuchElementException("Pet not found with id " + petId));
//
//        if (!user.getAdoptedPets().contains(pet)) {
//            throw new NoSuchElementException("Pet not found for user with id " + userId);
//        }
//
//        user.deletePet(pet);
//        adoptionService.deletePet(user,pet);
//
//
//        return ResponseEntity.ok().build();
//    }


}
