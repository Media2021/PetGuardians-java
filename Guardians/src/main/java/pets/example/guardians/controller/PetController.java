package pets.example.guardians.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pets.example.guardians.configuration.isauthenticated.IsAuthenticated;
import pets.example.guardians.model.Pet;
import pets.example.guardians.model.PetType;
import pets.example.guardians.services.PetService;


import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {
    private final PetService petService;

    @IsAuthenticated
    @RolesAllowed({ "ROLE_ADMIN"})
    @PostMapping
    public Pet createPet(@RequestBody Pet pet)
    {
        return petService.createPet(pet);
    }
    @IsAuthenticated
    @RolesAllowed({ "ROLE_ADMIN"})
    @GetMapping("/available/cats")
    public ResponseEntity<Long> countAvailableCats() {
        try {
            long count = petService.countAvailableCats();
            return ResponseEntity.ok(count);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/available/dogs")
    public ResponseEntity<Long> countAvailableDogs() {
        try {
            long count = petService.countAvailableDogs();
            return ResponseEntity.ok(count);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/adopted/dogs")
    public ResponseEntity<Long> countAdoptedDogs() {
        try {
            long count = petService.countAdoptedDogs();
            return ResponseEntity.ok(count);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/adopted/cats")
    public ResponseEntity<Long> countAdoptedCats() {
        try {
            long count = petService.countAdoptedCats();
            return ResponseEntity.ok(count);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/count")
    public ResponseEntity<Long> countPets() {
        long petCount;
        try {
            petCount = petService.countPets();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(petCount);
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
    @GetMapping("/available")
    public ResponseEntity<List<Pet>> getAvailablePets() {
        List<Pet> pets;
        try {
            pets = petService.getAvailablePets();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pets);
    }
    @GetMapping("/adoptedCount/{petType}")
    public ResponseEntity<Long> countAdoptedPets(@PathVariable PetType petType) {
        try {
            long count = petService.countAdoptedPets(petType);
            return ResponseEntity.ok(count);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }


}
