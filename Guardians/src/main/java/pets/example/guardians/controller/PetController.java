package pets.example.guardians.controller;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pets.example.guardians.configuration.isauthenticated.IsAuthenticated;
import pets.example.guardians.model.Pet;
import pets.example.guardians.model.PetType;
import pets.example.guardians.repository.AvailableListPetType;
import pets.example.guardians.repository.PetTypeCount;
import pets.example.guardians.repository.entity.PetEntity;
import pets.example.guardians.services.PetService;


import javax.annotation.security.RolesAllowed;
import java.util.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("/available")
    public List<PetEntity> getAvailablePets() {
        return petService.getListAvailablePets();
    }
    @GetMapping("/count-adopted-by-type")
    public ResponseEntity<List<Map<String, Object>>> countAdoptedByType() {
        try {
            List<PetTypeCount> petTypeCounts = petService.countByStatusAdoptedAndType();

            List<Map<String, Object>> result = new ArrayList<>();

            for (PetTypeCount petTypeCount : petTypeCounts) {
                PetType petType = petTypeCount.getType();
                Long count = petTypeCount.getCount();

                if (petType != null) {
                    Map<String, Object> petTypeData = new HashMap<>();
                    petTypeData.put("pet Type", petType.toString());
                    petTypeData.put("count", count);

                    result.add(petTypeData);
                }
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/count-available-by-type")
    public ResponseEntity<List<Map<String, Object>>> countAvailableByType() {
        try {
            List<AvailableListPetType> petTypeCounts = petService.countByStatusAvailableAndType();

            List<Map<String, Object>> result = new ArrayList<>();

            for (AvailableListPetType petTypeCount : petTypeCounts) {
                PetType petType = petTypeCount.getType();
                Long count = petTypeCount.getCount();

                if (petType != null) {
                    Map<String, Object> petTypeData = new HashMap<>();
                    petTypeData.put("pet Type", petType.toString());
                    petTypeData.put("count", count);

                    result.add(petTypeData);
                }
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all/count")
    public ResponseEntity<Long> countAllPets() {
        try {
            long count = petService.countAllPets();
            return ResponseEntity.ok(count);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/available/count")
    public ResponseEntity<Long> countAvailablePetsByStatus() {
        try {
            long count = petService.countAvailablePetsByStatus();
            return ResponseEntity.ok(count);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @IsAuthenticated
    @RolesAllowed({ "ROLE_ADMIN"})
    @PostMapping
    public Pet createPet(@RequestBody Pet pet)
    {
        return petService.createPet(pet);
    }


    @GetMapping()
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets;
        try {
            pets = petService.getAllPets();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pets);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id)
    {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        Optional<Pet> pet = petService.getPetById(id);
        return pet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @IsAuthenticated
    @RolesAllowed({ "ROLE_ADMIN"})
    @PutMapping("{id}")
    public ResponseEntity<Pet> updatePetById(@PathVariable Long id , @RequestBody Pet pet)
    {
        pet = petService.updatePetById(id, pet);
        return ResponseEntity.ok(pet);
    }



}
