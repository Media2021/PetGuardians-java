package pets.example.guardians.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pets.example.guardians.model.*;

import pets.example.guardians.repository.entity.AdoptionRequestEntity;
import pets.example.guardians.services.AdoptionService;

import pets.example.guardians.services.PetService;
import pets.example.guardians.services.UserService;
import pets.example.guardians.services.Mapper.AdoptionRequestMapper;
import pets.example.guardians.services.Mapper.PetMapper;
import pets.example.guardians.services.Mapper.UserMapper;


import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/adoption")
@AllArgsConstructor
public class AdoptionController {
    private final AdoptionService adoptionService;
    private final PetService petService;
    private final UserService userService;
    @GetMapping("/count/{petType}")
    public ResponseEntity<Long> countAdoptedPetsByType(@PathVariable PetType petType) {
        long count = adoptionService.countAdoptedPetsByType(petType);
        return ResponseEntity.ok(count);
    }
    @PostMapping
    public ResponseEntity<AdoptionRequest> createAdoptionRequest(@RequestBody AdoptionRequest request) {
        Optional<Long> optionalUserId = Optional.ofNullable(request.getUser()).map(User::getId);
        Optional<Long> optionalPetId = Optional.ofNullable(request.getPet()).map(Pet::getId);

        User user = optionalUserId.flatMap(userService::getUserById).orElse(null);
        Pet pet = optionalPetId.flatMap(petService::getPetById).orElse(null);

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
    @Transactional
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


    @PutMapping("/{id}/accept")
    public ResponseEntity<AdoptionRequest> acceptAdoptionRequest(@PathVariable Long id) {
        try {
            AdoptionRequest adoptionRequest = adoptionService.acceptAdoptionRequest(id);
            return ResponseEntity.ok(adoptionRequest);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<AdoptionRequest> declineAdoptionRequest(@PathVariable("id") Long id) {
        try {
            AdoptionRequest declinedRequest = adoptionService.declineAdoptionRequest(id);
            return ResponseEntity.ok(declinedRequest);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{userId}/adoption-requests")
    public ResponseEntity<List<AdoptionRequest>> getAdoptionRequestsByUserId(@PathVariable Long userId) {
        List<AdoptionRequest> adoptionRequests = adoptionService.getAdoptionRequestsByUserId(userId);
        if (adoptionRequests.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(adoptionRequests);
        }
    }



}
